package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.brand.dto.BrandMapper;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.util.Output.Value;

@RequestMapping(FindBrandController.API_URL)
@RestController
@RequiredArgsConstructor
public class FindBrandController {

    public static final String API_URL = "/api/brands/{id}";

    private final RequestHandler<?, BrandResponse> requestHandler;
    private final BrandService service;
    private final BrandMapper mapper;

    @GetMapping({"", "/"})
    public Response<BrandResponse> handle(@PathVariable String id) {
        return requestHandler.handle(null, (res, req, params) -> {
            Long brandId;
            try {
                brandId = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException ex) {
                return res.badRequest(
                        "Cannot convert [" + id + "] to number."
                );
            }
            Value<Brand> o = service.findByKey(brandId);
            if (!o.isNull()) {
                return res.ok(o.map(mapper::map).get());
            }
            return res.notFound("Brand with id [" + id + "] not found");
        });
    }
}

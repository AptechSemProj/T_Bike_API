package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.Request;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.impl.StdRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.brand.conf.BrandApiUrls;
import se.pj.tbike.core.api.brand.dto.BrandMapper;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.japi.impl.ResponseConfigurerImpl;
import se.pj.tbike.util.Output;

@RequiredArgsConstructor
@RestController
public class FindBrandController {

    private static final RequestHandler<?, BrandResponse> REQUEST_HANDLER;

    static {
        REQUEST_HANDLER = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

    private final BrandService service;
    private final BrandMapper mapper;

    @GetMapping({BrandApiUrls.BRAND_INFO_API})
    public Response<BrandResponse> handle(@PathVariable String id) {
        return REQUEST_HANDLER.handle(null, (res, req, params) -> {
            Long brandId;
            try {
                brandId = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException ex) {
                return res.badRequest(
                        "Cannot convert [" + id + "] to number."
                );
            }
            Output.Value<Brand> old = service.findByKey(brandId);
            if (!old.isNull()) {
                return res.ok(mapper.map(old.get()));
            }
            return res.notFound("Brand with id [" + id + "] not found");
        });
    }
}

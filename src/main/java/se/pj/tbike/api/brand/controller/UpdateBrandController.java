package se.pj.tbike.api.brand.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.brand.dto.BrandMapper;
import se.pj.tbike.api.brand.data.BrandService;
import se.pj.tbike.api.brand.dto.BrandRequest;
import se.pj.tbike.api.brand.entity.Brand;

import java.util.Optional;

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(UpdateBrandController.API_URL)
@RestController
@RequiredArgsConstructor
public class UpdateBrandController {

    public static final String API_URL = "/api/brands/{id}";

    private final RequestHandler<BrandRequest, Long> requestHandler;
    private final BrandService service;
    private final BrandMapper mapper;

    @SuppressWarnings("DuplicatedCode")
    @PutMapping({"", "/"})
    public Response<Long> update(@PathVariable String id,
                                 @RequestBody BrandRequest request) {
        return requestHandler.handle(request, (res, req) -> {
            Long brandId;
            try {
                brandId = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException ex) {
                return res.badRequest(
                        "Cannot convert [" + id + "] to number."
                );
            }
            Optional<Brand> old = service.findByKey(brandId);
            if (old.isEmpty()) {
                return res.notFound(
                        "Brand with id [" + brandId + "] not found"
                );
            }
            Brand brand = old.get();
            Brand b = mapper.map(req);
            b.setId(brand.getId());
            if (service.update(b, brand)) {
                return res.noContent();
            }
            return res.internalServerError(
                    "Cannot update brand with id [" + brandId + ']'
            );
        });
    }
}

package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.brand.conf.BrandApiUrls;
import se.pj.tbike.core.api.brand.dto.BrandMapper;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandRequest;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.util.Output.Value;

import static org.springframework.util.NumberUtils.parseNumber;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor
public class UpdateBrandController {

    private final RequestHandler<BrandRequest, Long> requestHandler;
    private final BrandService service;
    private final BrandMapper mapper;

    @PutMapping({BrandApiUrls.BRAND_INFO_API})
    public Response<Long> update(
            @PathVariable String id,
            @RequestBody BrandRequest request
    ) {
        return requestHandler.handle(request, (res, req) -> {
            Long brandId;
            try {
                brandId = parseNumber(id, Long.class);
            } catch (IllegalArgumentException ex) {
                return res.badRequest(
                        "Cannot convert [" + id + "] to number."
                );
            }
            Value<Brand> old = service.findByKey(brandId);
            if (old.isNull()) {
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

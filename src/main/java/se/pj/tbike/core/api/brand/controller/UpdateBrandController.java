package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.brand.conf.BrandApiUrls;
import se.pj.tbike.core.api.brand.conf.BrandMapper;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandRequest;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.japi.impl.ResponseConfigurerImpl;
import se.pj.tbike.util.Output;

@RestController
public class UpdateBrandController {

    private final RequestHandler<BrandRequest, Long> handler;
    private final BrandService service;
    private final BrandMapper mapper;

    public UpdateBrandController(
            BrandService service,
            BrandMapper mapper
    ) {
        this.service = service;
        this.mapper = mapper;
        this.handler = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({
            BrandApiUrls.BRAND_INFO_API
    })
    public com.ank.japi.Response<Long> handle(
            @PathVariable String id,
            @RequestBody BrandRequest request
    ) {
        return handler.handle(request, (res, req, params) -> {
            try {
                Long brandId = NumberUtils.parseNumber(id, Long.class);
                Output.Value<Brand> old = service.findByKey(brandId);
                if (old.isNull()) {
                    return res.notFound(
                            "Brand with id [" + id + "] not found"
                    );
                }
                Brand brand = old.get();
                Brand b = mapper.map(req);
                b.setId(brand.getId());
                if (service.update(b, brand)) {
                    return res.noContent();
                }
                return res.internalServerError(
                        "Cannot update brand with id [" + id + ']'
                );
            } catch (IllegalArgumentException ex) {
                return res.badRequest(
                        "Cannot convert [" + id + "] to number."
                );
            }
        });
    }
}

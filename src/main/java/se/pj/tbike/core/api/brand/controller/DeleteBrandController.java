package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.brand.conf.BrandApiUrls;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandRequest;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.japi.impl.ResponseConfigurerImpl;
import se.pj.tbike.util.Output;

@RestController
public class DeleteBrandController {

    private final RequestHandler<BrandRequest, Long> handler;
    private final BrandService service;

    public DeleteBrandController(BrandService service) {
        this.service = service;
        this.handler = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({
            BrandApiUrls.BRAND_INFO_API
    })
    public com.ank.japi.Response<Long> handle(
            @PathVariable String id
    ) {
        return handler.handle(null, (res, body, params) -> {
            try {
                Long brandId = NumberUtils.parseNumber(id, Long.class);
                Output.Value<Brand> old = service.findByKey(brandId);
                if (old.isNull()) {
                    return res.notFound(
                            "Brand with id [" + id + "] not found"
                    );
                }
                if (service.remove(old.get())) {
                    return res.noContent();
                }
                return res.internalServerError(
                        "Cannot delete brand with id [" + id + ']'
                );
            } catch (IllegalArgumentException ex) {
                return res.badRequest(
                        "Cannot convert [" + id + "] to number."
                );
            }
        });
    }
}

package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandRequest;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.util.Output.Value;

@RequestMapping(DeleteBrandController.API_URL)
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor
public class DeleteBrandController {

    public static final String API_URL = "/api/brands/{id}";

    private final RequestHandler<BrandRequest, Long> requestHandler;
    private final BrandService service;

    @DeleteMapping({"", "/"})
    public Response<Long> delete(@PathVariable String id) {
        return requestHandler.handle(null, (res, body) -> {
            Long brandId;
            try {
                brandId = NumberUtils.parseNumber(id, Long.class);
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
            if (service.remove(old.get())) {
                return res.noContent();
            }
            return res.internalServerError(
                    "Cannot delete brand with id [" + brandId + ']'
            );
        });
    }
}

package se.pj.tbike.http.controller.brand;

import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.Brand;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.helper.FindBrandProcessor;
import se.pj.tbike.http.model.brand.BrandMapper;
import se.pj.tbike.http.model.brand.UpdateBrandRequest;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.UPDATE_BRAND_PATH)
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class UpdateBrandController extends BaseController {

    private final FindBrandProcessor processor;
    private final BrandMapper mapper;

    public UpdateBrandController(
            ResponseConfigurer configurer,
            FindBrandProcessor processor,
            BrandMapper mapper
    ) {
        super(configurer);
        this.processor = processor;
        this.mapper = mapper;
    }

    @PutMapping({"", "/"})
    public Response update(@PathVariable String id,
                           @RequestBody UpdateBrandRequest req) {
        return tryCatch(() -> {
            Brand brand = processor.process(id);
            Brand b = mapper.map(req);
            b.setId(brand.getId());
            if (processor.service().update(b, brand)) {
                return noContent();
            }
            throw HttpException.internalServerError(
                    "Cannot update brand with id [" + id + ']'
            );
        });
    }
}

package se.pj.tbike.http.controller.brand;

import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.Brand;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.helper.FindBrandProcessor;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.DELETE_BRAND_PATH)
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class DeleteBrandController extends BaseController {

    private final FindBrandProcessor processor;

    public DeleteBrandController(
            ResponseConfigurer configurer,
            FindBrandProcessor processor
    ) {
        super(configurer);
        this.processor = processor;
    }

    @DeleteMapping({"", "/"})
    public Response delete(@PathVariable String id) {
        return tryCatch(() -> {
            Brand brand = processor.process(id);
            if (processor.service().delete(brand)) {
                return noContent();
            }
            throw HttpException.internalServerError(
                    "Cannot delete brand with id [" + id + ']'
            );
        });
    }
}

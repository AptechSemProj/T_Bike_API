package se.pj.tbike.http.controller.admin.category;

import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.Category;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.helper.FindCategoryProcessor;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.DELETE_CATEGORY_PATH)
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class DeleteCategoryController extends BaseController {

    private final FindCategoryProcessor processor;

    public DeleteCategoryController(
            ResponseConfigurer configurer,
            FindCategoryProcessor processor
    ) {
        super(configurer);
        this.processor = processor;
    }

    @DeleteMapping({"", "/"})
    public Response delete(@PathVariable String id) {
        return tryCatch(() -> {
            Category category = processor.process(id);
            if (processor.service().delete(category)) {
                return noContent();
            }
            throw HttpException.internalServerError(
                    "Cannot delete category with id [" + id + ']'
            );
        });
    }
}

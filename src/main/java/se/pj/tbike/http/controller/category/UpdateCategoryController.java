package se.pj.tbike.http.controller.category;

import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.helper.FindCategoryProcessor;
import se.pj.tbike.http.model.category.CategoryMapper;
import se.pj.tbike.http.model.category.CategoryRequest;
import se.pj.tbike.domain.entity.Category;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.UPDATE_CATEGORY_PATH)
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class UpdateCategoryController extends BaseController {

    private final FindCategoryProcessor processor;
    private final CategoryMapper mapper;

    public UpdateCategoryController(
            ResponseConfigurer configurer,
            FindCategoryProcessor processor,
            CategoryMapper mapper
    ) {
        super(configurer);
        this.processor = processor;
        this.mapper = mapper;
    }

    @PutMapping({"", "/"})
    public Response update(@PathVariable String id,
                           @RequestBody CategoryRequest req) {
        return tryCatch(() -> {
            Category category = processor.process(id);
            Category c = mapper.map(req);
            c.setId(category.getId());
            if (processor.service().update(c, category)) {
                return noContent();
            }
            throw HttpException.internalServerError(
                    "Cannot update category with id [" + id + ']'
            );
        });
    }

}

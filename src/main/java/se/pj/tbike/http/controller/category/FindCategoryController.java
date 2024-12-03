package se.pj.tbike.http.controller.category;

import com.ank.japi.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.Category;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.helper.FindCategoryProcessor;
import se.pj.tbike.http.model.category.CategoryMapper;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.FIND_CATEGORY_PATH)
@RestController
public class FindCategoryController extends BaseController {

    private final FindCategoryProcessor processor;
    private final CategoryMapper mapper;

    public FindCategoryController(
            ResponseConfigurer configurer,
            FindCategoryProcessor processor,
            CategoryMapper mapper
    ) {
        super(configurer);
        this.processor = processor;
        this.mapper = mapper;
    }

    @GetMapping({"", "/"})
    public Response find(@PathVariable String id) {
        return tryCatch(() -> {
            Category category = processor.process(id);
            return ok(mapper.map(category));
        });
    }

}

package se.pj.tbike.http.controller.category;

import com.ank.japi.Response;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.category.CategoryMapper;
import se.pj.tbike.http.model.category.CategoryRequest;
import se.pj.tbike.domain.service.CategoryService;
import se.pj.tbike.domain.entity.Category;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.CREATE_CATEGORY_PATH)
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class CreateCategoryController extends BaseController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    public CreateCategoryController(
            ResponseConfigurer configurer,
            CategoryService service,
            CategoryMapper mapper
    ) {
        super(configurer);
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping({"", "/"})
    public Response create(@RequestBody CategoryRequest req) {
        return tryCatch(() -> {
            Category category = mapper.map(req);
            Category saved = service.create(category);
            return created(saved.getId());
        });
    }
}

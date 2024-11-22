package se.pj.tbike.core.api.category.controller;

import com.ank.japi.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.core.api.category.dto.CategoryMapper;
import se.pj.tbike.core.api.category.entity.Category;
import se.pj.tbike.core.api.category.data.CategoryService;
import se.pj.tbike.core.api.category.dto.CategoryResponse;
import se.pj.tbike.core.util.PageableParameters;
import se.pj.tbike.core.util.PageableController;
import se.pj.tbike.util.Output.Array;

import java.util.Collection;

@RequestMapping(QueryCategoryController.API_URL)
@RestController
@RequiredArgsConstructor
public class QueryCategoryController
        implements PageableController {

    public static final String API_URL = "/api/categories";

    private final CategoryService service;
    private final CategoryMapper mapper;

    @Override
    public Config configure() {
        return new Config() {
            @Override
            public int basedPageSize() {
                return 0;
            }

            @Override
            public int basedPageNumber() {
                return 0;
            }
        };
    }

    @GetMapping({"", "/"})
    public Response<Collection<CategoryResponse>> query(
            PageableParameters parameters
    ) {
        return paginated(parameters, (page, size) -> {
            Array<Category> o;
            if (size == 0) {
                o = service.findAll();
            } else {
                o = service.findPage(page, size);
            }
            return o.map(mapper::map);
        });
    }
}

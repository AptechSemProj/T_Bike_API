package se.pj.tbike.api.category.controller;

import com.ank.japi.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.api.category.dto.CategoryMapper;
import se.pj.tbike.api.category.entity.Category;
import se.pj.tbike.api.category.data.CategoryService;
import se.pj.tbike.api.category.dto.CategoryResponse;
import se.pj.tbike.util.PageableParameters;
import se.pj.tbike.util.PageableController;

import java.util.List;

@RequestMapping(QueryCategoryController.API_URL)
@RestController
@RequiredArgsConstructor
public class QueryCategoryController
        implements PageableController<CategoryResponse> {

    public static final String API_URL = "/api/categories";

    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping({"", "/"})
    public Response<List<CategoryResponse>> query(
            PageableParameters parameters
    ) {
        return paginated(parameters, (page, size) -> {
            Page<Category> paged;
            if (size == defaultPageSize()) {
                paged = new PageImpl<>(service.findAll());
            } else {
                paged = service.findPage(PageRequest.of(page, size));
            }
            return paged.map(mapper::map);
        });
    }

    @Override
    public int defaultPageSize() {
        return 0;
    }
}

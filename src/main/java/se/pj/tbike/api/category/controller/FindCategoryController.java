package se.pj.tbike.api.category.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.category.data.CategoryService;
import se.pj.tbike.api.category.dto.CategoryMapper;
import se.pj.tbike.api.category.dto.CategoryResponse;
import se.pj.tbike.api.category.entity.Category;

import java.util.Optional;

@RequestMapping(FindCategoryController.API_URL)
@RestController
@RequiredArgsConstructor
public class FindCategoryController {

    public static final String API_URL = "/api/categories/{id}";

    private final RequestHandler<?, CategoryResponse> requestHandler;
    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping({"", "/"})
    public Response<CategoryResponse> find(@PathVariable String id) {
        return requestHandler.handle(null, (res, req) -> {
            Long categoryId;
            try {
                categoryId = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException e) {
                return res.badRequest(
                        "Cannot convert [" + id + "] to number."
                );
            }
            Optional<Category> o = service.findByKey(categoryId);
            if (o.isPresent()) {
                return res.ok(mapper.map(o.get()));
            }
            return res.notFound("Category with id [" + id + "] not found");
        });
    }

}

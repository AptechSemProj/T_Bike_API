package se.pj.tbike.api.category.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.category.data.CategoryService;
import se.pj.tbike.api.category.dto.CategoryMapper;
import se.pj.tbike.api.category.dto.CategoryRequest;
import se.pj.tbike.api.category.entity.Category;

import java.util.Optional;

@RequestMapping(UpdateCategoryController.API_URL)
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor
public class UpdateCategoryController {

    public static final String API_URL = "/api/categories/{id}";

    private final RequestHandler<CategoryRequest, Long> requestHandler;
    private final CategoryService service;
    private final CategoryMapper mapper;

    @SuppressWarnings("DuplicatedCode")
    @PutMapping({"", "/"})
    public Response<Long> update(@PathVariable String id,
                                 @RequestBody CategoryRequest request) {
        return requestHandler.handle(request, (res, req) -> {
            Long categoryId;
            try {
                categoryId = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException ex) {
                return res.badRequest(
                        "Cannot convert [" + id + "] to number."
                );
            }
            Optional<Category> o = service.findByKey(categoryId);
            if (o.isEmpty()) {
                return res.notFound(
                        "Category with id [" + categoryId + "] not found"
                );
            }
            Category category = o.get();
            Category c = mapper.map(req);
            c.setId(category.getId());
            if (service.update(c, category)) {
                return res.noContent();
            }
            return res.internalServerError(
                    "Cannot update category with id [" + categoryId + ']'
            );
        });
    }

}

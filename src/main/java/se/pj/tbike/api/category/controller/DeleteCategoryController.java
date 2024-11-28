package se.pj.tbike.api.category.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.category.data.CategoryService;
import se.pj.tbike.api.category.dto.CategoryRequest;
import se.pj.tbike.api.category.entity.Category;

import java.util.Optional;

@RequestMapping(DeleteCategoryController.API_URL)
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor
public class DeleteCategoryController {

    public static final String API_URL = "/api/categories/{id}";

    private final RequestHandler<CategoryRequest, Long> requestHandler;
    private final CategoryService service;

    @DeleteMapping({"", "/"})
    public Response<Long> delete(@PathVariable String id) {
        return requestHandler.handle(null, (res, body) -> {
            Long categoryId;
            try {
                categoryId = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException e) {
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
            if (service.delete(o.get())) {
                return res.noContent();
            }
            return res.internalServerError(
                    "Cannot delete category with id [" + categoryId + ']'
            );
        });
    }
}

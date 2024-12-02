package se.pj.tbike.http.controller.admin.category;

import com.ank.japi.HttpStatus;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.http.model.category.CategoryMapper;
import se.pj.tbike.http.model.category.CategoryRequest;
import se.pj.tbike.domain.service.CategoryService;
import se.pj.tbike.domain.entity.Category;

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
    public Response update(@PathVariable String id,
                                 @RequestBody CategoryRequest request) {
        return requestHandler.handle(request, (res, req) -> {
            Long categoryId;
            try {
                categoryId = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException ex) {
                throw new HttpException(
                        HttpStatus.BAD_REQUEST,
                        "Cannot convert [" + id + "] to number."
                );
            }
            Optional<Category> o = service.findByKey(categoryId);
            if (o.isEmpty()) {
                throw new HttpException(
                        HttpStatus.NOT_FOUND,
                        "Category with id [" + categoryId + "] not found"
                );
            }
            Category category = o.get();
            Category c = mapper.map(req);
            c.setId(category.getId());
            if (service.update(c, category)) {
                return res.noContent();
            }
            throw new HttpException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cannot update category with id [" + categoryId + ']'
            );
        });
    }

}

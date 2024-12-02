package se.pj.tbike.http.controller.category;

import com.ank.japi.HttpStatus;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.service.CategoryService;
import se.pj.tbike.domain.entity.Category;
import se.pj.tbike.http.model.category.CategoryMapper;
import se.pj.tbike.http.model.category.CategoryResponse;

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
    public Response find(@PathVariable String id) {
        return requestHandler.handle(null, (res, req) -> {
            Long categoryId;
            try {
                categoryId = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException e) {
                throw new HttpException(
                        HttpStatus.BAD_REQUEST,
                        "Cannot convert [" + id + "] to number."
                );
            }
            Optional<Category> o = service.findByKey(categoryId);
            if (o.isPresent()) {
                return res.ok(mapper.map(o.get()));
            }
            throw new HttpException(
                    HttpStatus.NOT_FOUND,
                    "Category with id [" + id + "] not found"
            );
        });
    }

}

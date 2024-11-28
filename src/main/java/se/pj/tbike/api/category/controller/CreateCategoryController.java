package se.pj.tbike.api.category.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.validation.Requirements;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.category.data.CategoryService;
import se.pj.tbike.api.category.dto.CategoryMapper;
import se.pj.tbike.api.category.dto.CategoryRequest;
import se.pj.tbike.api.category.entity.Category;

@RequestMapping(CreateCategoryController.API_URL)
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor
public class CreateCategoryController {

    public static final String API_URL = "/api/categories";

    private final RequestHandler<CategoryRequest, Long> requestHandler;
    private final CategoryService service;
    private final CategoryMapper mapper;

    @PostMapping({"", "/"})
    public Response<Long> create(@RequestBody CategoryRequest request) {
        return requestHandler.handle(request, (res, req) -> {
            Category c = mapper.map(req);
            Category saved = service.create(c);
            return res.created(saved.getId());
        }, (req, validators) -> {
            validators.bind("name", req.getName())
                    .require(Requirements.notBlank(300));
            validators.bind("imageUrl", req.getImageUrl())
                    .require(Requirements.notBlank());
        });
    }
}

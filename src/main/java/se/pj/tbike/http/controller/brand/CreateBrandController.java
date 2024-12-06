package se.pj.tbike.http.controller.brand;

import com.ank.japi.Response;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.Brand;
import se.pj.tbike.domain.service.BrandService;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.brand.BrandMapper;
import se.pj.tbike.http.model.brand.CreateBrandRequest;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.CREATE_BRAND_PATH)
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class CreateBrandController extends BaseController {

    private final BrandService service;
    private final BrandMapper mapper;

    public CreateBrandController(
            ResponseConfigurer configurer,
            BrandService service,
            BrandMapper mapper
    ) {
        super(configurer);
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping({"", "/"})
    public Response create(@RequestBody CreateBrandRequest request) {
        return tryCatch(() -> {
            Brand brand = mapper.map(request);
            Brand created = service.create(brand);
            return created(created.getId());
        });
    }
}

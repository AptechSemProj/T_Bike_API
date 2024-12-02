package se.pj.tbike.http.controller.brand;

import com.ank.japi.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.Brand;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.helper.FindBrandProcessor;
import se.pj.tbike.http.model.brand.BrandMapper;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.FIND_BRAND_PATH)
@RestController
public
class FindBrandController extends BaseController {

    private final FindBrandProcessor processor;
    private final BrandMapper mapper;

    public FindBrandController(
            ResponseConfigurer configurer,
            FindBrandProcessor processor,
            BrandMapper mapper
    ) {
        super(configurer);
        this.processor = processor;
        this.mapper = mapper;
    }

    @GetMapping({"", "/"})
    public Response handle(@PathVariable String id) {
        return tryCatch(() -> {
            Brand brand = processor.process(id);
            return ok(mapper.map(brand));
        });
    }
}

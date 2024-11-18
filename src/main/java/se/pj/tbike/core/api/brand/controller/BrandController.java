package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.core.api.brand.conf.BrandApiUrls;
import se.pj.tbike.core.config.Urls;
import se.pj.tbike.core.api.brand.conf.BrandMapper;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.io.Arr;
import se.pj.tbike.io.Response;
import se.pj.tbike.core.util.PageableController;
import se.pj.tbike.util.Output;
import com.ank.japi.validation.Requirements;
import com.ank.japi.validation.ValidationResult;

@RequiredArgsConstructor
@RestController
@RequestMapping({BrandApiUrls.BRAND_API})
public class BrandController
        implements PageableController {

    private final BrandService service;
    private final BrandMapper brandMapper;

    @Override
    public ValidationResult validatePageSize(Object pageSize) {
        Validator validator = new Validator();
        validator.accept(Requirements.positiveInt(false, true));
        return validator.validate(pageSize);
    }

    @GetMapping({Urls.URL_LIST_1, Urls.URL_LIST_2})
    public Response<Arr<BrandResponse>> getList(
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "0") String size
    ) {
        return paginated(page, size, (p, s) -> {
            Output.Array<Brand> o;
            if (s == 0) {
                o = service.findAll();
            } else {
                o = service.findPage(p, s);
            }
            return o.map(brandMapper::map);
        });
    }
}

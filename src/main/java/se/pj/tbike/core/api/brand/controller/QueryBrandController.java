package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.core.api.brand.conf.BrandApiUrls;
import se.pj.tbike.core.api.brand.dto.BrandMapper;
import se.pj.tbike.core.config.Urls;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.util.PageableParameters;
import se.pj.tbike.core.util.PageableController;
import se.pj.tbike.util.Output.Array;

import java.util.Collection;

@RequestMapping({BrandApiUrls.BRAND_API})
@RestController
@RequiredArgsConstructor
public class QueryBrandController
        implements PageableController {

    private final BrandService service;
    private final BrandMapper brandMapper;

    @Override
    public Config configure() {
        return new Config() {
            @Override
            public int basedPageSize() {
                return 0;
            }

            @Override
            public int basedPageNumber() {
                return 0;
            }
        };
    }

    @GetMapping({Urls.URL_LIST_1, Urls.URL_LIST_2})
    public Response<Collection<BrandResponse>> getList(
            PageableParameters parameters
//            @RequestParam(defaultValue = "0") String page,
//            @RequestParam(defaultValue = "0") String size
    ) {
        return paginated(parameters, (page, size) -> {
            Array<Brand> array;
            if (size == 0) {
                array = service.findAll();
            } else {
                array = service.findPage(page, size);
            }
            return array.map(brandMapper::map);
        });
    }
}

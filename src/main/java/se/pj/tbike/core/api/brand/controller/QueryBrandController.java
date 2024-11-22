package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.core.api.brand.dto.BrandMapper;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.util.PageableParameters;
import se.pj.tbike.core.util.PageableController;
import se.pj.tbike.util.Output.Array;

import java.util.Collection;

@RequestMapping(QueryBrandController.API_URL)
@RestController
@RequiredArgsConstructor
public class QueryBrandController
        implements PageableController {

    public static final String API_URL = "/api/brands";

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

    @GetMapping({"", "/"})
    public Response<Collection<BrandResponse>> query(
            PageableParameters parameters
    ) {
        return paginated(parameters, (page, size) -> {
            Array<Brand> o;
            if (size == 0) {
                o = service.findAll();
            } else {
                o = service.findPage(page, size);
            }
            return o.map(brandMapper::map);
        });
    }
}

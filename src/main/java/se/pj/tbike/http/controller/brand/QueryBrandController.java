package se.pj.tbike.http.controller.brand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.brand.BrandMapper;
import se.pj.tbike.http.model.brand.BrandResponse;
import se.pj.tbike.domain.service.BrandService;
import se.pj.tbike.domain.entity.Brand;
import se.pj.tbike.impl.Response;
import se.pj.tbike.util.PageableParameters;
import se.pj.tbike.util.PageableController;

import java.util.List;

@RequestMapping(Routes.QUERY_BRAND_PATH)
@RestController
@RequiredArgsConstructor
public class QueryBrandController
        implements PageableController<BrandResponse> {

    private final BrandService service;
    private final BrandMapper mapper;

    @GetMapping({"", "/"})
    public Response<List<BrandResponse>> query(
            PageableParameters parameters
    ) {
        return paginated(parameters, (page, size) -> {
            Page<Brand> paged;
            if (size == defaultPageSize()) {
                paged = new PageImpl<>(service.findAll());
            } else {
                paged = service.findPage(PageRequest.of(page, size));
            }
            return paged.map(mapper::map);
        });
    }

    @Override
    public int defaultPageSize() {
        return 0;
    }
}

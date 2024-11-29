package se.pj.tbike.api.brand.controller;

import com.ank.japi.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import se.pj.tbike.api.brand.dto.BrandMapper;
import se.pj.tbike.api.brand.data.BrandService;
import se.pj.tbike.api.brand.dto.BrandResponse;
import se.pj.tbike.api.brand.entity.Brand;
import se.pj.tbike.util.PageableParameters;
import se.pj.tbike.util.PageableController;

import java.util.List;

@RequestMapping(QueryBrandController.API_URL)
@RestController
@RequiredArgsConstructor
public class QueryBrandController
        implements PageableController<BrandResponse> {

    public static final String API_URL = "/api/brands";

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

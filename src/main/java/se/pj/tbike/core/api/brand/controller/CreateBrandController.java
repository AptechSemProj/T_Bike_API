package se.pj.tbike.core.api.brand.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.impl.StdRequestHandler;
import com.ank.japi.validation.Requirements;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.brand.conf.BrandApiUrls;
import se.pj.tbike.core.api.brand.conf.BrandMapper;
import se.pj.tbike.core.api.brand.dto.BrandRequest;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.config.Urls;
import se.pj.tbike.core.japi.impl.ResponseConfigurerImpl;

@RestController
public class CreateBrandController {

    private final RequestHandler<BrandRequest, Long> requestHandler;
    private final BrandService service;
    private final BrandMapper mapper;

    public CreateBrandController(
            BrandService service,
            BrandMapper mapper
    ) {
        this.service = service;
        this.mapper = mapper;
        this.requestHandler = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping({
            BrandApiUrls.BRAND_API,
            BrandApiUrls.BRAND_API + Urls.URL_LIST_2
    })
    public Response<Long> handle(@RequestBody BrandRequest request) {
        return requestHandler.handle(request, (res, req) -> {
            Brand brand = mapper.map(request);
            Brand created = service.create(brand);
            return res.created(created.getId());
        }, (req, validators) -> {
            validators.bind("name", req.getName())
                    .require(Requirements.notBlank(400));
            validators.bind("imageUrl", req.getImageUrl())
                    .require(Requirements.notBlank());
        });
    }
}

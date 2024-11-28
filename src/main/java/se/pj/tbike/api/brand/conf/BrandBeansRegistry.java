package se.pj.tbike.api.brand.conf;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.api.brand.data.BrandRepository;
import se.pj.tbike.api.brand.data.BrandService;
import se.pj.tbike.api.brand.dto.BrandMapper;
import se.pj.tbike.api.brand.dto.BrandRequest;
import se.pj.tbike.api.brand.dto.BrandResponse;
import se.pj.tbike.api.brand.impl.BrandMapperImpl;
import se.pj.tbike.api.brand.impl.BrandServiceImpl;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@Configuration
public class BrandBeansRegistry {

    @Bean
    public BrandService brandService(BrandRepository repository) {
        return new BrandServiceImpl(repository);
    }

    @Bean
    public BrandMapper brandMapper() {
        return new BrandMapperImpl();
    }

    @Bean
    public RequestHandler<BrandRequest, Long> modifyBrandHandler() {
        return new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }

    @Bean
    public RequestHandler<?, BrandResponse> findBrandHandler() {
        return new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }
}

package se.pj.tbike.api.brand.conf;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.api.brand.dto.BrandRequest;
import se.pj.tbike.api.brand.dto.BrandResponse;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@Configuration
public class BrandBeansRegistry {

    @Bean
    public RequestHandler<BrandRequest, Long> modifyBrandHandler() {
        return new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }

    @Bean
    public RequestHandler<?, BrandResponse> findBrandHandler() {
        return new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }
}

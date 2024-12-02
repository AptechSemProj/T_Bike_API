package se.pj.tbike.http.controller.category;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.http.model.category.CategoryRequest;
import se.pj.tbike.http.model.category.CategoryResponse;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@Configuration
public class CategoryBeansRegistry {

    @Bean
    public RequestHandler<CategoryRequest, Long> modifyCategoryHandler() {
        return new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }

    @Bean
    public RequestHandler<?, CategoryResponse> findCategoryHandler() {
        return new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }
}

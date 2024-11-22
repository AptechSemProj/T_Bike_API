package se.pj.tbike.core.api.category.conf;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.api.category.data.CategoryRepository;
import se.pj.tbike.core.api.category.data.CategoryService;
import se.pj.tbike.core.api.category.dto.CategoryMapper;
import se.pj.tbike.core.api.category.dto.CategoryRequest;
import se.pj.tbike.core.api.category.dto.CategoryResponse;
import se.pj.tbike.core.api.category.impl.CategoryMapperImpl;
import se.pj.tbike.core.api.category.impl.CategoryServiceImpl;
import se.pj.tbike.core.japi.impl.ResponseConfigurerImpl;

@Configuration
public class CategoryBeansRegistry {

    @Bean
    public CategoryService categoryService(CategoryRepository repository) {
        return new CategoryServiceImpl(repository);
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapperImpl();
    }

    @Bean
    public RequestHandler<CategoryRequest, Long> modifyCategoryHandler() {
        return new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }

    @Bean
    public RequestHandler<?, CategoryResponse> findCategoryHandler() {
        return new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }
}

package se.pj.tbike.core.api.category.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.api.category.data.CategoryRepository;
import se.pj.tbike.core.api.category.data.CategoryService;
import se.pj.tbike.core.api.category.data.CategoryServiceImpl;

@Configuration
public class CategoryServiceConf {

	@Bean
	public CategoryService categoryService(CategoryRepository repository) {
		return new CategoryServiceImpl( repository );
	}
}

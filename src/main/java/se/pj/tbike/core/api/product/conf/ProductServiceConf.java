package se.pj.tbike.core.api.product.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.api.product.data.ProductRepository;
import se.pj.tbike.core.api.product.data.ProductService;
import se.pj.tbike.core.api.product.data.ProductServiceImpl;
import se.pj.tbike.core.api.attribute.data.AttributeRepository;

@Configuration
public class ProductServiceConf {

	@Bean
	public ProductService productService(ProductRepository repository,
	                                     AttributeRepository attributeRepository) {
		return new ProductServiceImpl( repository, attributeRepository );
	}
}

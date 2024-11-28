package se.pj.tbike.api.product.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.api.attribute.data.AttributeService;
import se.pj.tbike.api.product.data.ProductRepository;
import se.pj.tbike.api.product.data.ProductService;
import se.pj.tbike.api.product.impl.ProductServiceImpl;

@Configuration
public class ProductServiceConf {

    @Bean
    public ProductService productService(ProductRepository repository,
                                         AttributeService attributeService) {
        return new ProductServiceImpl(repository, attributeService);
    }
}

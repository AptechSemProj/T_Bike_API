package se.pj.tbike.core.api.attribute.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.api.attribute.data.AttributeRepository;
import se.pj.tbike.core.api.attribute.data.AttributeService;
import se.pj.tbike.core.api.attribute.dto.AttributeMapper;
import se.pj.tbike.core.api.attribute.impl.AttributeMapperImpl;
import se.pj.tbike.core.api.attribute.impl.AttributeServiceImpl;

@Configuration
public class AttributeBeansRegistry {

    @Bean
    public AttributeService attributeService(AttributeRepository repository) {
        return new AttributeServiceImpl(repository);
    }

    @Bean
    public AttributeMapper attributeMapper() {
        return new AttributeMapperImpl();
    }

}

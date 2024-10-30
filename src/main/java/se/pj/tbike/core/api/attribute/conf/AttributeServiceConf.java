package se.pj.tbike.core.api.attribute.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.api.attribute.data.AttributeRepository;
import se.pj.tbike.core.api.attribute.data.AttributeService;
import se.pj.tbike.core.api.attribute.data.AttributeServiceImpl;

@Configuration
public class AttributeServiceConf {

	@Bean
	public AttributeService attributeService(AttributeRepository repository) {
		return new AttributeServiceImpl( repository );
	}
}

package se.pj.tbike.core.api.brand.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.api.brand.data.BrandRepository;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.data.BrandServiceImpl;

@Configuration
public class BrandServiceConf {

	@Bean
	public BrandService brandService(BrandRepository repository) {
		return new BrandServiceImpl( repository );
	}
}

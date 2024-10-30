package se.pj.tbike.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.io.Response;
import se.pj.tbike.validation.error.ExistedError;
import se.pj.tbike.validation.error.InvalidError;
import se.pj.tbike.validation.error.MaxError;
import se.pj.tbike.validation.error.MinError;
import se.pj.tbike.validation.error.NanError;
import se.pj.tbike.validation.error.NotExistError;
import se.pj.tbike.validation.error.NullError;
import se.pj.tbike.validation.error.UnexpectedError;

@Configuration
public class ResponseMappingConf {

	@Bean
	public ResponseMapping responseErrorMapping() {
		ResponseMapping map = new ResponseMapping();
		map.addMapping( ExistedError.builder(), Response::badRequest );
		map.addMapping( InvalidError.builder(), Response::badRequest );
		map.addMapping( MaxError.builder(), Response::badRequest );
		map.addMapping( MinError.builder(), Response::badRequest );
		map.addMapping( NotExistError.builder(), Response::notFound );
		map.addMapping( NullError.builder(), Response::badRequest );
		map.addMapping( NanError.builder(), Response::badRequest );
		map.addMapping( UnexpectedError.builder(), Response::badRequest );
		return map;
	}
}

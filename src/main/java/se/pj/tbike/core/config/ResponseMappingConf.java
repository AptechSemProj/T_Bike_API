package se.pj.tbike.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.io.Response;
import se.pj.tbike.validation.error.AlreadyExistsError;
import se.pj.tbike.validation.error.MaximumOverflowError;
import se.pj.tbike.validation.error.MinimumOverflowError;
import se.pj.tbike.validation.error.NegativeNumberError;
import se.pj.tbike.validation.error.NoContentError;
import se.pj.tbike.validation.error.NotExistError;
import se.pj.tbike.validation.error.NumberFormatError;
import se.pj.tbike.validation.error.PositiveNumberError;
import se.pj.tbike.validation.error.UnexpectedTypeError;
import se.pj.tbike.validation.error.UnexpectedValueError;
import se.pj.tbike.validation.error.UnknownError;

@Configuration
public class ResponseMappingConf {

	@Bean
	public ResponseMapping responseErrorMapping() {
		ResponseMapping map = new ResponseMapping();
		map.addMapping( AlreadyExistsError.builder(), Response::badRequest );
		map.addMapping( MaximumOverflowError.builder(), Response::badRequest );
		map.addMapping( MinimumOverflowError.builder(), Response::badRequest );
		map.addMapping( NegativeNumberError.builder(), Response::badRequest );
		map.addMapping( NoContentError.builder(), Response::badRequest );
		map.addMapping( NotExistError.builder(), Response::notFound );
		map.addMapping( NumberFormatError.builder(), Response::badRequest );
		map.addMapping( PositiveNumberError.builder(), Response::badRequest );
		map.addMapping( UnexpectedTypeError.builder(), Response::badRequest );
		map.addMapping( UnexpectedValueError.builder(), Response::badRequest );
		map.addMapping( UnknownError.builder(), Response::internalServerError );
		return map;
	}
}

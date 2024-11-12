package se.pj.tbike.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.util.ResponseMapping;
import se.pj.tbike.io.Response;
import com.ank.japi.validation.error.AlreadyExistsError;
import com.ank.japi.validation.error.MaximumOverflowError;
import com.ank.japi.validation.error.MinimumOverflowError;
import com.ank.japi.validation.error.NegativeNumberError;
import com.ank.japi.validation.error.NoContentError;
import com.ank.japi.validation.error.NotExistError;
import com.ank.japi.validation.error.NumberFormatError;
import com.ank.japi.validation.error.PositiveNumberError;
import com.ank.japi.validation.error.UnexpectedTypeError;
import com.ank.japi.validation.error.UnexpectedValueError;
import com.ank.japi.validation.error.UnknownError;

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

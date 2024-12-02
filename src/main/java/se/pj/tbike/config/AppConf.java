package se.pj.tbike.config;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

import com.ank.japi.Controller.ResponseConfigurer;
import com.ank.japi.Controller.ResponseConfigurer.Binding;
import com.ank.japi.exception.HttpException;
import com.ank.japi.validation.error.AlreadyExistsError;
import com.ank.japi.validation.error.NotExistError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import se.pj.tbike.impl.Response;

@Configuration
public class AppConf {

    @Bean
    public ResponseConfigurer responseConfigurer() {
        ResponseConfigurer configurer = new ResponseConfigurer();
        configurer.setErrorBinding(Binding.bind(
                AlreadyExistsError.class,
                Response.status(CONFLICT)::ofThrowable
        )).setErrorBinding(Binding.bind(
                NotExistError.class,
                Response.status(NOT_FOUND)::ofThrowable
        )).setErrorBinding(Binding.bind(
                com.ank.japi.validation.error.UnknownError.class,
                Response.status(NOT_IMPLEMENTED)::ofThrowable
        )).setErrorBinding(Binding.bind(
                HttpException.class,
                e -> Response.status(e.getStatusCode())
                        .message(e.getLocalizedMessage())
                        .build()
        )).setErrorBinding(Binding.bind(
                Throwable.class,
                Response.status(INTERNAL_SERVER_ERROR)::ofThrowable
        ));
        return configurer.setResponseBuilder((code, body) -> {
            HttpStatus status = HttpStatus.valueOf(code);
            return Response.status(status).build(body);
        });
    }
}

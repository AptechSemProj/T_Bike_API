package se.pj.tbike.core.util;

import com.ank.japi.validation.ValidationError;
import se.pj.tbike.io.Response;
import se.pj.tbike.io.ResponseType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class ResponseMapping {

    private final Map<Integer, Function<String, Response<?>>> mappings;

    public ResponseMapping() {
        this.mappings = new LinkedHashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T extends ResponseType> Response<T> getResponse(
            ValidationError error
    ) {
        if ( error == null ) {
            throw new IllegalArgumentException();
        }
        Function<String, Response<?>> func = mappings.get( error.getCode() );
        if ( func == null ) {
            throw new IllegalArgumentException();
        }
        return (Response<T>) func.apply( error.getReason() );
    }

    public void addMapping(
            ValidationError.Builder<?> error,
            Function<String, Response<?>> resp
    ) {
        if ( error == null ) {
            throw new IllegalArgumentException();
        }
        if ( resp == null ) {
            throw new IllegalArgumentException();
        }
        this.mappings.put( error.build().getCode(), resp );
    }
}

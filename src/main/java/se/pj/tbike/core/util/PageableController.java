package se.pj.tbike.core.util;

import com.ank.japi.Response;
import org.springframework.http.HttpStatus;
import se.pj.tbike.core.japi.impl.ResponseImpl;
import se.pj.tbike.io.Pagination.Metadata;
import se.pj.tbike.util.Output;
import se.pj.tbike.util.Output.Array;

import java.util.Collection;
import java.util.function.BiFunction;

public interface PageableController {

    interface Config {

        int basedPageSize();

        int basedPageNumber();

    }

    default Config configure() {
        return new Config() {
            @Override
            public int basedPageSize() {
                return 1;
            }

            @Override
            public int basedPageNumber() {
                return 0;
            }
        };
    }

    default <T> Response<Collection<T>> paginated(
            PageableParameters parameters,
            BiFunction<Integer, Integer, Array<T>> handler
    ) {
        if (handler == null) {
            throw new NullPointerException("handler is null");
        }
        Config config = configure();
        Array<T> array = handler.apply(
                parameters.getPageNumber(config.basedPageNumber()),
                parameters.getPageSize(config.basedPageSize())
        );
        ResponseImpl<Collection<T>> resp = ResponseImpl
                .<Collection<T>>status(HttpStatus.OK)
                .data(array.get())
                .build();
        if (array instanceof Output.Pagination<T> p) {
            Metadata metadata = new Metadata(
                    p.getTotalElements(), p.getTotalPages(),
                    p.getSize(), p.getNumber(),
                    p.next(), p.previous()
            );
            return resp.addExtraField("metadata", metadata.toJson());
        } else {
            return resp;
        }
    }
}

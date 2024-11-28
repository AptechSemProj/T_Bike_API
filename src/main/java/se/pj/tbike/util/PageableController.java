package se.pj.tbike.util;

import com.ank.japi.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import se.pj.tbike.impl.ResponseImpl;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @param <T> response type
 */
public interface PageableController<T> {

    Pageable defaultPageable();

    default Response<List<T>> paginated(
            PageableParameters params,
            BiFunction<Integer, Integer, Page<T>> handler
    ) {
        if (handler == null) {
            throw new NullPointerException("handler is null");
        }
        Pageable dp = defaultPageable();
        Page<T> page = handler.apply(
                params.getPageNumber(dp.getPageNumber()),
                params.getPageSize(dp.getPageSize())
        );
        ResponseImpl<List<T>> resp = ResponseImpl
                .status(HttpStatus.OK)
                .build(page.getContent());
        Pageable pageable = page.getPageable();
        if (pageable.isUnpaged()) {
            return resp;
        }
        Integer next = page.hasNext()
                ? page.nextPageable().getPageNumber()
                : null;
        Integer prev = page.hasPrevious()
                ? page.previousPageable().getPageNumber()
                : null;
        return resp.metadata(
                page.getTotalElements(), page.getTotalPages(),
                page.getSize(), page.getNumber(),
                next, prev
        );
    }
}

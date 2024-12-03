package se.pj.tbike.http.helper;

import com.ank.japi.exception.HttpException;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import se.pj.tbike.domain.entity.Category;
import se.pj.tbike.domain.service.CategoryService;

@Service
public record FindCategoryProcessor(CategoryService service) {

    public Category process(String id) {
        Long cid;
        try {
            cid = NumberUtils.parseNumber(id, Long.class);
        } catch (IllegalArgumentException e) {
            throw HttpException.badRequest(
                    "Cannot convert [" + id + "] to number."
            );
        }
        return service
                .findByKey(cid)
                .orElseThrow(() -> HttpException.notFound(
                        "Category with id [" + id + "] not found."
                ));
    }

}

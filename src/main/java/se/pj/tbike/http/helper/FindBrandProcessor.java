package se.pj.tbike.http.helper;

import com.ank.japi.exception.HttpException;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import se.pj.tbike.domain.entity.Brand;
import se.pj.tbike.domain.service.BrandService;

@Service
public
record FindBrandProcessor(BrandService service) {

    public Brand process(String id) {
        long bid;
        try {
            bid = NumberUtils.parseNumber(id, Long.class);
        } catch (IllegalArgumentException ex) {
            throw HttpException.badRequest(
                    "Cannot convert [" + id + "] to number."
            );
        }
        return service
                .findByKey(bid)
                .orElseThrow(() -> HttpException.notFound(
                        "Brand with id [" + bid + "] not found."
                ));
    }
}

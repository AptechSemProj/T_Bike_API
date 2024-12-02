package se.pj.tbike.http.model.brand;

import com.ank.japi.exception.HttpException;
import org.springframework.http.HttpStatus;
import se.pj.tbike.domain.entity.Brand;

public class UpdateBrandRequest extends CreateBrandRequest {

    private final Long id;

    public UpdateBrandRequest(
            Long id, String name,
            String imageUrl, String description
    ) {
        super(name, imageUrl, description);
        this.id = id;
    }

    public Long getId() {
        if (id != null && id < 1) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid id [" + id + "]"
            );
        }
        return id;
    }

    @Override
    public Brand toBrand() {
        Brand brand = super.toBrand();
        brand.setId(getId());
        return brand;
    }
}

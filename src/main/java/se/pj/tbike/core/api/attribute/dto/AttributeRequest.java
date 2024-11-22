package se.pj.tbike.core.api.attribute.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.ank.japi.Request;

@Getter
@AllArgsConstructor
public class AttributeRequest implements Request<Long> {

    private Long id;

    private String name;

    private String imageUrl;

    private long price;

    private int quantity;

    private Boolean represent;

    public boolean getRepresent() {
        return represent != null && represent;
    }
}

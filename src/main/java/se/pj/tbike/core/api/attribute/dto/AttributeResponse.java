package se.pj.tbike.core.api.attribute.dto;

import lombok.Getter;
import lombok.Setter;
import se.pj.tbike.io.ResponseType;

@Getter
@Setter
public class AttributeResponse
        implements ResponseType {

    private long id;

    private String name;

    private String imageUrl;

    private long price;

    private int quantity;

}

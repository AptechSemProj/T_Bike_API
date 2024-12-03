package se.pj.tbike.http.model.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeResponse {

    private long id;

    private String name;

    private String imageUrl;

    private long price;

    private int quantity;

}

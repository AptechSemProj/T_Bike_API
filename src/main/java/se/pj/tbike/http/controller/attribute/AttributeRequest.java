package se.pj.tbike.http.controller.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttributeRequest {

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

package se.pj.tbike.core.api.attribute.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import se.pj.tbike.core.api.orderdetail.entity.OrderDetail;
import se.pj.tbike.core.common.entity.IdentifiedEntity;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.util.Cacheable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
        name = "attributes",
        indexes = {
                @Index(
                        name = "idx_attribute_price_id",
                        columnList = "price, id"
                ),
                @Index(
                        name = "idx_attribute_product_id_id",
                        columnList = "product_id, id, represent"
                ),
                @Index(
                        name = "idx_attribute_represent_id",
                        columnList = "represent, id"
                )
        }
)
public class Attribute
        implements IdentifiedEntity<Attribute, Long>, Cacheable<Attribute> {

    //*************** BASIC ******************//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean represent;

    @Column(
            length = 50,
            nullable = false
    )
    private String color;

    @Column(
            name = "image_url",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String imageUrl;

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private long price;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int quantity;

    //*************** RELATIONSHIPS ******************//

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            updatable = false
    )
    private Product product;

    @Transient
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orders = new ArrayList<>();

    //*************** EQUALS & HASHCODE ******************//

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof Attribute that)) {
            return false;
        }
        return Objects.equals(id, that.id) &&
                represent == that.represent &&
                Objects.equals(color, that.color) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                price == that.price &&
                quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, represent, color, imageUrl, price, quantity);
    }

    //*************** IMPLEMENTS METHODS ******************//

    @Override
    public Map<String, Object> toCacheObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("represent", represent);
        map.put("color", color);
        map.put("imageUrl", imageUrl);
        map.put("price", price);
        map.put("quantity", quantity);
        return map;
    }

    @Override
    public Attribute fromCacheObject(Map<String, Object> cacheObject) {
        this.setId((Long) cacheObject.get("id"));
        this.setRepresent((Boolean) cacheObject.get("represent"));
        this.setColor((String) cacheObject.get("color"));
        this.setImageUrl((String) cacheObject.get("imageUrl"));
        this.setPrice((Long) cacheObject.get("price"));
        this.setQuantity((Integer) cacheObject.get("quantity"));
        return this;
    }
}

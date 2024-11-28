package se.pj.tbike.api.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import se.pj.tbike.api.attribute.entity.Attribute;
import se.pj.tbike.api.category.entity.Category;
import se.pj.tbike.common.entity.IdentifiedEntity;
import se.pj.tbike.api.brand.entity.Brand;
import se.pj.tbike.util.Cacheable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
        name = "products",
        indexes = {
                @Index(
                        name = "idx_product_name_brand_id",
                        columnList = "name, brand_id, category_id, id"
                ),
                @Index(
                        name = "idx_product_sku_id",
                        columnList = "sku, id"
                ),
                @Index(
                        name = "idx_product_brand_id",
                        columnList = "brand_id"
                ),
                @Index(
                        name = "idx_product_category_id",
                        columnList = "category_id"
                )
        }
)
public class Product
        implements IdentifiedEntity<Product, Long>, Cacheable<Product> {

    //** identifier **//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            length = 20
    )
    private String sku;

    //** basic information **//

    @Column(
            nullable = false
    )
    private String name;

    //** specifications **//

    @Column(
            length = 20,
            nullable = false
    )
    private String size;

    @Column(
            length = 200,
            nullable = false
    )
    private String frame;

    @Column(
            length = 200,
            nullable = false
    )
    private String saddle;

    @Column(
            name = "seat_post",
            length = 200,
            nullable = false
    )
    private String seatPost;

    @Column(
            length = 200
    )
    private String bell;

    @Column(
            length = 200
    )
    private String fork;

    @Column(
            length = 200
    )
    private String shock;

    //** steering system **//

    @Column(
            length = 200,
            nullable = false
    )
    private String handlebar;

    @Column(
            name = "handlebar_stem",
            length = 200,
            nullable = false
    )
    private String handlebarStem;

    //** power train system **//

    @Column(
            length = 200,
            nullable = false
    )
    private String pedal;

    @Column(
            length = 200,
            nullable = false
    )
    private String crankset;

    @Column(
            name = "bottom_bracket",
            length = 200,
            nullable = false
    )
    private String bottomBracket;

    @Column(
            length = 200,
            nullable = false
    )
    private String chain;

    @Column(
            name = "chain_guard",
            length = 200
    )
    private String chainGuard;

    @Column(
            length = 200,
            nullable = false
    )
    private String cassette;

    @Column(
            name = "front_derailleur",
            length = 200
    )
    private String frontDerailleur;

    @Column(
            name = "rear_derailleur",
            length = 200
    )
    private String rearDerailleur;

    //** motion system **//

    @Column(
            length = 200,
            nullable = false
    )
    private String rims;

    @Column(
            length = 200,
            nullable = false
    )
    private String hubs;

    @Column(
            length = 200,
            nullable = false
    )
    private String spokes;

    @Column(
            length = 200,
            nullable = false
    )
    private String tires;

    @Column(
            length = 200,
            nullable = false
    )
    private String valve;

    //** brake system **//

    @Column(
            length = 200,
            nullable = false
    )
    private String brakes;

    @Column(
            name = "brake_levers",
            length = 200,
            nullable = false
    )
    private String brakeLevers;

    //*************** RELATIONSHIPS ******************//

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "brand_id",
            nullable = false,
            updatable = false
    )
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            updatable = false
    )
    private Category category;

    @BatchSize(size = 20)
    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.EAGER
    )
    private List<Attribute> attributes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product that)) {
            return false;
        }
        return Objects.equals(id, that.id)
                && Objects.equals(sku, that.sku)
                && Objects.equals(name, that.name)
                && Objects.equals(size, that.size)
                && Objects.equals(frame, that.frame)
                && Objects.equals(saddle, that.saddle)
                && Objects.equals(seatPost, that.seatPost)
                && Objects.equals(bell, that.bell)
                && Objects.equals(fork, that.fork)
                && Objects.equals(shock, that.shock)
                && Objects.equals(handlebar, that.handlebar)
                && Objects.equals(handlebarStem, that.handlebarStem)
                && Objects.equals(pedal, that.pedal)
                && Objects.equals(crankset, that.crankset)
                && Objects.equals(bottomBracket, that.bottomBracket)
                && Objects.equals(chain, that.chain)
                && Objects.equals(chainGuard, that.chainGuard)
                && Objects.equals(cassette, that.cassette)
                && Objects.equals(frontDerailleur, that.frontDerailleur)
                && Objects.equals(rearDerailleur, that.rearDerailleur)
                && Objects.equals(rims, that.rims)
                && Objects.equals(hubs, that.hubs)
                && Objects.equals(spokes, that.spokes)
                && Objects.equals(tires, that.tires)
                && Objects.equals(valve, that.valve)
                && Objects.equals(brakes, that.brakes)
                && Objects.equals(brakeLevers, that.brakeLevers)
                && Objects.equals(brand, that.brand)
                && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sku, name, size, frame, saddle, seatPost,
                bell, fork, shock, handlebar, handlebarStem, pedal, crankset,
                bottomBracket, chain, chainGuard, cassette, frontDerailleur,
                rearDerailleur, rims, hubs, spokes, tires, valve, brakes,
                brakeLevers, brand, category);
    }

    //*************** IMPLEMENTS METHODS ******************//

    @Override
    public Map<String, Object> toCacheObject() {
        Map<String, Object> map = new HashMap<>();
        try {
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fn = field.getName();
                map.put(fn, field.get(this));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public Product fromCacheObject(Map<String, Object> cacheObject) {
        try {
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fn = field.getName();
                field.set(this, cacheObject.get(fn));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}

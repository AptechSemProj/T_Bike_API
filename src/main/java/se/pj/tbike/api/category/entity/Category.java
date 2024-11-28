package se.pj.tbike.api.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import se.pj.tbike.api.product.entity.Product;
import se.pj.tbike.util.Cacheable;
import se.pj.tbike.common.entity.SoftDeletionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(
                        name = "idx_category_name_id",
                        columnList = "name, id"
                ),
                @Index(
                        name = "idx_category_deleted_id",
                        columnList = "deleted, id"
                )
        }
)
public class Category
        implements SoftDeletionEntity<Category, Long>, Cacheable<Category> {

    //*************** BASIC ******************//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private boolean deleted;

    @Column(
            length = 300,
            nullable = false
    )
    private String name;

    @Column(
            name = "image_url",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    //*************** RELATIONSHIPS ******************//

    @Transient
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    //*************** EQUALS & HASHCODE ******************//

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category that)) {
            return false;
        }
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(description, that.description) &&
                Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, description, deleted);
    }

    //*************** IMPLEMENTS & OVERRIDE METHODS ******************//

    @Override
    public Map<String, Object> toCacheObject() {
        @SuppressWarnings("DuplicatedCode")
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("name", getName());
        map.put("imageUrl", getImageUrl());
        map.put("description", getDescription());
        return map;
    }

    @SuppressWarnings({"DuplicatedCode"})
    @Override
    public Category fromCacheObject(Map<String, Object> cacheObject) {
        setId((Long) cacheObject.get("id"));
        setName((String) cacheObject.get("name"));
        setImageUrl((String) cacheObject.get("imageUrl"));
        setDescription((String) cacheObject.get("description"));
        return this;
    }
}

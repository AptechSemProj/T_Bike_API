package se.pj.tbike.core.api.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.pj.tbike.core.api.order.entity.Order;
import se.pj.tbike.core.common.entity.SoftDeletionEntity;
import se.pj.tbike.core.util.Cacheable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(
                        name = "idx_user_account",
                        columnList = "username, password, deleted, id"
                ),
                @Index(
                        name = "idx_user_id_deleted",
                        columnList = "deleted, id"
                ),
                @Index(
                        name = "idx_user_username_id_deleted",
                        columnList = "username, id, deleted"
                )
        }
)
public class User
        implements SoftDeletionEntity<User, Long>,
        UserDetails,
        Cacheable<User> {

    //*************** BASIC ******************//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private boolean deleted;

    @Convert(converter = RoleConverter.class)
    @Column(nullable = false)
    private Role role;

    @Column(
            length = 100,
            nullable = false,
            unique = true
    )
    private String username;

    @Column(
            columnDefinition = "CHAR(60)",
            nullable = false
    )
    private String password;

    @Column(
            length = 100,
            nullable = false
    )
    private String name;

    @Column(
            name = "phone_number",
            columnDefinition = "CHAR(10)",
            nullable = false
    )
    private String phoneNumber;

    @Column(
            name = "avatar_image",
            columnDefinition = "TEXT"
    )
    private String avatarImage;

    //*************** RELATIONSHIPS ******************//

    @Transient
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    //*************** EQUALS & HASHCODE ******************//

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof User that)) {
            return false;
        }
        if (!(Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(avatarImage, that.avatarImage))) {
            return false;
        }
        if (orders != null) {
            if (that.orders == null) {
                return false;
            }
            int s = orders.size();
            if (s != that.orders.size()) {
                return false;
            }
            for (int i = 0; i < s; i++) {
                Order o1 = orders.get(i);
                Order o2 = that.orders.get(i);
                if (o1 == o2) {
                    continue;
                }
                if (o1 != null && o2 != null && Objects
                        .equals(o1.getId(), o2.getId())) {
                    continue;
                }
                return false;
            }
            return true;
        }
        return that.orders == null;
    }

    @Override
    public int hashCode() {
        int hashed = Objects.hash(super.hashCode(),
                username, password,
                name, phoneNumber, avatarImage
        );
        if (orders == null) {
            return hashed;
        }
        int s = orders.size();
        Long[] ids = new Long[s];
        for (int i = 0; i < s; i++) {
            Order o = orders.get(i);
            if (o != null) {
                ids[i] = o.getId();
            } else {
                ids[i] = 0L;
            }
        }
        return Objects.hash(hashed, Arrays.hashCode(ids));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    //*************** IMPLEMENTS METHODS ******************//

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }

    @Override
    public Map<String, Object> toCacheObject() {
        var map = new HashMap<String, Object>();
        map.put("id", getId());
        map.put("name", getName());
        map.put("phoneNumber", getPhoneNumber());
        map.put("avatarImage", getAvatarImage());
        map.put("deleted", isDeleted());
        map.put("role", getRole());
        map.put("username", getUsername());
//        map.put("password", getPassword());
        return map;
    }

    @Override
    public User fromCacheObject(Map<String, Object> cacheObject) {
        setId((Long) cacheObject.get("id"));
        setName((String) cacheObject.get("name"));
        setPhoneNumber((String) cacheObject.get("phoneNumber"));
        setAvatarImage((String) cacheObject.get("avatarImage"));
        setDeleted((Boolean) cacheObject.get("deleted"));
        setRole((Role) cacheObject.get("role"));
        setUsername((String) cacheObject.get("username"));
//        setPassword((String) cacheObject.get("password"));
        return this;
    }

}

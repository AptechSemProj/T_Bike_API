package se.pj.tbike.core.api.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import se.pj.tbike.core.common.entity.IdentifiedEntity;
import se.pj.tbike.core.api.orderdetail.entity.OrderDetail;
import se.pj.tbike.core.api.user.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(
        name = "orders",
        indexes = {
                @Index(
                        name = "idx_order_created_at_id",
                        columnList = "created_at, id"
                ),
                @Index(
                        name = "idx_order_user_id_id",
                        columnList = "user_id, id"
                )
        }
)
public class Order
        implements IdentifiedEntity<Order, Long> {

    //*************** BASIC ******************//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "total_amount",
            columnDefinition = "BIGINT",
            nullable = false
    )
    private long totalAmount;

    @Column(
            name = "created_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            updatable = false,
            insertable = false
    )
    private Timestamp createdAt;

    @Convert(converter = StatusConverter.class)
    @Column(nullable = false)
    private Status status = Status.CART;

    //*************** RELATIONSHIPS ******************//

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false
    )
    private User user;

    @BatchSize(size = 30)
    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.EAGER
    )
    private List<OrderDetail> details = new ArrayList<>();

    //*************** EQUALS & HASHCODE ******************//

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof Order that)) {
            return false;
        }
        if (!status.equals(that.status)) {
            return false;
        }
        if (user != null) {
            if (that.user == null) {
                return false;
            }
            if (!Objects.equals(user.getId(), that.user.getId())) {
                return false;
            }
        }
        if (details != null) {
            if (that.details == null) {
                return false;
            }
            int s = details.size();
            if (s != that.details.size()) {
                return false;
            }
            for (int i = 0; i < s; i++) {
                OrderDetail o1 = details.get(i);
                OrderDetail o2 = that.details.get(i);
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
        return that.details == null;
    }

    @Override
    public int hashCode() {
        int hashed = Objects.hash(super.hashCode(), user.getId(), status);
        if (details == null) {
            return hashed;
        }
        int s = details.size();
        OrderDetail.Id[] ids = new OrderDetail.Id[s];
        for (int i = 0; i < s; i++) {
            OrderDetail d = details.get(i);
            if (d != null) {
                ids[i] = d.getId();
            } else {
                ids[i] = new OrderDetail.Id();
            }
        }
        return Objects.hash(hashed, Arrays.hashCode(ids));
    }

}

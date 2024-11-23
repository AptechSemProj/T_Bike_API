package se.pj.tbike.core.api.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import se.pj.tbike.core.common.entity.IdentifiedEntity;
import se.pj.tbike.core.api.orderdetail.entity.OrderDetail;
import se.pj.tbike.core.api.user.entity.User;

import java.util.ArrayList;
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
    @Setter(AccessLevel.PRIVATE)
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order that)) {
            return false;
        }
        return totalAmount == that.totalAmount
                && Objects.equals(id, that.id)
                && Objects.equals(createdAt, that.createdAt)
                && status == that.status
                && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalAmount, createdAt, status, user);
    }

}

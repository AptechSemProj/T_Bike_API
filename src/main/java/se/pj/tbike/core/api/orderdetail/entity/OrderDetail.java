package se.pj.tbike.core.api.orderdetail.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.api.order.entity.Order;
import se.pj.tbike.core.common.entity.IdentifiedEntity;

import java.util.Objects;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(
        name = "order_details",
        indexes = {
                @Index(
                        name = "idx_order_detail_order_id",
                        columnList = "order_id"
                ),
                @Index(
                        name = "idx_order_detail_quantity",
                        columnList = "order_id, quantity, total_amount"
                )
        }
)
public class OrderDetail
        implements
        IdentifiedEntity<OrderDetail, OrderDetail.Id>,
        Comparable<OrderDetail> {

    //*************** BASIC ******************//

    @EmbeddedId
    private Id id;

    @Column(nullable = false)
    private int quantity;

    @Column(
            name = "total_amount",
            columnDefinition = "BIGINT",
            nullable = false
    )
    private long totalAmount;

    //*************** RELATIONSHIPS ******************//

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("orderId")
    @JoinColumn(
            name = "order_id",
            nullable = false,
            updatable = false
    )
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productId")
    @JoinColumn(
            name = "product_id",
            nullable = false,
            updatable = false
    )
    private Attribute product;

    //*************** EQUALS & HASHCODE ******************//


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetail that)) {
            return false;
        }
        return quantity == that.quantity
                && totalAmount == that.totalAmount
                && Objects.equals(id, that.id)
                && Objects.equals(order, that.order)
                && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, totalAmount, order, product);
    }

    @Override
    public int compareTo(@NonNull OrderDetail o) {
        return id.hashCode() - o.id.hashCode();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class Id
            implements Serializable, Comparable<Id> {

        @Column(
                name = "order_id",
                nullable = false,
                updatable = false
        )
        private long orderId;

        @Column(
                name = "product_id",
                nullable = false,
                updatable = false
        )
        private long productId;

        @Override
        public int compareTo(@NonNull Id o) {
            long l1 = orderId + productId;
            long l2 = o.orderId + o.productId;
            return Long.compare(l1, l2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Id that)) {
                return false;
            }
            return orderId == that.orderId
                    && productId == that.productId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, productId);
        }
    }
}

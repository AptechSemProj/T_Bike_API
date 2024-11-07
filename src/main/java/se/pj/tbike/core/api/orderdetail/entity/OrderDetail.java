package se.pj.tbike.core.api.orderdetail.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
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
import se.pj.tbike.core.api.order.entity.Order;
import se.pj.tbike.core.api.product.entity.Product;
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
						name = "FK__orderDetails_orderId__orders_id",
						columnList = "order_id"
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

	@Column(
			nullable = false
	)
	private int quantity;

	@Column(
			name = "total_amount",
			columnDefinition = "BIGINT UNSIGNED",
			nullable = false
	)
	private long totalAmount;

	//*************** RELATIONSHIPS ******************//

	@ManyToOne(
			fetch = FetchType.EAGER
	)
	@MapsId("orderId")
	@JoinColumn(
			name = "order_id",
			nullable = false,
			updatable = false,
			foreignKey = @ForeignKey(
					name = "FK__orderDetails_orderId__orders_id"
			)
	)
	private Order order;

	@ManyToOne(
			fetch = FetchType.EAGER
	)
	@MapsId("productId")
	@JoinColumn(
			name = "product_id",
			nullable = false,
			updatable = false,
			foreignKey = @ForeignKey(
					name = "FK__orderDetails_productId__products_id"
			)
	)
	private Product product;

	//*************** CONSTRUCTOR ******************//

	public OrderDetail() {
	}

	//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

//	@EmbeddedId
//	public Id getId() {
//		return id;
//	}

//	public void setId(Id id) {
//		this.id = id;
//	}

	@Override
	public int compareTo(@NonNull OrderDetail o) {
		return id.hashCode() - o.id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof OrderDetail that) ) {
			return false;
		}
		return Objects.equals( id, that.id ) &&
				quantity == that.quantity &&
				totalAmount == that.totalAmount;
	}

	@Override
	public int hashCode() {
		return Objects.hash( id, quantity, totalAmount );
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Embeddable
	public static class Id
			implements Serializable, Comparable<Id> {

		@Column(name = "order_id", nullable = false, updatable = false)
		private long orderId;

		@Column(name = "product_id", nullable = false, updatable = false)
		private long productId;

		@Override
		public int compareTo(@NonNull Id o) {
			long l1 = orderId + productId;
			long l2 = o.orderId + o.productId;
			return Long.compare( l1, l2 );
		}
	}
}

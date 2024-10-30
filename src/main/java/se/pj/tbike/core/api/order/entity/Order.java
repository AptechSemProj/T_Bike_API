package se.pj.tbike.core.api.order.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import se.pj.tbike.core.common.IdentifiedEntity;
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
		name = "orders"
)
public class Order
		extends IdentifiedEntity<Order> {

	//*************** BASIC ******************//

	@Column(
			name = "total_amount",
			columnDefinition = "BIGINT UNSIGNED",
			nullable = false
	)
	private long totalAmount;

	@Column(
			name = "created_at",
			columnDefinition = "DEFAULT CURRENT_TIMESTAMP",
			updatable = false,
			insertable = false
	)
	private Timestamp createdAt;

	@Column(
			nullable = false
	)
	@Enumerated(EnumType.STRING)
	@Convert(
			converter = StatusConverter.class
	)
	private Status status = Status.CART;

	//*************** RELATIONSHIPS ******************//

	@ManyToOne(
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "user_id",
			nullable = false,
			updatable = false,
			foreignKey = @ForeignKey(
					name = "FK__orders_userId__users_id"
			)
	)
	private User user;

	@OneToMany(
			mappedBy = "order",
			fetch = FetchType.EAGER
	)
	@BatchSize(
			size = 30
	)
	private List<OrderDetail> details = new ArrayList<>();

	//*************** CONSTRUCTOR ******************//

	public Order() {
	}

	//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

	@Override
	public boolean equals(Object o) {
		if ( !super.equals( o ) ) {
			return false;
		}
		if ( !(o instanceof Order that) ) {
			return false;
		}
		if ( !status.equals( that.status ) ) {
			return false;
		}
		if ( user != null ) {
			if ( that.user == null ) {
				return false;
			}
			if ( !Objects.equals( user.getId(), that.user.getId() ) ) {
				return false;
			}
		}
		if ( details != null ) {
			if ( that.details == null ) {
				return false;
			}
			int s = details.size();
			if ( s != that.details.size() ) {
				return false;
			}
			for ( int i = 0; i < s; i++ ) {
				OrderDetail o1 = details.get( i );
				OrderDetail o2 = that.details.get( i );
				if ( o1 == o2 ) {
					continue;
				}
				if ( o1 != null && o2 != null && Objects
						.equals( o1.getId(), o2.getId() ) ) {
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
		int hashed = Objects.hash( super.hashCode(), user.getId(), status );
		if ( details == null ) {
			return hashed;
		}
		int s = details.size();
		OrderDetail.Id[] ids = new OrderDetail.Id[s];
		for ( int i = 0; i < s; i++ ) {
			OrderDetail d = details.get( i );
			if ( d != null ) {
				ids[i] = d.getId();
			} else {
				ids[i] = new OrderDetail.Id();
			}
		}
		return Objects.hash( hashed, Arrays.hashCode( ids ) );
	}

	public enum Status {

		CART,
		PURCHASED,
		REFUNDED,
		WAITING,
		SHIPPING,
		SHIPPED,
		DONE,
	}

	@Converter
	private static class StatusConverter
			implements AttributeConverter<Status, String> {

		@Override
		public String convertToDatabaseColumn(Status status) {
			return status.name().toLowerCase();
		}

		@Override
		public Status convertToEntityAttribute(String value) {
			if ( value == null ) {
				throw new NullPointerException();
			}
			return Status.valueOf( value.toUpperCase() );
		}
	}
}

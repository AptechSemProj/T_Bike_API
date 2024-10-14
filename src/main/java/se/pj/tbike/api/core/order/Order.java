package se.pj.tbike.api.core.order;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.hash;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import se.pj.tbike.api.core.order.Order.Detail.Id;
import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.api.core.user.User;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import se.pj.tbike.api.common.entity.IdentifiedEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "orders" )
public class Order
		extends IdentifiedEntity<Order> {

	@Column( nullable = false )
	@Enumerated( EnumType.ORDINAL )
	@Convert( converter = Status.Converter.class )
	private Status status;

	//*************** RELATIONSHIPS ******************//

	@ManyToOne( fetch = EAGER )
	@JoinColumn( name = "user_id", nullable = false, updatable = false,
			foreignKey = @ForeignKey( name =
					"FK__orders_userId__users_id" ) )
	private User user;

	@OneToMany( mappedBy = "order", fetch = LAZY )
	private List<Detail> details = new ArrayList<>();

	//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) return true;
		if ( !( o instanceof Order that ) ) return false;
		if ( !( getId() == that.getId() && status.equals( that.status ) ) )
			return false;
		if ( user != null && (
				that.user == null ||
						user.getId() != that.user.getId() ) ) return false;
		if ( details != null ) {
			if ( that.details == null ) return false;
			int s = details.size();
			if ( s != that.details.size() ) return false;
			Id id1, id2;
			for ( int i = 0; i < s; i++ ) {
				id1 = details.get( i ).getId();
				id2 = that.details.get( i ).getId();
				if ( !id1.equals( id2 ) ) return false;
			}
			return true;
		}
		return that.details == null;
	}

	@Override
	public int hashCode() {
		int hashed = hash( getId(), user.getId(), status );
		if ( details == null ) return hashed;
		int s = details.size();
		Id[] ids = new Id[s];
		for ( int i = 0; i < s; i++ )
			ids[i] = details.get( i ).getId();
		return hash( hashed, Arrays.hashCode( ids ) );
	}


	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Entity
	@Table( name = "order_details", indexes = {
			@Index( name = "FK__orderDetails_orderId__orders_id",
					columnList = "order_id" )
	} )
	public static class Detail
			implements Comparable<Detail> {

		@EmbeddedId
		private Id id;

		@Column( nullable = false )
		private int quantity;

		@Column( name = "total_amount",
				columnDefinition = "BIGINT UNSIGNED NOT NULL" )
		private long totalAmount;

		//*************** RELATIONSHIPS ******************//

		@ManyToOne
		@MapsId( "orderId" )
		@JoinColumn( name = "order_id", nullable = false, updatable = false,
				foreignKey = @ForeignKey( name =
						"FK__orderDetails_orderId__orders_id" ) )
		private Order order;

		@ManyToOne
		@MapsId( "productId" )
		@JoinColumn( name = "product_id", nullable = false, updatable = false,
				foreignKey = @ForeignKey( name =
						"FK__orderDetails_productId__products_id" ) )
		private Product product;

		//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

		@Override
		public int compareTo( @NonNull Detail o ) {
			return id.hashCode() - o.id.hashCode();
		}

		@Override
		public boolean equals( Object o ) {
			if ( this == o ) return true;
			if ( !( o instanceof Detail that ) ) return false;
			return id.equals( that.id ) &&
					order.getId() == that.order.getId() &&
					product.getId() == that.product.getId() &&
					quantity == that.quantity &&
					totalAmount == that.totalAmount;
		}

		@Override
		public int hashCode() {
			return hash( id, order.getId(), product.getId(),
					quantity, totalAmount );
		}


		@Data
		@AllArgsConstructor
		@NoArgsConstructor
		@Embeddable
		public static class Id
				implements Serializable, Comparable<Id> {

			@Column( name = "order_id", nullable = false, updatable = false )
			private long orderId;

			@Column( name = "product_id", nullable = false, updatable = false )
			private long productId;

			@Override
			public int compareTo( @NonNull Id o ) {
				long l1 = orderId + productId, l2 = o.orderId + o.productId;
				return (int) ( l1 - l2 );
			}
		}
	}


	/**
	 * cart, purchased, refunded, waiting, shipping, shipped, done
	 */
	@Getter
	@AllArgsConstructor
	public enum Status {

		CART( 1, "Cart", "khi đồ chưa mua" ),
		PURCHASED( 2, "Purchased", "những đồ đã thanh toán" ),
		REFUNDED( 3, "Refunded", "những đồ chủ của hàng k thích giao cho " +
				"khách thì có cái này" ),
		WAITING( 4, "Waiting", "chờ lấy hàng / xử lý hoá đơn" ),
		SHIPPING( 5, "Shipping", "đang giao hàng" ),
		SHIPPED( 6, "Shipped", "đã giao" ),
		DONE( 7, "Done", "khách đã nhận hàng" ),
		;

		//*************** BASIC ******************//

		private final int id;

		private final String name;

		private final String description;

		//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

		public static Status valueOf( int id ) {
			return Stream.of( Status.values() )
					.filter( s -> s.id == id )
					.findFirst()
					.orElseThrow( IllegalArgumentException::new );
		}

		@jakarta.persistence.Converter
		private static class Converter
				implements AttributeConverter<Status, Integer> {

			@Override
			public Integer convertToDatabaseColumn( Status status ) {
				return status.id;
			}

			@Override
			public Status convertToEntityAttribute( Integer id ) {
				if ( id == null )
					throw new NullPointerException();
				return Status.valueOf( id );
			}
		}
	}
}

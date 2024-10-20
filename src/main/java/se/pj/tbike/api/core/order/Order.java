package se.pj.tbike.api.core.order;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.hash;

import java.io.Serializable;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Converter;
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

	@Column( name = "total_amount",
			columnDefinition = "BIGINT UNSIGNED NOT NULL" )
	private long totalAmount;

	@Column( name = "created_at",
			columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;

	@Column( nullable = false )
	@Enumerated( EnumType.STRING )
	@Convert( converter = Status.StatusConverter.class )
	private Status status = Status.CART;

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
		if ( !( Objects.equals( getId(), that.getId() ) &&
				status.equals( that.status ) ) )
			return false;
		if ( user != null && (
				that.user == null ||
						!Objects.equals( user.getId(), that.user.getId() ) ) )
			return false;
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
					Objects.equals( order.getId(), that.order.getId() ) &&
					Objects.equals( product.getId(), that.product.getId() ) &&
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

		CART,
		PURCHASED,
		REFUNDED,
		WAITING,
		SHIPPING,
		SHIPPED,
		DONE,
		;

		@Converter
		private static class StatusConverter
				implements AttributeConverter<Status, String> {

			@Override
			public String convertToDatabaseColumn( Status status ) {
				return status.name().toLowerCase();
			}

			@Override
			public Status convertToEntityAttribute( String value ) {
				if ( value == null )
					throw new NullPointerException();
				return Status.valueOf( value.toUpperCase() );
			}
		}
	}
}

package com.sem.proj.tbike.api.core.product;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.sem.proj.tbike.api.core.order.Order.Detail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.sem.proj.tbike.api.common.entity.IdentifiedEntity;
import com.sem.proj.tbike.api.core.brand.Brand;
import com.sem.proj.tbike.api.core.order.Order;
import com.sem.proj.tbike.api.core.order.Order.Detail.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "products" )
public class Product
		extends IdentifiedEntity<Product> {

	//** identifier **//

	@Column( length = 20, nullable = false, unique = true )
	private String sku;

	//** basic information **//

	@Column( length = 300, nullable = false )
	private String name;

	@Column( length = 20, nullable = false )
	private String size;

	//** specifications **//

	@Column( columnDefinition = "TEXT", nullable = false )
	private String frame;

	@Column( columnDefinition = "TEXT", nullable = false )
	private String saddle;

	@Column( name = "seat_post", columnDefinition = "TEXT", nullable = false )
	private String seatPost;

	@Column( columnDefinition = "TEXT" )
	private String bell;

	@Column( columnDefinition = "TEXT" )
	private String fork;

	@Column( columnDefinition = "TEXT" )
	private String shock;

	//** steering system **//

	@Column( columnDefinition = "TEXT", nullable = false )
	private String handlebar;

	@Column( name = "handlebar_stem", nullable = false,
			columnDefinition = "TEXT" )
	private String handlebarStem;

	//** power train system **//

	@Column( columnDefinition = "TEXT", nullable = false )
	private String pedal;

	@Column( columnDefinition = "TEXT", nullable = false )
	private String crankset;

	@Column( name = "bottom_bracket", nullable = false,
			columnDefinition = "TEXT" )
	private String bottomBracket;

	@Column( columnDefinition = "TEXT", nullable = false )
	private String chain;

	@Column( name = "chain_guard", columnDefinition = "TEXT" )
	private String chainGuard;

	@Column( columnDefinition = "TEXT", nullable = false )
	private String cassette;

	@Column( name = "front_derailleur", columnDefinition = "TEXT" )
	private String frontDerailleur;

	@Column( name = "rear_derailleur", columnDefinition = "TEXT" )
	private String rearDerailleur;

	//** motion system **//

	@Column( columnDefinition = "TEXT", nullable = false )
	private String rims;

	@Column( columnDefinition = "TEXT", nullable = false )
	private String hubs;

	@Column( columnDefinition = "TEXT", nullable = false )
	private String spokes;

	@Column( columnDefinition = "TEXT", nullable = false )
	private String tires;

	@Column( columnDefinition = "TEXT", nullable = false )
	private String valve;

	//** brake system **//

	@Column( columnDefinition = "TEXT", nullable = false )
	private String brakes;

	@Column( name = "brake_levers", nullable = false,
			columnDefinition = "TEXT" )
	private String brakeLevers;

	//*************** RELATIONSHIPS ******************//

	@ManyToOne
	@JoinColumn( name = "brand_id", nullable = false, updatable = false,
			foreignKey = @ForeignKey( name =
					"FK__products_brandId__brands_id" ) )
	private Brand brand;

	@ManyToOne
	@JoinColumn( name = "category_id", nullable = false, updatable = false,
			foreignKey = @ForeignKey( name =
					"FK__products_categoryId__categories_id" ) )
	private Category category;

	@OneToMany( mappedBy = "product", fetch = LAZY )
	private List<Attribute> attributes = new ArrayList<>();

	@OneToMany( mappedBy = "product", fetch = LAZY )
	private List<Order.Detail> orders = new ArrayList<>();

	//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) return true;
		if ( !( o instanceof Product that ) ) return false;
		return getId() == that.getId() &&
				brand.getId() == that.brand.getId() &&
				category.getId() == that.category.getId() &&
				compareAttrs( that.attributes ) &&
				compareOrders( that.orders ) &&
				Objects.equals( sku, that.sku ) &&
				Objects.equals( name, that.name ) &&
				Objects.equals( size, that.size ) &&
				Objects.equals( frame, that.frame ) &&
				Objects.equals( saddle, that.saddle ) &&
				Objects.equals( seatPost, that.seatPost ) &&
				Objects.equals( bell, that.bell ) &&
				Objects.equals( fork, that.fork ) &&
				Objects.equals( shock, that.shock ) &&
				Objects.equals( handlebar, that.handlebar ) &&
				Objects.equals( handlebarStem, that.handlebarStem ) &&
				Objects.equals( pedal, that.pedal ) &&
				Objects.equals( crankset, that.crankset ) &&
				Objects.equals( bottomBracket, that.bottomBracket ) &&
				Objects.equals( chain, that.chain ) &&
				Objects.equals( chainGuard, that.chainGuard ) &&
				Objects.equals( cassette, that.cassette ) &&
				Objects.equals( frontDerailleur, that.frontDerailleur ) &&
				Objects.equals( rearDerailleur, that.rearDerailleur ) &&
				Objects.equals( rims, that.rims ) &&
				Objects.equals( hubs, that.hubs ) &&
				Objects.equals( spokes, that.spokes ) &&
				Objects.equals( tires, that.tires ) &&
				Objects.equals( valve, that.valve ) &&
				Objects.equals( brakes, that.brakes ) &&
				Objects.equals( brakeLevers, that.brakeLevers );
	}

	private boolean compareAttrs( List<Attribute> that ) {
		if ( attributes != null ) {
			if ( that == null ) return false;
			int s = attributes.size();
			if ( s != that.size() ) return false;
			long id1, id2;
			for ( int i = 0; i < s; i++ ) {
				id1 = attributes.get( i ).getId();
				id2 = that.get( i ).getId();
				if ( id1 != id2 ) return false;
			}
			return true;
		}
		return false;
	}

	private boolean compareOrders( List<Detail> that ) {
		if ( orders != null ) {
			if ( that == null ) return false;
			int s = orders.size();
			if ( s != that.size() ) return false;
			Id id1, id2;
			for ( int i = 0; i < s; i++ ) {
				id1 = orders.get( i ).getId();
				id2 = that.get( i ).getId();
				if ( !id1.equals( id2 ) ) return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashed = hash( getId(), brand.getId(), category.getId(),
				sku, name,
				frame, saddle, seatPost, bell, fork, shock,
				handlebar, handlebarStem,
				pedal, crankset, bottomBracket, chain, chainGuard, cassette,
				frontDerailleur, rearDerailleur,
				rims, hubs, spokes, tires, valve,
				brakes, brakeLevers );
		int s;
		if ( attributes != null ) {
			long[] ids = new long[s = attributes.size()];
			for ( int i = 0; i < s; i++ )
				ids[i] = attributes.get( i ).getId();
			hashed = hash( hashed, Arrays.hashCode( ids ) );
		}
		if ( orders != null ) {
			Id[] ids = new Id[s = orders.size()];
			for ( int i = 0; i < s; i++ )
				ids[i] = orders.get( i ).getId();
			hashed = hash( hashed, Arrays.hashCode( ids ) );
		}
		return hashed;
	}


	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Entity
	@Table( name = "product_attributes" )
	public static class Attribute
			extends IdentifiedEntity<Attribute> {

		//*************** BASIC ******************//

		@Column( nullable = false )
		private boolean represent;

		@Column( length = 50, nullable = false )
		private String color;

		@Column( name = "image_url", nullable = false,
				columnDefinition = "TEXT" )
		private String imageUrl;

		@Column( columnDefinition = "BIGINT UNSIGNED DEFAULT 0" )
		private long price;

		@Column( columnDefinition = "DEFAULT 0" )
		private int quantity;

		//*************** RELATIONSHIPS ******************//

		@ManyToOne( fetch = EAGER )
		@JoinColumn( name = "product_id", nullable = false, updatable = false,
				foreignKey = @ForeignKey( name =
						"FK__productAttributes_productId__products_id" ) )
		private Product product;

		//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

		@Override
		public boolean equals( Object o ) {
			if ( this == o ) return true;
			if ( !( o instanceof Attribute that ) ) return false;
			return getId() == that.getId() &&
					product.getId() == that.product.getId() &&
					represent == that.represent &&
					Objects.equals( color, that.color ) &&
					Objects.equals( imageUrl, that.imageUrl ) &&
					price == that.price &&
					quantity == that.quantity;
		}

		@Override
		public int hashCode() {
			return hash( getId(), product.getId(),
					represent, color, imageUrl,
					price, quantity );
		}
	}


	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Entity
	@Table( name = "categories" )
	public static class Category
			extends IdentifiedEntity<Category> {

		//*************** BASIC ******************//

		@Column( length = 300, nullable = false )
		private String name;

		@Column( columnDefinition = "TEXT" )
		private String description;

		//*************** RELATIONSHIPS ******************//

		@OneToMany( mappedBy = "category", fetch = LAZY )
		private List<Product> products = new ArrayList<>();

		//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

		@Override
		public boolean equals( Object o ) {
			if ( this == o ) return true;
			if ( !( o instanceof Category that ) ) return false;
			if ( !( getId() == that.getId() &&
					Objects.equals( name, that.name ) &&
					Objects.equals( description, that.description ) ) )
				return false;
			if ( products != null ) {
				if ( that.products == null ) return false;
				int s = products.size();
				if ( s != that.products.size() ) return false;
				long id1, id2;
				for ( int i = 0; i < s; i++ ) {
					id1 = products.get( i ).getId();
					id2 = that.products.get( i ).getId();
					if ( id1 != id2 ) return false;
				}
				return true;
			}
			return that.products == null;
		}

		@Override
		public int hashCode() {
			int hashed = hash( getId(), name, description );
			if ( products == null ) return hashed;
			int s = products.size();
			long[] ids = new long[s];
			for ( int i = 0; i < s; i++ )
				ids[i] = products.get( i ).getId();
			return hash( hashed, Arrays.hashCode( ids ) );
		}
	}
}

package se.pj.tbike.api.core.product;

import static java.util.Objects.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.BatchSize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.order.Order;
import se.pj.tbike.api.core.order.Order.Detail;
import se.pj.tbike.api.core.order.Order.Detail.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "products" )
public class Product
		extends IdentifiedEntity<Product> {

	//** identifier **//

	@Column( length = 20 )
	private String sku;

	//** basic information **//

	@Column( nullable = false )
	private String name;

	//** specifications **//

	@Column( length = 20, nullable = false )
	private String size;

	@Column( length = 200, nullable = false )
	private String frame;

	@Column( length = 200, nullable = false )
	private String saddle;

	@Column( name = "seat_post", length = 200, nullable = false )
	private String seatPost;

	@Column( length = 200)
	private String bell;

	@Column( length = 200)
	private String fork;

	@Column( length = 200)
	private String shock;

	//** steering system **//

	@Column( length = 200, nullable = false )
	private String handlebar;

	@Column( name = "handlebar_stem", nullable = false,
			length = 200)
	private String handlebarStem;

	//** power train system **//

	@Column( length = 200, nullable = false )
	private String pedal;

	@Column( length = 200, nullable = false )
	private String crankset;

	@Column( name = "bottom_bracket", nullable = false,
			length = 200)
	private String bottomBracket;

	@Column( length = 200, nullable = false )
	private String chain;

	@Column( name = "chain_guard", length = 200)
	private String chainGuard;

	@Column( length = 200, nullable = false )
	private String cassette;

	@Column( name = "front_derailleur", length = 200)
	private String frontDerailleur;

	@Column( name = "rear_derailleur", length = 200)
	private String rearDerailleur;

	//** motion system **//

	@Column( length = 200, nullable = false )
	private String rims;

	@Column( length = 200, nullable = false )
	private String hubs;

	@Column( length = 200, nullable = false )
	private String spokes;

	@Column( length = 200, nullable = false )
	private String tires;

	@Column( length = 200, nullable = false )
	private String valve;

	//** brake system **//

	@Column( length = 200, nullable = false )
	private String brakes;

	@Column( name = "brake_levers", nullable = false,
			length = 200)
	private String brakeLevers;

	//*************** RELATIONSHIPS ******************//

	@ManyToOne( fetch = EAGER )
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
		return Objects.equals( getId(), that.getId() ) &&
				Objects.equals( brand.getId(), that.brand.getId() ) &&
				Objects.equals( category.getId(), that.category.getId() ) &&
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
			Long id1, id2;
			for ( int i = 0; i < s; i++ ) {
				id1 = attributes.get( i ).getId();
				id2 = that.get( i ).getId();
				if ( !Objects.equals( id1, id2 ) ) return false;
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
			Long[] ids = new Long[s = attributes.size()];
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

		//*************** CONSTRUCTOR ******************//

		public Attribute( long id, String color, String imageUrl,
		                  long price, int quantity ) {
			setId( id );
			this.color = color;
			this.imageUrl = imageUrl;
			this.price = price;
			this.quantity = quantity;
		}

		//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

		@Override
		public boolean equals( Object o ) {
			if ( this == o ) return true;
			if ( !( o instanceof Attribute that ) ) return false;
			return Objects.equals( getId(), that.getId() ) &&
					Objects.equals( product.getId(), that.product.getId() ) &&
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
	@Entity
	@Table( name = "categories" )
	public static class Category
			extends IdentifiedEntity<Category> {

		//*************** BASIC ******************//

		@Column( length = 300, nullable = false )
		private String name;

		@Column( name = "image_url", columnDefinition = "TEXT", nullable = false )
		private String imageUrl;

		@Column( columnDefinition = "TEXT" )
		private String description;

		//*************** RELATIONSHIPS ******************//

		@OneToMany( mappedBy = "category", fetch = EAGER )
		@BatchSize( size = 20 )
		private List<Product> products = new ArrayList<>();

		//*************** CONSTRUCTOR ******************//

		public Category() {
		}

		public Category( String name, String description ) {
			this.name = name;
			this.description = description;
		}

		public Category( long id, String name, String description ) {
			setId( id );
			this.name = name;
			this.description = description;
		}

		//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

		@Override
		public boolean equals( Object o ) {
			if ( this == o ) return true;
			if ( !( o instanceof Category that ) ) return false;
			if ( !( Objects.equals( getId(), that.getId() ) &&
					Objects.equals( name, that.name ) &&
					Objects.equals( description, that.description ) ) )
				return false;
			if ( products != null ) {
				if ( that.products == null ) return false;
				int s = products.size();
				if ( s != that.products.size() ) return false;
				for ( int i = 0; i < s; i++ ) {
					Long id1, id2;
					id1 = products.get( i ).getId();
					id2 = that.products.get( i ).getId();
					if ( !Objects.equals( id1, id2 ) ) return false;
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
			Long[] ids = new Long[s];
			for ( int i = 0; i < s; i++ )
				ids[i] = products.get( i ).getId();
			return hash( hashed, Arrays.hashCode( ids ) );
		}
	}
}

package se.pj.tbike.core.api.attribute.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import se.pj.tbike.api.util.Handleable;
import se.pj.tbike.core.common.entity.IdentifiedEntity;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.util.Cacheable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
		name = "attributes"
)
public class Attribute
		implements IdentifiedEntity<Attribute, Long>,
		Cacheable<Attribute>, Handleable {

	//*************** BASIC ******************//

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
			nullable = false
	)
	private boolean represent;

	@Column(
			length = 50,
			nullable = false
	)
	private String color;

	@Column(
			name = "image_url",
			nullable = false,
			columnDefinition = "TEXT"
	)
	private String imageUrl;

	@Column(
			columnDefinition = "BIGINT UNSIGNED DEFAULT 0"
	)
	private long price;

	@Column(
			columnDefinition = "DEFAULT 0"
	)
	private int quantity;

	//*************** RELATIONSHIPS ******************//

	@ManyToOne(
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "product_id",
			nullable = false,
			updatable = false,
			foreignKey = @ForeignKey(
					name = "FK__productAttributes_productId__products_id"
			)
	)
	private Product product;

	//*************** CONSTRUCTOR ******************//

	public Attribute() {
	}

	//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

//	@Override
//	public Long getId() {
//		return id;
//	}
//
//	@Override
//	public void setId(Long id) {
//		this.id = id;
//	}

	@Override
	public Map<String, Object> toCacheObject() {
		Map<String, Object> map = new HashMap<>();
		map.put( "id", getId() );
		map.put( "represent", represent );
		map.put( "color", color );
		map.put( "imageUrl", imageUrl );
		map.put( "price", price );
		map.put( "quantity", quantity );
		map.put( "product", product );
		return map;
	}

	@Override
	public Attribute fromCacheObject(Map<String, Object> cacheObject) {
		this.setId( (Long) cacheObject.get( "id" ) );
		this.setRepresent( (Boolean) cacheObject.get( "represent" ) );
		this.setColor( (String) cacheObject.get( "color" ) );
		this.setImageUrl( (String) cacheObject.get( "imageUrl" ) );
		this.setPrice( (Long) cacheObject.get( "price" ) );
		this.setQuantity( (Integer) cacheObject.get( "quantity" ) );
		this.setProduct( (Product) cacheObject.get( "product" ) );
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if ( !super.equals( o ) ) {
			return false;
		}
		if ( !(o instanceof Attribute that) ) {
			return false;
		}
		return Objects.equals( product.getId(), that.product.getId() ) &&
				represent == that.represent &&
				Objects.equals( color, that.color ) &&
				Objects.equals( imageUrl, that.imageUrl ) &&
				price == that.price &&
				quantity == that.quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash( super.hashCode(), product.getId(),
				represent, color, imageUrl,
				price, quantity );
	}
}

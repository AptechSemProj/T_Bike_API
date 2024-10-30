package se.pj.tbike.core.api.attribute.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import se.pj.tbike.core.common.IdentifiedEntity;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.caching.Cacheable;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
		name = "attributes"
)
public class Attribute
		extends IdentifiedEntity<Attribute>
		implements Cacheable {

	//*************** BASIC ******************//

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

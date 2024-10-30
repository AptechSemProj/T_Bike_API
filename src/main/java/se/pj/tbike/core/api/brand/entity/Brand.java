package se.pj.tbike.core.api.brand.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.caching.Cacheable;
import se.pj.tbike.core.common.SoftDeletionEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
		name = "brands",
		indexes = {
				@Index(
						name = "entity_deleted_idx",
						columnList = "deleted"
				)
		}
)
public class Brand
		extends SoftDeletionEntity<Brand>
		implements Cacheable {

	//*************** BASIC ******************//

	@Column(
			length = 400,
			nullable = false
	)
	private String name;

	@Column(
			name = "image_url",
			columnDefinition = "TEXT",
			nullable = false
	)
	private String imageUrl;

	@Column(
			columnDefinition = "TEXT"
	)
	private String description;

	//*************** RELATIONSHIPS ******************//

	@OneToMany(
			mappedBy = "brand",
			fetch = FetchType.EAGER
	)
	@BatchSize(
			size = 20
	)
	private List<Product> products = new ArrayList<>();

	//*************** CONSTRUCTOR ******************//

	public Brand() {
	}

	//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !super.equals( o ) ) {
			return false;
		}
		if ( !(o instanceof Brand that) ) {
			return false;
		}
		if ( !Objects.equals( name, that.name ) ) {
			return false;
		}
		if ( !Objects.equals( imageUrl, that.imageUrl ) ) {
			return false;
		}
		if ( !Objects.equals( description, that.description ) ) {
			return false;
		}
		if ( products != null ) {
			if ( that.products == null ) {
				return false;
			}
			int s = products.size();
			if ( s != that.products.size() ) {
				return false;
			}
			for ( int i = 0; i < s; i++ ) {
				Product p1 = products.get( i );
				Product p2 = that.products.get( i );
				if ( p1 == p2 ) {
					continue;
				}
				if ( p1 != null && p2 != null && Objects
						.equals( p1.getId(), p2.getId() ) ) {
					continue;
				}
				return false;
			}
			return true;
		}
		return that.products == null;
	}

	@Override
	public int hashCode() {
		int hashed = Objects
				.hash( super.hashCode(), name, imageUrl, description );
		if ( products == null ) {
			return hashed;
		}
		int s = products.size();
		Long[] ids = new Long[s];
		for ( int i = 0; i < s; i++ ) {
			Product p = products.get( i );
			if ( p == null ) {
				ids[i] = 0L;
			} else {
				ids[i] = p.getId();
			}
		}
		return Objects.hash( hashed, Arrays.hashCode( ids ) );
	}
}

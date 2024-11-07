package se.pj.tbike.core.api.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.util.Cacheable;
import se.pj.tbike.core.common.entity.SoftDeletionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
		name = "categories",
		indexes = {
				@Index(
						name = "entity_deleted_idx",
						columnList = "deleted"
				)
		}
)
public class Category
		implements
		SoftDeletionEntity<Category, Long>,
		Cacheable<Category> {

	//*************** BASIC ******************//

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
			nullable = false
	)
	@Setter(AccessLevel.PRIVATE)
	private boolean deleted;

	@Column(
			length = 300,
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

	@Transient
	@OneToMany(
			mappedBy = "category",
			fetch = FetchType.LAZY
	)
	private List<Product> products = new ArrayList<>();

	//*************** CONSTRUCTOR ******************//

	public Category() {
	}

	//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

	@Override
	public Map<String, Object> toCacheObject() {
		@SuppressWarnings("DuplicatedCode")
		Map<String, Object> map = new HashMap<>();
		map.put( "id", getId() );
		map.put( "name", getName() );
		map.put( "imageUrl", getImageUrl() );
		map.put( "description", getDescription() );
		return map;
	}

	@SuppressWarnings({ "DuplicatedCode" })
	@Override
	public Category fromCacheObject(Map<String, Object> cacheObject) {
		setId( (Long) cacheObject.get( "id" ) );
		setName( (String) cacheObject.get( "name" ) );
		setImageUrl( (String) cacheObject.get( "imageUrl" ) );
		setDescription( (String) cacheObject.get( "description" ) );
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Category that) ) {
			return false;
		}
		return Objects.equals( id, that.id ) &&
				Objects.equals( deleted, that.deleted ) &&
				Objects.equals( name, that.name ) &&
				Objects.equals( imageUrl, that.imageUrl ) &&
				Objects.equals( description, that.description );
//		if ( products != null ) {
//			if ( that.products == null ) {
//				return false;
//			}
//			int s = products.size();
//			if ( s != that.products.size() ) {
//				return false;
//			}
//			for ( int i = 0; i < s; i++ ) {
//				Product p1 = products.get( i );
//				Product p2 = that.products.get( i );
//				if ( p1 == p2 ) {
//					continue;
//				}
//				if ( p1 != null && p2 != null && Objects
//						.equals( p1.getId(), p2.getId() ) ) {
//					continue;
//				}
//				return false;
//			}
//			return true;
//		}
//		return that.products == null;
	}

	@Override
	public int hashCode() {
//		int hashed =
		return Objects.hash( id, name, imageUrl, description );
//		if ( products == null ) {
//		return hashed;
//		}
//		int s = products.size();
//		Long[] ids = new Long[s];
//		for ( int i = 0; i < s; i++ ) {
//			Product p = products.get( i );
//			if ( p == null ) {
//				ids[i] = 0L;
//			} else {
//				ids[i] = p.getId();
//			}
//		}
//		return Objects.hash( hashed, Arrays.hashCode( ids ) );
	}
}

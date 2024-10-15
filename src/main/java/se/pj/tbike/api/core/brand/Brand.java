package se.pj.tbike.api.core.brand;

import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.EAGER;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.BatchSize;
import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.product.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "brands" )
public class Brand
		extends IdentifiedEntity<Brand> {

	//*************** BASIC ******************//

	@Column( length = 400, nullable = false )
	private String name;

	@Column( name = "image_url", columnDefinition = "TEXT", nullable = false )
	private String imageUrl;

	@Column( columnDefinition = "TEXT" )
	private String introduction;

	//*************** RELATIONSHIPS ******************//

	@OneToMany( mappedBy = "brand", fetch = EAGER )
	@BatchSize( size = 100 )
	private List<Product> products = new ArrayList<>();

	//*************** CONSTRUCTOR ******************//

	public Brand( String name, String introduction, String imageUrl ) {
		this.name = name;
		this.introduction = introduction;
		this.imageUrl = imageUrl;
	}

	public Brand( Long id, String name,
	              String introduction, List<Product> products ) {
		setId( id );
		this.name = name;
		this.introduction = introduction;
		this.products = products;
	}

	//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) return true;
		if ( !( o instanceof Brand that ) ) return false;
		if ( !( Objects.equals( getId(), that.getId() ) &&
				Objects.equals( name, that.name ) &&
				Objects.equals( introduction, that.introduction ) ) )
			return false;
		if ( products != null ) {
			if ( that.products == null ) return false;
//			if ( products.isEmpty() ) {
//				return that.products.isEmpty();
//			} else {
			int s1 = products.size();
			if ( s1 != that.products.size() ) return false;
			long id1, id2;
			for ( int i = 0; i < s1; i++ ) {
				id1 = products.get( i ).getId();
				id2 = that.products.get( i ).getId();
				if ( id1 != id2 ) return false;
			}
			return true;
//			}
		}
		return that.products == null;
	}

	@Override
	public int hashCode() {
		int hashed = hash( getId(), name, introduction );
		if ( products == null ) return hashed;
		int s = products.size();
		long[] ids = new long[s];
		for ( int i = 0; i < s; i++ )
			ids[i] = products.get( i ).getId();
		return hash( hashed, Arrays.hashCode( ids ) );
	}
}

package se.pj.tbike.api.core.brand;

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

import org.hibernate.annotations.BatchSize;

import lombok.Getter;
import lombok.Setter;

import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.product.Product;

@Getter
@Setter
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
	private String description;

	//*************** RELATIONSHIPS ******************//

	@OneToMany( mappedBy = "brand", fetch = EAGER )
	@BatchSize( size = 20 )
	private List<Product> products = new ArrayList<>();

	//*************** CONSTRUCTOR ******************//

	public Brand() {
	}

	public Brand( String name, String imageUrl, String description ) {
		this.name = name;
		this.imageUrl = imageUrl;
		this.description = description;
	}

	//*************** IMPLEMENTS & OVERRIDE METHODS ******************//

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) return true;
		if ( !( o instanceof Brand that ) ) return false;
		if ( !( Objects.equals( getId(), that.getId() ) &&
				Objects.equals( name, that.name ) &&
				Objects.equals( description, that.description ) ) )
			return false;
		if ( products != null ) {
			if ( that.products == null ) return false;
			int s1 = products.size();
			if ( s1 != that.products.size() ) return false;
			long id1, id2;
			for ( int i = 0; i < s1; i++ ) {
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

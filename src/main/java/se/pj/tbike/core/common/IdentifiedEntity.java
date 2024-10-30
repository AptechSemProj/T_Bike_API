package se.pj.tbike.core.common;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class IdentifiedEntity<T extends IdentifiedEntity<T>>
		implements Comparable<T> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Override
	public int compareTo(T o) {
		Long id1 = getId() == null ? 0 : getId();
		Long id2 = o.getId() == null ? 0 : o.getId();
		return id1.compareTo( id2 );
	}

	// java  : Long            : max value = 09_223_372_036_854_775_807
	// mysql : BIGINT UNSIGNED : max value = 18_446_744_073_709_551_615
	public void setId(Long id) {
		if ( id != null && id < 1 ) {
			throw new IllegalArgumentException( "id cannot be less than 0" );
		}
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof IdentifiedEntity<?> that) ) {
			return false;
		}
		if ( id != null ) {
			return that.id != null && id.equals( that.id );
		}
		return that.id == null;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
}

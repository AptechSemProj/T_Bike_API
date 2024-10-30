package se.pj.tbike.core.common;

import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public class SoftDeletionEntity<T extends SoftDeletionEntity<T>>
		extends IdentifiedEntity<T> {

	@Column(
			nullable = false
	)
	private boolean deleted;

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !super.equals( o ) ) {
			return false;
		}
		if ( !(o instanceof SoftDeletionEntity<?> that) ) {
			return false;
		}
		return deleted == that.deleted;
	}

	@Override
	public int hashCode() {
		return Objects.hash( super.hashCode(), deleted );
	}
}

package se.pj.tbike.api.common.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract
class IdentifiedEntity<T extends IdentifiedEntity<T>>
		implements Comparable<T> {

	@Id
	@GeneratedValue( strategy = IDENTITY )
	private Long id;

	@Override
	public int compareTo( T o ) {
		return (int) ( getId() - o.getId() );
	}
}

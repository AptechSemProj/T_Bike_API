package com.sem.proj.tbike.api.common.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class IdentifiedEntity< T extends IdentifiedEntity<T> >
		implements Comparable<T> {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	@Override
	public int compareTo(T o) {
		return (int) ( getId() - o.getId() );
	}
}

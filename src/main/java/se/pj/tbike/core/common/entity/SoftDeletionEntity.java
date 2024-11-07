package se.pj.tbike.core.common.entity;

public interface SoftDeletionEntity<
		E extends SoftDeletionEntity<E, ID>,
		ID extends Comparable<ID>
		> extends IdentifiedEntity<E, ID> {

	boolean isDeleted();

}

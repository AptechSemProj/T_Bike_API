package se.pj.tbike.common.entity;

public interface SoftDeletionEntity<
		E extends SoftDeletionEntity<E, ID>,
		ID extends Comparable<ID>
		> extends IdentifiedEntity<E, ID> {

	boolean isDeleted();

}

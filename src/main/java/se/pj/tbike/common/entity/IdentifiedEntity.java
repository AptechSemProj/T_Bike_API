package se.pj.tbike.common.entity;

public interface IdentifiedEntity<
		T extends IdentifiedEntity<T, K>,
		K extends Comparable<K>
		> extends Comparable<T> {

	K getId();

	void setId(K id);

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	@Override
	default int compareTo(T o) {
		K id1 = getId();
		K id2 = o.getId();
		if ( id1 == null && id2 == null ) {
			return 0;
		} else if ( id1 == null ) {
			return -1;
		} else if ( id2 == null ) {
			return 1;
		} else {
			return id1.compareTo( id2 );
		}
	}

}

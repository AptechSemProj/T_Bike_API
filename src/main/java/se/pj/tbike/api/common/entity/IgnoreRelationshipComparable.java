package se.pj.tbike.api.common.entity;

import lombok.NonNull;

public interface IgnoreRelationshipComparable<T>
		extends Comparable<T> {

	int compareTo(@NonNull T o, boolean isIgnoreRelationship);

	@Override
	default int compareTo(@NonNull T o) {
		return compareTo(o, false);
	}
}

package se.pj.tbike.service;

public interface CrudService<T, K extends Comparable<K>>
		extends CreationService<T>,
		ModificationService<T>,
		QueryService<T, K>,
		RemovalService<T, K> {

}

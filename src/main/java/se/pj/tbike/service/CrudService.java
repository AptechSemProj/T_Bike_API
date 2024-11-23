package se.pj.tbike.service;

import com.ank.japi.annotation.Old;

@Old
public interface CrudService<T, K>
		extends CreationService<T>,
		ModificationService<T>,
		QueryService<T, K>,
		RemovalService<T, K> {

}

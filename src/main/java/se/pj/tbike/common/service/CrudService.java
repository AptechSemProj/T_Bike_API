package se.pj.tbike.common.service;

public interface CrudService<T, ID>
        extends CreationService<T>,
        ModificationService<T>,
        QueryService<T, ID>,
        DeletionService<T, ID> {
}

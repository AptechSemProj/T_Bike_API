package se.pj.tbike.service;

public interface CrudService<T, ID>
        extends CreationService<T>,
        ModificationService<T>,
        QueryService<T, ID>,
        DeletionService<T, ID> {
}

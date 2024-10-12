package com.sem.proj.tbike.core.mapper;

/**
 * @param <T_M>   type of model class.
 * @param <T_DTO> type of data transfer object class
 */
public interface DTOMapper<T_M, T_DTO> {

	T_DTO toDTO(T_M m);

	T_M toModel(T_DTO dto);

}

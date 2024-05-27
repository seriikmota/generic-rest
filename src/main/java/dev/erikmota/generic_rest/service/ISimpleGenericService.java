package dev.erikmota.generic_rest.service;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;

public interface ISimpleGenericService<DTO, TYPE_PK> {
    List<DTO> listAll();
    DTO create(DTO dtoCreate);
    DTO update(TYPE_PK id, DTO dtoUpdate) throws JsonMappingException;
    DTO getById(TYPE_PK id);
    DTO deleteById(TYPE_PK id);
}

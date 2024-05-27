package dev.erikmota.generic_rest.service;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;

public interface IGenericService<DTO, DTOCreate, DTOUpdate, DTOList, TYPE_PK> {
    List<DTOList> listAll();
    DTO create(DTOCreate dtoCreate);
    DTO update(TYPE_PK id, DTOUpdate dtoUpdate) throws JsonMappingException;
    DTO getById(TYPE_PK id);
    DTO deleteById(TYPE_PK id);
}

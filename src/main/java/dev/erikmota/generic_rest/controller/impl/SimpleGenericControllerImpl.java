package dev.erikmota.generic_rest.controller.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import dev.erikmota.generic_rest.controller.ISimpleGenericController;
import dev.erikmota.generic_rest.service.ISimpleGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class SimpleGenericControllerImpl<DTO, SERVICE extends ISimpleGenericService<DTO, TYPE_PK>, TYPE_PK> implements ISimpleGenericController<DTO, TYPE_PK> {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected SERVICE service;

    @PostMapping
    @Transactional
    public ResponseEntity<DTO> create(@RequestBody DTO dtoCreate){
        DTO resultDTO = service.create(dtoCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDTO);
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<DTO> update(@PathVariable TYPE_PK id, @RequestBody DTO dto) throws JsonMappingException {
        DTO modelSaved = service.update(id, dto);
        return ResponseEntity.ok(modelSaved);
    }

    @DeleteMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<DTO> delete(@PathVariable TYPE_PK id){
        DTO deleteDTO = service.deleteById(id);
        return ResponseEntity.ok(deleteDTO);
    }

    @GetMapping
    public ResponseEntity<List<DTO>> listAll(){
        List<DTO> listDTO = service.listAll();
        return ResponseEntity.ok(listDTO);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DTO> getById(@PathVariable TYPE_PK id){
        DTO dtoResult = service.getById(id);
        return ResponseEntity.ok(dtoResult);
    }
}

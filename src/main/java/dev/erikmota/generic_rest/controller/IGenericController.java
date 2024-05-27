package dev.erikmota.generic_rest.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import dev.erikmota.generic_rest.teste.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IGenericController<DTO, DTOCreate, DTOUpdate, DTOList, TYPE_PK> {
    @Operation(description = "Endpoint to register a object", responses = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    })
    ResponseEntity<DTO> create(@RequestBody DTOCreate dto);

    @Operation(description = "Endpoint to edit a object", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
    })
    ResponseEntity<DTO> update(@PathVariable TYPE_PK id, @RequestBody DTOUpdate dto) throws JsonMappingException;

    @Operation(description = "Endpoint to remove a object", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<DTO> delete(@PathVariable TYPE_PK id);

    @Operation(description = "Endpoint to list all objects", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<List<DTOList>> listAll();

    @Operation(description = "Endpoint to search for an object by primary key", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<DTO> getById(@PathVariable TYPE_PK id);
}

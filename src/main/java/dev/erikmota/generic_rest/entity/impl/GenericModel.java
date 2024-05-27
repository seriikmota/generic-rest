package dev.erikmota.generic_rest.entity.impl;

import dev.erikmota.generic_rest.entity.IGenericModel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericModel<TYPE_PK> implements IGenericModel<TYPE_PK> {
    public TYPE_PK id;
//    public String sortParam;
//    private String sortDirection;
//    private Long pageNumber;
//    private Long pageSize;
//    private String[] displayedColumns;
//    private String[] otherColumnsSerialize;
}

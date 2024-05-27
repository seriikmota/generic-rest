package dev.erikmota.generic_rest.entity;

public interface IGenericModel<TYPE_PK> {
    TYPE_PK getId();
    void setId(TYPE_PK id);
    //String getSortParam();
    //void setSortParam(String sortParam);
    //String getSortDirection();
    //void setSortDirection(String sortDirection);
    //Long getPageNumber();
    //void setPageNumber(Long pageNumber);
    //Long getPageSize();
    //void setPageSize(Long pageSize);
    //String[] getDisplayedColumns();
    //void setDisplayedColumns(String[] displayedColumns);
    //String[] getOtherColumnsSerialize();
    //void setOtherColumnsSerialize(String[] otherColumnsSerialize);
}

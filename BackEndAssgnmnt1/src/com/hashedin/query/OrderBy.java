package com.hashedin.query;

enum SortOrder {
    ASC,
    DESC
}

public interface OrderBy {
    public String getColName();
    public SortOrder getSortOrder();
}

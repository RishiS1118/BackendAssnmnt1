package com.hashedin.query;

public class QueryResult {
    private String colName;
    private Object value;

    public QueryResult(String colName, Object data) {
        this.colName = colName;
        this.value = data;
    }

    public String getColName() {
        return colName;
    }

    public Object getValue() {
        return value;
    }
}

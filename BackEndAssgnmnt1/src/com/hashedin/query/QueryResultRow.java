package com.hashedin.query;

import java.util.List;

public class QueryResultRow {
    List<QueryResult> queryResultList;

    public QueryResultRow(List<QueryResult> queryResultList) {
        this.queryResultList = queryResultList;
    }

    public List<QueryResult> getRow() {
        return queryResultList;
    }
}

package com.hashedin.query;

import java.util.List;

public interface Datasource {
    public void connect();
    public void disconnect();
    public List<QueryResultRow> runQuery(QueryStatement queryStatement) throws Exception;
}

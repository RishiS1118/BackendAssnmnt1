package com.hashedin.implementations;

import com.hashedin.query.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class TSVDatasource implements Datasource {
    private List<List<String>> data;
    private String fileName;
    private String delimiter;
    private boolean connected;
    private Map<String, Integer> headersToIndexMap;

    public TSVDatasource(String fileName) {
        this(fileName, "\t");
    }

    TSVDatasource(String fileName, String delimiter) {
        this.data = new ArrayList<>();
        this.fileName = fileName;
        this.delimiter = delimiter;
        this.connected = false;
        this.headersToIndexMap = new HashMap<>();
    }

    @Override
    public void connect() {
        BufferedReader br = null;
        try {
             br = new BufferedReader(new FileReader(this.fileName));
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(this.delimiter);
                if (header) {
                    for(int index = 0; index< values.length; index++) {
                        this.headersToIndexMap.put(values[index], index);
                    }
                    header = false;
                    continue;
                }
                this.data.add(Arrays.asList(values));
            }
            this.connected = true;
        } catch (Exception exc) {
            System.out.println("Error reading file  "+ this.fileName+":  "+exc);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception exc) {
                    //do Nothing
                }
            }
        }
    }

    @Override
    public void disconnect() {
        this.data = new ArrayList<>();
        this.connected = false;
    }

    @Override
    public List<QueryResultRow> runQuery(QueryStatement queryStatement) throws Exception {
        Map<List<String>, QueryResultRow> resultMap = new HashMap<>();


        int resultCount = 0;
        for(List<String> row: this.data) {
            if (!queryStatement.filterFn(row)) {
                continue;
            }
            List<QueryResult> resultRow = new ArrayList<>();
            List<String> key = getResultKey(queryStatement, row, resultCount++);

            SelectStatement selectStatement = queryStatement.getSelectStatement();
            AliasSelectStatement aliasSelectStatement = queryStatement.getAliasSelectStatement();
            GroupBy groupBy = queryStatement.getGroupBy();

            int rowColIndex = 0;
            if(selectStatement !=  null) {
                for (String col : selectStatement.getSelectCols()) {
                    if (this.headersToIndexMap.containsKey(col)) {
                        int colIndex = this.headersToIndexMap.get(col);
                        Object value = selectStatement.formatData(row, colIndex);
                        if (groupBy!= null && groupBy.getGroupByCols().contains(col)) {
                            resultRow.add((new QueryResult(col, value)));
                        } else {
                            if (resultMap.containsKey(key)) {
                                resultRow.add(new QueryResult(col, selectStatement.aggregateDataFn(
                                        resultMap.get(key).getRow().get(rowColIndex).getValue(), value)));
                            } else {
                                resultRow.add(new QueryResult(col, selectStatement.aggregateDataFn(null, value)));
                            }
                        }
                        rowColIndex++;
                    } else {
                        throw new Exception("Cannot get data for col: " + col);
                    }
                }
            }

            if (aliasSelectStatement != null) {
                for (AliasSelectStatement.AliasSelect aliasSelect : aliasSelectStatement.getAliases()) {
                    if (this.headersToIndexMap.containsKey(aliasSelect.getColName())) {
                        int colIndex = this.headersToIndexMap.get(aliasSelect.getColName());
                        Object value = aliasSelect.formatData(row, colIndex);
                        if (groupBy != null && groupBy.getGroupByCols().contains(aliasSelect.getAlias())) {
                            resultRow.add(new QueryResult(aliasSelect.getAlias(), aliasSelect.formatData(row, colIndex)));
                        } else {
                            if (resultMap.containsKey(key)) {
                                resultRow.add(new QueryResult(aliasSelect.getAlias(), aliasSelect.aggregateDataFn(
                                        resultMap.get(key).getRow().get(rowColIndex).getValue(), value)));
                            } else {
                                resultRow.add(new QueryResult(aliasSelect.getAlias(), aliasSelect.aggregateDataFn(null, value)));
                            }
                        }
                        rowColIndex++;
                    } else {
                        throw new Exception("Cannot get data for col: " + aliasSelect.getColName());
                    }
                }
            }
            resultMap.put(key, new QueryResultRow(resultRow));
        }

        OrderBy orderBy = queryStatement.getOrderBy();
        List<QueryResultRow> results = resultMap.values().stream().collect(Collectors.toList());
        if (orderBy != null && orderBy.getOrderByCols() != null && !orderBy.getOrderByCols().isEmpty()) {
            Collections.sort( results, (QueryResultRow row1, QueryResultRow row2) -> {
                return this.myCompare(queryStatement, 0, row1, row2);
            });
        }

        int limit = queryStatement.getLimit();
        if (limit != -1) {
            return results.stream().limit(limit).collect(Collectors.toList());
        }
        return results;
    }

    public Map<String, Integer> getHeaderToIndexMap() {
        return new HashMap<>(this.headersToIndexMap);
    }

    private List<String> getResultKey(QueryStatement queryStatement, List<String> data, int count) throws Exception {
        GroupBy groupBy = queryStatement.getGroupBy();
        List<String> key = new ArrayList<>();

        SelectStatement selectStatement = queryStatement.getSelectStatement();
        AliasSelectStatement aliasSelectStatement = queryStatement.getAliasSelectStatement();


        if (groupBy != null && groupBy.getGroupByCols() != null) {
            List<String> groupByCols = groupBy.getGroupByCols();
            if(selectStatement !=  null) {
                for (String col : selectStatement.getSelectCols()) {
                    if (groupByCols.contains(col)) {
                        if (this.headersToIndexMap.containsKey(col)) {
                            Object value = selectStatement.formatData(data, this.headersToIndexMap.get(col));
                            key.add(value.toString());
                        }
                    }
                }
            }

            if (aliasSelectStatement != null) {
                for (AliasSelectStatement.AliasSelect aliasSelect : aliasSelectStatement.getAliases()) {
                    if (groupByCols.contains(aliasSelect.getAlias())) {
                        if (this.headersToIndexMap.containsKey(aliasSelect.getColName())) {
                            key.add(aliasSelect.formatData(data, this.headersToIndexMap.get(aliasSelect.getColName())).toString());
                        }
                    }
                }
            }
        } else {
            key.add((Integer.toString(count)));
        }
        return key;
    }

    private int myCompare(QueryStatement queryStatement, int index, QueryResultRow row1, QueryResultRow row2) {
        List<OrderBy.Order> orderByCols= queryStatement.getOrderBy().getOrderByCols();

        if(index < orderByCols.size()) {
            OrderBy.Order orderCol = orderByCols.get(index);

            int colIndex = 0;
            for(QueryResult queryResult: row1.getRow()) {
                if (queryResult.getColName().equalsIgnoreCase(orderCol.getColName())) {
                    int compare = orderCol.compare(queryResult.getValue(), row2.getRow().get(colIndex).getValue());
                    if (compare == 0) {
                        if (index + 1 < orderByCols.size()) {
                            return myCompare(queryStatement, index+1, row1, row2);
                        }
                    }
                    if (orderCol.getOrder() == OrderBy.SortOrder.ASC) {
                        return compare;
                    }
                    return -compare;
                }
                colIndex++;
            }
        }
        return 0;
    }
}

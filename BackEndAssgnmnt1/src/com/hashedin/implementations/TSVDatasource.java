package com.hashedin.implementations;

import com.hashedin.query.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

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
                this.data.add(Arrays.asList(values));
                if (header) {
                    for(int index = 0; index< values.length; index++) {
                        this.headersToIndexMap.put(values[index], index);
                    }
                    header = false;
                }
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
        List<QueryResultRow> results = new ArrayList<>();

        for(List<String> row: this.data) {
            List<QueryResult> resultRow = new ArrayList<>();
            SelectStatement selectStatement = queryStatement.getSelectStatement();

            if(selectStatement !=  null) {
                for (String col : selectStatement.getSelectCols()) {
                    if (this.headersToIndexMap.containsKey(col)) {
                        resultRow.add(
                                new QueryResult(col, selectStatement.formatData(row, this.headersToIndexMap.get(col))));
                    } else {
                        throw new Exception("Cannot get data for col: " + col);
                    }
                }
            }

            AliasSelectStatement aliasSelectStatement = queryStatement.getAliasSelectStatement();
            if (aliasSelectStatement != null) {
                for (AliasSelectStatement.AliasSelect aliasSelect : aliasSelectStatement.getAliases()) {
                    if (this.headersToIndexMap.containsKey(aliasSelect.getColName())) {
                        resultRow.add(
                                new QueryResult(aliasSelect.getAlias(), aliasSelect.formatData(row, this.headersToIndexMap.get(aliasSelect.getColName()))));
                    } else {
                        throw new Exception("Cannot get data for col: " + aliasSelect.getColName());
                    }
                }
            }

            results.add(new QueryResultRow(resultRow));
        }
        return results;
    }

    public Map<String, Integer> getHeaderToIndexMap() {
        return new HashMap<>(this.headersToIndexMap);
    }
}

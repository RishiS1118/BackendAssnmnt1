package com.hashedin;

import com.hashedin.implementations.TSVDatasource;
import com.hashedin.query.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        CsvReader csvReader = new ReadCsvImplementation();
//        System.out.println(csvReader.readCsv("events.tsv"));

        System.out.println("Initializing dataSource");
        TSVDatasource tsvDatasource = new TSVDatasource("events.tsv");
        tsvDatasource.connect();

        System.out.println("DataSource connected");
        SelectStatement selectStatement = new SelectStatement() {
            @Override
            public List<String> getSelectCols() {
                List<String> colNames = new ArrayList<>();
                colNames.add("uuid");
//                colNames.add("event_type");
//                colNames.add("event_category");
                return colNames;
            }
        };

        QueryStatement queryStatement = new QueryStatement(selectStatement) {
        };

        System.out.println("Running query");
        try {
            List<QueryResultRow> queryResultRows = tsvDatasource.runQuery(queryStatement);

            for(QueryResultRow resultRow: queryResultRows) {
                for(QueryResult result: resultRow.getRow()) {
                    System.out.print(result.getColName()+": "+result.getValue() + ", ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Query Finished");
    }
}

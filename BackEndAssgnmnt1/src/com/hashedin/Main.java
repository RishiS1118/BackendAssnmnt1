package com.hashedin;

import com.hashedin.implementations.TSVDatasource;
import com.hashedin.query.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static TSVDatasource tsvDatasource;
    public static List<QueryResultRow> getTopViewedPages(int n) {
        AliasSelectStatement aliasSelectStatement = new AliasSelectStatement() {
            @Override
            public List<AliasSelect> getAliases() {
                AliasSelect aliasSelect = new AliasSelect("location", "count") {
                    @Override
                    public Object aggregateDataFn(Object data1, Object data2) {
                        if (data1 == null) {
                            return 1;
                        }
                        return Integer.valueOf(data1.toString()) + 1;
                    }
                };

                AliasSelect pageAlias = new AliasSelect("location", "page") {};
                List<AliasSelect> aliases = new ArrayList<>();
                aliases.add(aliasSelect);
                aliases.add(pageAlias);
                return aliases;
            }

        };

        OrderBy orderBy = new OrderBy() {
            @Override
            public List<Order> getOrderByCols() {
                List<Order> orders = new ArrayList<>();
                Order order = new Order("count", SortOrder.DESC) {
                    @Override
                    public int compare(Object val1, Object val2) {
                        return Integer.valueOf(val1.toString()).compareTo(Integer.valueOf(val2.toString()));
                    }
                };
                orders.add(order);
                return orders;
            }
        };

        GroupBy groupBy = new GroupBy() {
            @Override
            public List<String> getGroupByCols() {
                List<String> groupByCols = new ArrayList<>();
                groupByCols.add("page");
                return groupByCols;
            }
        };
        QueryStatement queryStatement = new QueryStatement(aliasSelectStatement, orderBy, groupBy, n) {
        };

        System.out.println("Running query");
        List<QueryResultRow> queryResultRows = null;
        try {
            queryResultRows = tsvDatasource.runQuery(queryStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Query Finished");
        return queryResultRows;
    }

    public static void main(String[] args) {
//        CsvReader csvReader = new ReadCsvImplementation();
//        System.out.println(csvReader.readCsv("events.tsv"));

        System.out.println("Initializing dataSource");
        tsvDatasource = new TSVDatasource("events.tsv");
        tsvDatasource.connect();

        System.out.println("DataSource connected");
        List<QueryResultRow> queryResultRows = getTopViewedPages(10);
        if (queryResultRows != null) {
            for (QueryResultRow resultRow : queryResultRows) {
                for (QueryResult result : resultRow.getRow()) {
                    System.out.print(result.getColName() + ": " + result.getValue() + ", ");
                }
                System.out.println();
            }
        }
    }
}

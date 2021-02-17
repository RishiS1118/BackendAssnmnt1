package com.hashedin.query;

public abstract class QueryStatement {
    private SelectStatement selectStatement;
    private AliasSelectStatement aliasSelectStatement;
    private OrderBy orderBy;
    private GroupBy groupBy;


    protected QueryStatement(SelectStatement selectStatement) {
        this(selectStatement, null, null, null) ;
    }

    QueryStatement(AliasSelectStatement aliasSelectStatement) {
        this(null, aliasSelectStatement, null, null) ;
    }

    QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement) {
        this(selectStatement, aliasSelectStatement, null, null) ;
    }

    QueryStatement(SelectStatement selectStatement, GroupBy groupBy) {
        this(selectStatement, null, null, groupBy) ;
    }

    QueryStatement(AliasSelectStatement aliasSelectStatement, GroupBy groupBy) {
        this(null, aliasSelectStatement, null, groupBy) ;
    }

    QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement, GroupBy groupBy) {
        this(selectStatement, aliasSelectStatement, null, groupBy) ;
    }

    QueryStatement(SelectStatement selectStatement, OrderBy orderBy) {
        this(selectStatement, null, orderBy, null) ;
    }

    QueryStatement(AliasSelectStatement aliasSelectStatement, OrderBy orderBy) {
        this(null, aliasSelectStatement, orderBy, null) ;
    }

    QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement, OrderBy orderBy) {
        this(selectStatement, aliasSelectStatement, orderBy, null) ;
    }

    QueryStatement(SelectStatement selectStatement, OrderBy orderBy, GroupBy groupBy) {
        this(selectStatement, null, null, null) ;
    }

    QueryStatement(AliasSelectStatement aliasSelectStatement, OrderBy orderBy, GroupBy groupBy) {
        this(null, aliasSelectStatement, orderBy, groupBy) ;
    }

    QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement,
                   OrderBy orderBy, GroupBy groupBy) {
        this.selectStatement = selectStatement;
        this.aliasSelectStatement = aliasSelectStatement;
        this.orderBy = orderBy;
        this.groupBy = groupBy;
    }

    public SelectStatement getSelectStatement() {
        return selectStatement;
    }

    public AliasSelectStatement getAliasSelectStatement() {
        return aliasSelectStatement;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public GroupBy getGroupBy() {
        return groupBy;
    }
}

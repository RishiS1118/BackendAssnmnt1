package com.hashedin.query;

import java.util.List;

public abstract class QueryStatement {
    private SelectStatement selectStatement;
    private AliasSelectStatement aliasSelectStatement;
    private OrderBy orderBy;
    private GroupBy groupBy;
    private int limit;


    protected QueryStatement(SelectStatement selectStatement) {
        this(selectStatement, null, null, null) ;
    }

    protected QueryStatement(AliasSelectStatement aliasSelectStatement) {
        this(null, aliasSelectStatement, null, null) ;
    }

    protected QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement) {
        this(selectStatement, aliasSelectStatement, null, null) ;
    }

    protected QueryStatement(SelectStatement selectStatement, GroupBy groupBy) {
        this(selectStatement, null, null, groupBy) ;
    }

    protected QueryStatement(AliasSelectStatement aliasSelectStatement, GroupBy groupBy) {
        this(null, aliasSelectStatement, null, groupBy) ;
    }

    protected QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement, GroupBy groupBy) {
        this(selectStatement, aliasSelectStatement, null, groupBy) ;
    }

    protected QueryStatement(SelectStatement selectStatement, OrderBy orderBy) {
        this(selectStatement, null, orderBy, null) ;
    }

    protected QueryStatement(AliasSelectStatement aliasSelectStatement, OrderBy orderBy) {
        this(null, aliasSelectStatement, orderBy, null) ;
    }

    protected QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement, OrderBy orderBy) {
        this(selectStatement, aliasSelectStatement, orderBy, null) ;
    }

    protected QueryStatement(SelectStatement selectStatement, OrderBy orderBy, GroupBy groupBy) {
        this(selectStatement, null, null, null) ;
    }

    protected QueryStatement(AliasSelectStatement aliasSelectStatement, OrderBy orderBy, GroupBy groupBy) {
        this(null, aliasSelectStatement, orderBy, groupBy) ;
    }

    protected QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement,
                             OrderBy orderBy, GroupBy groupBy) {
        this(selectStatement, aliasSelectStatement, orderBy, groupBy, -1);
    }

    protected QueryStatement(SelectStatement selectStatement, int limit) {
        this(selectStatement, null, null, null, limit) ;
    }

    protected QueryStatement(AliasSelectStatement aliasSelectStatement, int limit) {
        this(null, aliasSelectStatement, null, null, limit) ;
    }

    protected QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement, int limit) {
        this(selectStatement, aliasSelectStatement, null, null, limit) ;
    }

    protected QueryStatement(SelectStatement selectStatement, GroupBy groupBy, int limit) {
        this(selectStatement, null, null, groupBy, limit) ;
    }

    protected QueryStatement(AliasSelectStatement aliasSelectStatement, GroupBy groupBy, int limit) {
        this(null, aliasSelectStatement, null, groupBy, limit) ;
    }

    protected QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement, GroupBy groupBy, int limit) {
        this(selectStatement, aliasSelectStatement, null, groupBy, limit) ;
    }

    protected QueryStatement(SelectStatement selectStatement, OrderBy orderBy, int limit) {
        this(selectStatement, null, orderBy, null, limit) ;
    }

    protected QueryStatement(AliasSelectStatement aliasSelectStatement, OrderBy orderBy, int limit) {
        this(null, aliasSelectStatement, orderBy, null, limit) ;
    }

    protected QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement, OrderBy orderBy, int limit) {
        this(selectStatement, aliasSelectStatement, orderBy, null, limit) ;
    }

    protected QueryStatement(SelectStatement selectStatement, OrderBy orderBy, GroupBy groupBy, int limit) {
        this(selectStatement, null, null, null, limit) ;
    }

    protected QueryStatement(AliasSelectStatement aliasSelectStatement, OrderBy orderBy, GroupBy groupBy, int limit) {
        this(null, aliasSelectStatement, orderBy, groupBy, limit) ;
    }

    protected QueryStatement(SelectStatement selectStatement, AliasSelectStatement aliasSelectStatement,
                             OrderBy orderBy, GroupBy groupBy, int limit) {
        this.selectStatement = selectStatement;
        this.aliasSelectStatement = aliasSelectStatement;
        this.orderBy = orderBy;
        this.groupBy = groupBy;
        this.limit = limit;
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

    public boolean filterFn(List<String> data) {
        return true;
    }

    public int getLimit() {
        return limit;
    }
}

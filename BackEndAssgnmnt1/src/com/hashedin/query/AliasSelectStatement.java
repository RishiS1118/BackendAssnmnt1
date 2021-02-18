package com.hashedin.query;

import java.util.List;

public abstract class AliasSelectStatement {
    private List<AliasSelect> aliases;

    protected  AliasSelectStatement() {
        this(null);
    }

    protected  AliasSelectStatement(List<AliasSelect> aliases) {
        this.aliases = aliases;
    }

    public List<AliasSelect> getAliases() {
        return this.aliases;
    }

    public abstract class AliasSelect {
        private String alias, colName;

        public AliasSelect(String col, String alias) {
            this.alias = alias;
            this.colName = col;
        }

        public String getAlias() {
            return alias;
        }

        public String getColName() {
            return colName;
        }

        public Object aggregateDataFn(Object data1, Object data2) {
            if (data1 == null) {
                return data2.toString();
            }
            return data1.toString() + data2.toString();
        }

        public Object formatData(Object data) {
            return data;
        }

        public Object formatData(List<String> data, int index) {
            return data.get(index);
        }
    }
}

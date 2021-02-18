package com.hashedin.query;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderBy {
    public enum SortOrder {
        ASC,
        DESC
    }

    private List<Order> orderByCols;

    public OrderBy() {
        this(null);
    }

    public OrderBy(List<Order> orderbyCols) {
        this.orderByCols = orderbyCols;
    }

    public List<Order> getOrderByCols() {
        return new ArrayList<>(orderByCols);
    }

    public abstract  class Order {
        private String colName;
        SortOrder order;

        public Order(String col) {
            this(col, SortOrder.ASC);
        }
        public Order(String col, SortOrder order) {
            this.colName = col;
            this.order = order;
        }

        public String getColName() {
            return colName;
        }

        public SortOrder getOrder() {
            return order;
        }

        public abstract  int compare(Object val1, Object val2);
    }
}

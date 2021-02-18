package com.hashedin.query;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectStatement {
    private List<String> cols;
    protected SelectStatement() {
    }

    SelectStatement(List<String> cols) {
        this.cols = cols;
    }

    public List<String> getSelectCols(){
        return new ArrayList<>(this.cols);
    }

    public Object formatData(Object data) {
        return data;
    }

    public Object formatData(List<String> data, int index) {
        return data.get(index);
    }

    public Object aggregateDataFn(Object data1, Object data2) {
        if (data1 == null) {
            return data2.toString();
        }
        return data1.toString() + data2.toString();
    }
}

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
}

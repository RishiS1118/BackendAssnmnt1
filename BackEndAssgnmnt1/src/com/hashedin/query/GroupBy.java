package com.hashedin.query;

import java.util.List;

public abstract class GroupBy {
    private List<String> groupByCols;

    public GroupBy() {
        this(null);
    }

    public GroupBy(List<String> groupByCols) {
        this.groupByCols = groupByCols;
    }

    public List<String> getGroupByCols() {
        return groupByCols;
    }
}

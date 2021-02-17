package com.hashedin.query;

public enum Delimeter {

    TAB_DELIMITER("\t");

    private String delimiter;

    Delimeter(String s) {
        this.delimiter = s;
    }

    public  String getDelimiter() {
        return this.delimiter;
    }
}

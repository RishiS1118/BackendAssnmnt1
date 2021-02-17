package com.hashedin.query;

import java.util.List;

public interface CsvReader {

    public List<List<String>> readCsv(String fileName);
}

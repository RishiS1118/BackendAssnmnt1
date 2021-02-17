package com.hashedin.domain;

import com.hashedin.query.CsvReader;
import com.hashedin.query.Delimeter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadCsvImplementation implements CsvReader {
    @Override
    public List<List<String>> readCsv(String fileName) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(Delimeter.TAB_DELIMITER.getDelimiter());
                records.add(Arrays.asList(values));
            }
        }
        catch(Exception e) {

        }
        return records;
    }
}

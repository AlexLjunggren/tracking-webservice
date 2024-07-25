package io.ljunggren.tracking.webservice.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.ljunggren.csv.parser.Parser;
import ljunggren.io.excel.parser.model.SimpleCell;
import ljunggren.io.excel.parser.model.SimpleRow;
import ljunggren.io.excel.parser.model.SimpleSheet;
import ljunggren.io.excel.parser.model.SimpleWorkbook;

public class CSVUtils {

    public static SimpleWorkbook csvToWorkbook(File file) throws Exception {
        Parser parser = new Parser().firstRowIsHeader();
        List<String> headers = parser.parseHeaders(file);
        List<List<String>> data = parser.parse(file);
        return generateSimpleWorkbook(file, headers, data);
    }
    
    private static SimpleWorkbook generateSimpleWorkbook(File file, List<String> headers, List<List<String>> data) {
        List<SimpleSheet> sheets = Arrays.asList(new SimpleSheet[] {generateSheet(headers, data)}); 
        return new SimpleWorkbook(file.getName(), sheets);
    }
    
    private static SimpleSheet generateSheet(List<String> headers, List<List<String>> data) {
        return new SimpleSheet(null, 
                generateRow(headers), 
                generateRows(data));
    }
    
    private static List<SimpleRow> generateRows(List<List<String>> data) {
        return data.stream().map(values -> generateRow(values)).collect(Collectors.toList());
    }
    
    private static SimpleRow generateRow(List<String> values) {
        return new SimpleRow(values.stream().map(value -> new SimpleCell(value)).collect(Collectors.toList()));
    }
    
}

package io.ljunggren.tracking.webservice.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParsedCSV {

    private List<String> headers;
    private List<List<String>> data;
    
}

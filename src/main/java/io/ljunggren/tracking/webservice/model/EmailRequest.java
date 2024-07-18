package io.ljunggren.tracking.webservice.model;

import lombok.Data;

@Data
public class EmailRequest {

    private String to;
    private String subject;
    private String body;
    
}

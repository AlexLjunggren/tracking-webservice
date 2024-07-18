package io.ljunggren.tracking.webservice.model;

import io.ljunggren.sanitizer.annotation.Trim;
import io.ljunggren.tracking.webservice.Service;
import io.ljunggren.validator.annotation.NotNull;
import lombok.Data;

@Data
public class TrackingRequest {

    @NotNull
    @Trim
    private String trackingNumber;
    @NotNull
    private Service service;
    
}

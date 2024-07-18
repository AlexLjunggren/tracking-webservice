package io.ljunggren.tracking.webservice.model;

import io.ljunggren.sanitizer.annotation.Trim;
import io.ljunggren.tracking.webservice.Service;
import io.ljunggren.validator.annotation.Email;
import io.ljunggren.validator.annotation.NotEmpty;
import io.ljunggren.validator.annotation.NotNull;
import lombok.Data;

@Data
public class BatchTrackingRequest {

    @NotEmpty
    private String[] trackingNumbers;
    @Email
    @Trim
    private String email;
    @NotNull
    private Service service;
    
}

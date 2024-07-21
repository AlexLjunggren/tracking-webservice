package io.ljunggren.tracking.webservice.model.parcel;

import io.ljunggren.reportGenerator.annotation.AutoSize;
import io.ljunggren.reportGenerator.annotation.Reportable;
import io.ljunggren.sanitizer.annotation.Trim;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseParcel implements Parcel {

    @Trim
    @AutoSize
    @Reportable(headerName = "Tracking Number", column = "A")
    private String trackingNumber;
    
    @AutoSize
    @Reportable(headerName = "Service", column = "B")
    private String service;

    @AutoSize
    @Reportable(headerName = "Status", column = "C")
    private String status;
    
    @AutoSize
    @Reportable(headerName = "Message", column = "D")
    private String message;

}

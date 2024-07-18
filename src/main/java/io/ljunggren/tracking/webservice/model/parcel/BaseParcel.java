package io.ljunggren.tracking.webservice.model.parcel;

import io.ljunggren.csvParser.annotation.CSVColumn;
import io.ljunggren.reportGenerator.annotation.Reportable;
import io.ljunggren.sanitizer.annotation.Accent;
import io.ljunggren.sanitizer.annotation.Trim;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseParcel implements Parcel {

    @Trim
    @CSVColumn("A")
    @Reportable(headerName = "Tracking Number", column = "A")
    private String trackingNumber;
    
    @Accent
    @Reportable(headerName = "Service", column = "B")
    private String service;

    @Reportable(headerName = "Status", column = "C")
    private String status;
    
    @Reportable(headerName = "Message", column = "D")
    private String message;

}

package io.ljunggren.tracking.webservice.model.parcel;

import java.util.Date;

import io.ljunggren.report.generator.annotation.AutoSize;
import io.ljunggren.report.generator.annotation.DateFormatter;
import io.ljunggren.report.generator.annotation.Hyperlink;
import io.ljunggren.report.generator.annotation.Reportable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FedexParcel extends BaseParcel {

    @DateFormatter(format = "yyyy-MM-dd hh:mm aa")
    @AutoSize
    @Reportable(headerName = "Picked Up", column = "E")
    private Date pickedUp;
    
    @DateFormatter(format = "yyyy-MM-dd hh:mm aa")
    @AutoSize
    @Reportable(headerName = "Delivered", column = "F")
    private Date delivered;
    
    @AutoSize
    @Hyperlink
    @Reportable(headerName = "Link", column = "G")
    private String link;
    
}

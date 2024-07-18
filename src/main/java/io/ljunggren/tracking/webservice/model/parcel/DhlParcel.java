package io.ljunggren.tracking.webservice.model.parcel;

import java.util.Date;

import io.ljunggren.reportGenerator.annotation.DateFormatter;
import io.ljunggren.reportGenerator.annotation.Reportable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DhlParcel extends BaseParcel {

    @DateFormatter(format = "yyyy-MM-dd hh:mm aa")
    @Reportable(headerName = "Delivered", column = "E")
    private Date delivered;
    
}

package io.ljunggren.tracking.webservice.workflow;

import java.nio.file.Path;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import io.ljunggren.tracking.webservice.Service;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowData {

    private Service service;
    private String email;
    private List<Parcel> parcels;
    private Workbook workbook;
    private Path outputPath;
    
}

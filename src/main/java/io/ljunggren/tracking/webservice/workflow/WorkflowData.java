package io.ljunggren.tracking.webservice.workflow;

import java.nio.file.Path;
import java.util.List;

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

    private String email;
//    private List<? extends Parcel> parcels;
    private List<Parcel> parcels;
    private String filename;
    private String csv;
    private Path outputPath;
    
}

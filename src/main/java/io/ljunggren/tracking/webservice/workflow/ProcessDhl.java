package io.ljunggren.tracking.webservice.workflow;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ljunggren.tracking.webservice.model.parcel.DhlParcel;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.service.DhlService;

@Component
public class ProcessDhl extends WorkflowChain {
    
    @Autowired
    private DhlService dhlService;

    @Override
    public void execute(WorkflowData data) throws Exception {
        if (isDhlParcel(data.getParcels())) {
            dhlService.trackParcels(data.getParcels());
        }
        nextChain.execute(data);
    }
    
    private boolean isDhlParcel(List<? extends Parcel> parcels) {
        if (CollectionUtils.isEmpty(parcels)) {
            return false;
        }
        return parcels.get(0) instanceof DhlParcel;
    }
    
}

package io.ljunggren.tracking.webservice.workflow;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.model.parcel.UspsParcel;
import io.ljunggren.tracking.webservice.service.UspsService;

@Component
public class ProcessUsps extends WorkflowChain {
    
    @Autowired
    private UspsService uspsService;

    @Override
    public void execute(WorkflowData data) throws Exception {
        if (isUspsParcel(data.getParcels())) {
            uspsService.trackParcels(data.getParcels());
        }
        nextChain.execute(data);
    }
    
    private boolean isUspsParcel(List<? extends Parcel> parcels) {
        if (CollectionUtils.isEmpty(parcels)) {
            return false;
        }
        return parcels.get(0) instanceof UspsParcel;
    }
    
}

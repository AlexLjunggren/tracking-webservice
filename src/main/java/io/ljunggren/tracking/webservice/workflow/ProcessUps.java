package io.ljunggren.tracking.webservice.workflow;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.model.parcel.UpsParcel;
import io.ljunggren.tracking.webservice.service.UpsService;

@Component
public class ProcessUps extends WorkflowChain {
    
    @Autowired
    private UpsService upsService;

    @Override
    public void execute(WorkflowData data) throws Exception {
        if (isUpsParcel(data.getParcels())) {
            upsService.trackParcels(data.getParcels());
        }
        nextChain.execute(data);
    }
    
    private boolean isUpsParcel(List<? extends Parcel> parcels) {
        if (CollectionUtils.isEmpty(parcels)) {
            return false;
        }
        return parcels.get(0) instanceof UpsParcel;
    }
    
}

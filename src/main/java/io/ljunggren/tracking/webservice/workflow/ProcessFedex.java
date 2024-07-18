package io.ljunggren.tracking.webservice.workflow;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ljunggren.tracking.webservice.model.parcel.FedexParcel;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.service.FedexService;

@Component
public class ProcessFedex extends WorkflowChain {
    
    @Autowired
    private FedexService fedexService;

    @Override
    public void execute(WorkflowData data) throws Exception {
        if (isFedexParcel(data.getParcels())) {
            fedexService.trackParcels(data.getParcels());
        }
        nextChain.execute(data);
    }
    
    private boolean isFedexParcel(List<? extends Parcel> parcels) {
        if (CollectionUtils.isEmpty(parcels)) {
            return false;
        }
        return parcels.get(0) instanceof FedexParcel;
    }
    
}

package io.ljunggren.tracking.webservice.response.dhl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import io.ljunggren.dhl.api.response.Shipment;
import io.ljunggren.dhl.api.response.ShipmentEvent;
import io.ljunggren.dhl.api.response.TrackingResponse;
import io.ljunggren.tracking.webservice.model.parcel.DhlParcel;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.response.ResponseChain;

public class DhlDataResponse extends ResponseChain {

    @Override
    public void execute(List<Parcel> parcels, Object response) {
        if (parcels.get(0) instanceof DhlParcel == false) {
            nextChain.execute(parcels, response);
            return;
        }
        TrackingResponse dhlResponse = (TrackingResponse) response;
        if (dhlResponse.getShipments() == null) {
            nextChain.execute(parcels, response);
            return;
        }
        setDetails(parcels, dhlResponse);
    }
    
    private void setDetails(List<Parcel> parcels, TrackingResponse response) {
        response.getShipments().forEach(shipment -> {
            String trackingNumber = shipment.getId();
            List<DhlParcel> filteredParcels = parcels.stream()
                    .map(parcel -> (DhlParcel) parcel)
                    .filter(p -> p.getTrackingNumber().equals(trackingNumber))
                    .collect(Collectors.toList());
            filteredParcels.forEach(parcel -> setDetails(parcel, shipment));
        });
    }
    
    private void setDetails(DhlParcel parcel, Shipment shipment) {
        parcel.setService(shipment.getService());
        ShipmentEvent status = shipment.getStatus();
        if (status != null) {
            setShipmentEventDetails(parcel, status);
            return;
        }
        List<ShipmentEvent> events = shipment.getEvents();
        if (!CollectionUtils.isEmpty(events)) {
            setShipmentEventDetails(parcel, events.get(0));
            return;
        }
    }
    
    private void setShipmentEventDetails(DhlParcel parcel, ShipmentEvent shipmentEvent) {
        parcel.setStatus(shipmentEvent.getStatusCode());
        parcel.setDelivered(getDeliveredDate(shipmentEvent));
        parcel.setMessage(shipmentEvent.getDescription());
    }
    
    private Date getDeliveredDate(ShipmentEvent shipmentEvent) {
        if ("delivered".equalsIgnoreCase(shipmentEvent.getStatus())) {
            return shipmentEvent.getTimestamp();
        }
        return null;
    }

}

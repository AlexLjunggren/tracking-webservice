package io.ljunggren.tracking.webservice.response.ups;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.model.parcel.UpsParcel;
import io.ljunggren.tracking.webservice.response.ResponseChain;
import io.ljunggren.tracking.webservice.util.UpsUtils;
import io.ljunggren.ups.api.UpsResponse;
import io.ljunggren.ups.api.model.tracking.response.DeliveryDate;
import io.ljunggren.ups.api.model.tracking.response.DeliveryTime;
import io.ljunggren.ups.api.model.tracking.response.Package;
import io.ljunggren.ups.api.model.tracking.response.Shipment;
import io.ljunggren.ups.api.model.tracking.response.TrackingResponse;

public class UpsDataResponse extends ResponseChain {

    @Override
    public void execute(List<Parcel> parcels, Object response) {
        if (parcels.get(0) instanceof UpsParcel == false) {
            nextChain.execute(parcels, response);
            return;
        }
        UpsResponse upsResponse = (UpsResponse) response;
        if (upsResponse.getTrackingResponse() == null) {
            nextChain.execute(parcels, response);
            return;
        }
        setTrackingResponse(parcels, upsResponse);
    }
    
    private void setTrackingResponse(List<Parcel> parcels, UpsResponse response) {
        parcels.forEach(parcel -> setTrackingResponse((UpsParcel) parcel, response));
    }
    
    private void setTrackingResponse(UpsParcel parcel, UpsResponse response) {
        TrackingResponse trackingResponse = response.getTrackingResponse();
        List<Shipment> shipments = trackingResponse.getShipments();
        if (CollectionUtils.isEmpty(shipments)) {
            parcel.setStatus("Error");
            parcel.setMessage("Shipments is null");
            return;
        }
        Shipment shipment = shipments.get(0);
        if (hasWarnings(shipment)) {
            parcel.setStatus("Warning");
            parcel.setMessage(shipment.getWarnings().get(0).getMessage());
            return;
        }
        if (CollectionUtils.isEmpty(shipment.getPackages())) {
            parcel.setStatus("Error");
            parcel.setMessage("Packages is null");
            return;
        }
        Package pack = shipment.getPackages().get(0);
        if (pack.getService() != null) {
            parcel.setService(pack.getService().getDescription());
        }
        if (pack.getCurrentStatus() != null) {
            parcel.setStatus(pack.getCurrentStatus().getDescription());
        }
        if (!CollectionUtils.isEmpty(pack.getDeliveryDates())) {
            parcel.setDelivered(getDeliveredDate(pack));
        }
    }
    
    private boolean hasWarnings(Shipment shipment) {
        return !CollectionUtils.isEmpty(shipment.getWarnings());
    }
    
    private Date getDeliveredDate(Package pack) {
        List<DeliveryDate> deliveryDates = pack.getDeliveryDates();
        UpsUtils.sortDeliveryDatesDesc(deliveryDates);
        DeliveryDate deliveryDate = deliveryDates.stream()
                .filter(d -> d.getType().equals("DEL"))
                .findFirst()
                .orElse(null);
        if (deliveryDate == null) {
            return null;
        }
        DeliveryTime deliveryTime = pack.getDeliveryTime();
        return parseDate(deliveryDate, deliveryTime);
    }
    
    private Date parseDate(DeliveryDate deliveryDate, DeliveryTime deliveryTime) {
        try {
            if (deliveryTime == null) {
                return new SimpleDateFormat("yyyyMMdd").parse(deliveryDate.getDate());
            }
            return new SimpleDateFormat("yyyyMMddHHmmss").parse(deliveryDate.getDate() + deliveryTime.getEndTime());
        } catch (ParseException e) {
            return null;
        }
    }

}

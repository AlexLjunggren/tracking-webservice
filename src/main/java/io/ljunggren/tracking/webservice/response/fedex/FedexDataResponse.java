package io.ljunggren.tracking.webservice.response.fedex;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import io.ljunggren.fedex.api.tracking.response.CompleteTrackResult;
import io.ljunggren.fedex.api.tracking.response.LatestStatusDetail;
import io.ljunggren.fedex.api.tracking.response.ScanEvent;
import io.ljunggren.fedex.api.tracking.response.ServiceDetail;
import io.ljunggren.fedex.api.tracking.response.TrackResult;
import io.ljunggren.fedex.api.tracking.response.TrackingError;
import io.ljunggren.fedex.api.tracking.response.TrackingResponse;
import io.ljunggren.fedex.api.tracking.response.TrackingStatusCode;
import io.ljunggren.tracking.webservice.model.parcel.FedexParcel;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.response.ResponseChain;
import io.ljunggren.tracking.webservice.response.fedex.message.AncillaryMessage;
import io.ljunggren.tracking.webservice.response.fedex.message.CatchAllMessage;
import io.ljunggren.tracking.webservice.response.fedex.message.MessageChain;
import io.ljunggren.tracking.webservice.response.fedex.message.ReceivedByMessage;
import io.ljunggren.tracking.webservice.response.fedex.message.ServiceMessage;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.MultipleTrackResult;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.NullTrackResult;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.SingleTrackResult;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.TrackResultChain;
import io.ljunggren.tracking.webservice.util.FedexUtils;

public class FedexDataResponse extends ResponseChain {

    @Override
    public void execute(List<Parcel> parcels, Object response) {
        if (parcels.get(0) instanceof FedexParcel == false) {
            nextChain.execute(parcels, response);
            return;
        }
        TrackingResponse fedexResponse = (TrackingResponse) response;
        if (fedexResponse.getOutput() == null || fedexResponse.getOutput().getCompleteTrackResults() == null) {
            nextChain.execute(parcels, response);
            return;
        }
        setTrackingResponse(parcels, fedexResponse);
    }

    private void setTrackingResponse(List<Parcel> parcels, TrackingResponse response) {
        List<CompleteTrackResult> completeTrackResults = response.getOutput().getCompleteTrackResults();
        completeTrackResults.forEach(completeTrackResult -> {
            String trackingNumber = completeTrackResult.getTrackingNumber();
            List<FedexParcel> filteredParcels = parcels.stream()
                    .map(parcel -> (FedexParcel) parcel)
                    .filter(p -> p.getTrackingNumber().equals(trackingNumber))
                    .collect(Collectors.toList());
            filteredParcels.forEach(parcel -> setDetails(parcel, completeTrackResult));
        });
    }
        
    private void setDetails(FedexParcel parcel, CompleteTrackResult completeTrackResult) {
        TrackResult trackResult = getTrackResult(completeTrackResult);
        FedexUtils.sortScanEventsDesc(trackResult.getScanEvents());
        parcel.setStatus(getStatus(trackResult));
        parcel.setPickedUp(getPickupDate(trackResult));
        parcel.setDelivered(getDeliveredDate(trackResult));
        parcel.setService(getService(trackResult));
        parcel.setMessage(getMessage(trackResult));
        parcel.setLink(getLinkUrl(parcel.getTrackingNumber()));
    }
    
    private TrackResult getTrackResult(CompleteTrackResult completeTrackResult) {
        TrackResultChain chain = new NullTrackResult().nextChain(
                new SingleTrackResult().nextChain(
                new MultipleTrackResult()
        ));
        return chain.getSingle(completeTrackResult.getTrackResults());
    }
    
    private String getStatus(TrackResult trackResult) {
        LatestStatusDetail latestStatusDetail = trackResult.getLatestStatusDetail();
        if (latestStatusDetail != null) {
            return latestStatusDetail.getDescription();
        }
        TrackingError trackingError = trackResult.getError();
        if (trackingError != null) {
            return trackingError.getMessage();
        }
        return null;
    }
    
    private Date getPickupDate(TrackResult trackResult) {
        List<ScanEvent> scanEvents = trackResult.getScanEvents();
        if (CollectionUtils.isEmpty(scanEvents)) {
            return null;
        }
        List<TrackingStatusCode> pickedUpCodes = Arrays.asList(new TrackingStatusCode[] {
                TrackingStatusCode.PU,
                TrackingStatusCode.PX
        });
        return scanEvents.stream()
                .filter(scanEvent -> pickedUpCodes.contains(TrackingStatusCode.lookup(scanEvent.getEventType())))
                .map(scanEvent -> scanEvent.getDate())
                .reduce((first, second) -> second)
                .orElse(null);
    }
    
    private Date getDeliveredDate(TrackResult trackResult) {
        List<ScanEvent> scanEvents = trackResult.getScanEvents();
        if (CollectionUtils.isEmpty(scanEvents)) {
            return null;
        }
        return scanEvents.stream()
                .filter(scanEvent -> TrackingStatusCode.DL.equals(TrackingStatusCode.lookup(scanEvent.getEventType())))
                .map(scanEvent -> scanEvent.getDate())
                .findFirst()
                .orElse(null);
    }
    
    private String getService(TrackResult trackResult) {
        ServiceDetail serviceDetail = trackResult.getServiceDetail();
        return serviceDetail == null ? null : serviceDetail.getDescription();
    }
    
    private String getMessage(TrackResult trackResult) {
        MessageChain chain = 
                new AncillaryMessage().nextChain(
                new ReceivedByMessage().nextChain(
                new ServiceMessage().nextChain(
                new CatchAllMessage()
        )));
        return chain.getMessage(trackResult);
    }
    
    private String getLinkUrl(String trackingNumber) {
        return String.format("https://www.fedex.com/fedextrack/?trknbr=%s", trackingNumber);
    }
    
}

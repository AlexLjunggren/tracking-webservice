package io.ljunggren.tracking.webservice.response.fedex.message;

import io.ljunggren.fedex.api.tracking.response.DeliveryDetail;
import io.ljunggren.fedex.api.tracking.response.TrackResult;

public class ReceivedByMessage extends MessageChain {

    @Override
    public String getMessage(TrackResult trackResult) {
        String receivedByMessage = getReceivedByMessage(trackResult);
        if (receivedByMessage != null) {
            return receivedByMessage;
        }
        return nextChain.getMessage(trackResult);
    }

    private String getReceivedByMessage(TrackResult trackResult) {
        DeliveryDetail deliveryDetail = trackResult.getDeliveryDetail();
        if (deliveryDetail == null) {
            return null;
        }
        return deliveryDetail.getReceivedByName() == null ? null : 
            String.format("Signed for by: %s", deliveryDetail.getReceivedByName());
    }
    
}

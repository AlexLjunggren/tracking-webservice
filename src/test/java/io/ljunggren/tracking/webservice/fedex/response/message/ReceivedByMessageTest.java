package io.ljunggren.tracking.webservice.fedex.response.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.ljunggren.fedex.api.tracking.response.DeliveryDetail;
import io.ljunggren.fedex.api.tracking.response.TrackResult;
import io.ljunggren.tracking.webservice.response.fedex.message.CatchAllMessage;
import io.ljunggren.tracking.webservice.response.fedex.message.MessageChain;
import io.ljunggren.tracking.webservice.response.fedex.message.ReceivedByMessage;

public class ReceivedByMessageTest {
    
    private MessageChain chain = new ReceivedByMessage().nextChain(
            new CatchAllMessage());

    @Test
    public void executeTest() {
        DeliveryDetail deliveryDetail = new DeliveryDetail();
        deliveryDetail.setReceivedByName("JDoe");
        TrackResult trackResult = new TrackResult();
        trackResult.setDeliveryDetail(deliveryDetail);
        String actual = chain.getMessage(trackResult);
        assertEquals("Signed for by: JDoe", actual);
    }
    
    @Test
    public void executeNullReceivedByTest() {
        TrackResult trackResult = new TrackResult();
        trackResult.setDeliveryDetail(new DeliveryDetail());
        String actual = chain.getMessage(trackResult);
        assertNull(actual);
    }
    
    @Test
    public void executeNullDeliveryDetailTest() {
        TrackResult trackResult = new TrackResult();
        String actual = chain.getMessage(trackResult);
        assertNull(actual);
    }

}

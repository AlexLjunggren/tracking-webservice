package io.ljunggren.tracking.webservice.fedex.response.message;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.ljunggren.fedex.api.tracking.response.TrackResult;
import io.ljunggren.tracking.webservice.response.fedex.message.CatchAllMessage;
import io.ljunggren.tracking.webservice.response.fedex.message.MessageChain;

public class CatchAllMessageTest {
    
    private MessageChain chain = new CatchAllMessage();

    @Test
    public void executeTest() {
        TrackResult trackResult = new TrackResult();
        String actual = chain.getMessage(trackResult);
        assertNull(actual);
    }

}

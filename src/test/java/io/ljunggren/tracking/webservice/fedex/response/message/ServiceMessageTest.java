package io.ljunggren.tracking.webservice.fedex.response.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.ljunggren.fedex.api.tracking.response.ServiceCommitMessage;
import io.ljunggren.fedex.api.tracking.response.TrackResult;
import io.ljunggren.tracking.webservice.response.fedex.message.CatchAllMessage;
import io.ljunggren.tracking.webservice.response.fedex.message.MessageChain;
import io.ljunggren.tracking.webservice.response.fedex.message.ServiceMessage;

public class ServiceMessageTest {
    
    private MessageChain chain = new ServiceMessage().nextChain(
            new CatchAllMessage());

    @Test
    public void executeTest() {
        String message = "Delay Due to Weather";
        ServiceCommitMessage serviceCommitMessage = new ServiceCommitMessage();
        serviceCommitMessage.setMessage(message);
        TrackResult trackResult = new TrackResult();
        trackResult.setServiceCommitMessage(serviceCommitMessage);
        String actual = chain.getMessage(trackResult);
        assertEquals(message, actual);
    }
    
    @Test
    public void executeNullMessageTest() {
        TrackResult trackResult = new TrackResult();
        trackResult.setServiceCommitMessage(new ServiceCommitMessage());
        String actual = chain.getMessage(trackResult);
        assertNull(actual);
    }
    
    @Test
    public void excuteNullServiceCommitMessageTest() {
        TrackResult trackResult = new TrackResult();
        String actual = chain.getMessage(trackResult);
        assertNull(actual);
    }

}

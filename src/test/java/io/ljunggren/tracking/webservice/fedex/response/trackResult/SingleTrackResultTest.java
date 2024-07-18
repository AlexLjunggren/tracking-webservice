package io.ljunggren.tracking.webservice.fedex.response.trackResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.ljunggren.fedex.api.tracking.response.TrackResult;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.SingleTrackResult;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.TrackResultChain;

public class SingleTrackResultTest {
    
    private TrackResultChain chain = new SingleTrackResult().nextChain(
            new TerminatingTrackResult());
    
    private class TerminatingTrackResult extends TrackResultChain {
        @Override
        public TrackResult getSingle(List<TrackResult> trackResults) {
            return null;
        }
    }

    @Test
    public void getSingleTest() {
        TrackResult trackResult = new TrackResult();
        List<TrackResult> trackResults = Arrays.asList(new TrackResult[] { trackResult });
        TrackResult actual = chain.getSingle(trackResults);
        assertEquals(trackResult, actual);
    }
    
    @Test
    public void getSingleEmptyTest() {
        TrackResult actual = chain.getSingle(new ArrayList<>());
        assertNull(actual);
    }

}

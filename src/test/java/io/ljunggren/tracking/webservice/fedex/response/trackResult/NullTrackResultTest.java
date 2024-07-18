package io.ljunggren.tracking.webservice.fedex.response.trackResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.ljunggren.fedex.api.tracking.response.TrackResult;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.NullTrackResult;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.TrackResultChain;

public class NullTrackResultTest {
    
    private TrackResultChain chain = new NullTrackResult().nextChain(
            new TerminatingTrackResult());
    
    private class TerminatingTrackResult extends TrackResultChain {
        @Override
        public TrackResult getSingle(List<TrackResult> trackResults) {
            return null;
        }
    }

    @Test
    public void getSingleNullTest() {
        TrackResult trackResult = chain.getSingle(null);
        assertEquals(new TrackResult(), trackResult);
    }
    
    @Test
    public void getSingleEmptyTest() {
        TrackResult trackResult = chain.getSingle(new ArrayList<>());
        assertEquals(new TrackResult(), trackResult);
    }
    
    @Test
    public void getSingleHasDataTest() {
        List<TrackResult> trackResults = Arrays.asList(new TrackResult[] { new TrackResult()});
        TrackResult trackResult = chain.getSingle(trackResults);
        assertNull(trackResult);
    }

}

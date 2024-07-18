package io.ljunggren.tracking.webservice.fedex.response.trackResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.ljunggren.fedex.api.tracking.response.ScanEvent;
import io.ljunggren.fedex.api.tracking.response.TrackResult;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.MultipleTrackResult;
import io.ljunggren.tracking.webservice.response.fedex.trackResult.TrackResultChain;

public class MultipleTrackResultTest {
    
    private TrackResultChain chain = new MultipleTrackResult().nextChain(
            new TerminatingTrackResult());

    private class TerminatingTrackResult extends TrackResultChain {
        @Override
        public TrackResult getSingle(List<TrackResult> trackResults) {
            return null;
        }
    }

    @Test
    public void getSingleTest() throws ParseException {
        ScanEvent scanEvent1 = new ScanEvent();
        scanEvent1.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        ScanEvent scanEvent2 = new ScanEvent();
        scanEvent2.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-02"));
        List<ScanEvent> scanEvents12 = Arrays.asList(new ScanEvent[] {
                scanEvent1, scanEvent2
        });
        TrackResult trackResult1 = new TrackResult();
        trackResult1.setScanEvents(scanEvents12);
        ScanEvent scanEvent3 = new ScanEvent();
        scanEvent3.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-04"));
        ScanEvent scanEvent4 = new ScanEvent();
        scanEvent4.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-03"));
        List<ScanEvent> scanEvents34 = Arrays.asList(new ScanEvent[] {
                scanEvent3, scanEvent4
        });
        TrackResult trackResult2 = new TrackResult();
        trackResult2.setScanEvents(scanEvents34);
        List<TrackResult> trackResults = Arrays.asList(new TrackResult[] {
                trackResult1, trackResult2
        });
        TrackResult actual = chain.getSingle(trackResults);
        assertEquals(4, actual.getScanEvents().size());
    }
    
}

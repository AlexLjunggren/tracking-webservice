package io.ljunggren.tracking.webservice.fedex.response.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.ljunggren.fedex.api.tracking.response.AncillaryDetail;
import io.ljunggren.fedex.api.tracking.response.LatestStatusDetail;
import io.ljunggren.fedex.api.tracking.response.TrackResult;
import io.ljunggren.tracking.webservice.response.fedex.message.AncillaryMessage;
import io.ljunggren.tracking.webservice.response.fedex.message.CatchAllMessage;
import io.ljunggren.tracking.webservice.response.fedex.message.MessageChain;

public class AncillaryMessageTest {
    
    private MessageChain chain = new AncillaryMessage().nextChain(
            new CatchAllMessage());

    @Test
    public void getMessageTest() {
        String description = "Package left at front door.";
        AncillaryDetail ancillaryDetail = new AncillaryDetail();
        ancillaryDetail.setReasonDescription(description);
        List<AncillaryDetail> ancillaryDetails = Arrays.asList(new AncillaryDetail[] { ancillaryDetail });
        LatestStatusDetail latestStatusDetail = new LatestStatusDetail();
        latestStatusDetail.setAncillaryDetails(ancillaryDetails);
        TrackResult trackResult = new TrackResult();
        trackResult.setLatestStatusDetail(latestStatusDetail);
        String actual = chain.getMessage(trackResult);
        assertEquals(description, actual);
    }

    @Test
    public void getMessageMultipleAncillaryDetailsTest() {
        String description = "Package left at front door.";
        AncillaryDetail ancillaryDetail1 = new AncillaryDetail();
        AncillaryDetail ancillaryDetail2 = new AncillaryDetail();
        ancillaryDetail2.setReasonDescription(description);
        List<AncillaryDetail> ancillaryDetails = Arrays.asList(new AncillaryDetail[] { 
                ancillaryDetail1, ancillaryDetail2 
        });
        LatestStatusDetail latestStatusDetail = new LatestStatusDetail();
        latestStatusDetail.setAncillaryDetails(ancillaryDetails);
        TrackResult trackResult = new TrackResult();
        trackResult.setLatestStatusDetail(latestStatusDetail);
        String actual = chain.getMessage(trackResult);
        assertEquals(description, actual);
    }
    
    @Test
    public void getMessageHasAncillaryWithNoReasonTest() {
        AncillaryDetail ancillaryDetail = new AncillaryDetail();
        List<AncillaryDetail> ancillaryDetails = Arrays.asList(new AncillaryDetail[] { ancillaryDetail });
        LatestStatusDetail latestStatusDetail = new LatestStatusDetail();
        latestStatusDetail.setAncillaryDetails(ancillaryDetails);
        TrackResult trackResult = new TrackResult();
        trackResult.setLatestStatusDetail(latestStatusDetail);
        String actual = chain.getMessage(trackResult);
        assertNull(actual);
    }
    
    @Test
    public void getMessageNoAncillaryDetailTest() {
        TrackResult trackResult = new TrackResult();
        String actual = chain.getMessage(trackResult);
        assertNull(actual);
    }

}

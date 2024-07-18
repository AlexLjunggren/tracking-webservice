package io.ljunggren.tracking.webservice.response.fedex.message;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import io.ljunggren.fedex.api.tracking.response.AncillaryDetail;
import io.ljunggren.fedex.api.tracking.response.LatestStatusDetail;
import io.ljunggren.fedex.api.tracking.response.TrackResult;

public class AncillaryMessage extends MessageChain {

    @Override
    public String getMessage(TrackResult trackResult) {
        String ancillaryDescription = getAncillaryDescription(trackResult);
        if (ancillaryDescription != null) {
            return ancillaryDescription;
        }
        return nextChain.getMessage(trackResult);
    }

    private String getAncillaryDescription(TrackResult trackResult) {
        LatestStatusDetail latestStatusDetail = trackResult.getLatestStatusDetail();
        if (latestStatusDetail == null) {
            return null;
        }
        List<AncillaryDetail> ancillaryDetails = latestStatusDetail.getAncillaryDetails();
        if (CollectionUtils.isEmpty(ancillaryDetails)) {
            return null;
        }
        return ancillaryDetails.stream()
                .filter(ancillaryDetail -> ancillaryDetail.getReasonDescription() != null)
                .map(ancillaryDetail -> ancillaryDetail.getReasonDescription())
                .findFirst()
                .orElse(null);
    }
    
}

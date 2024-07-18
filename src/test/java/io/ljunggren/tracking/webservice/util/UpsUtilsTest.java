package io.ljunggren.tracking.webservice.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UpsUtilsTest {

    @Test
    public void isValidTrackingNumberTest() {
        assertTrue(UpsUtils.isValidTrackingNumber("1ZE427900364715892"));
        assertTrue(UpsUtils.isValidTrackingNumber("802405914305104882"));
    }

}

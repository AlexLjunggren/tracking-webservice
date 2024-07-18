package io.ljunggren.tracking.webservice.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProgressTest {

    @Test
    public void instantiationTest() {
        String message = "Progress";
        int size = 10;
        Progress progress = new Progress(message, size);
        assertEquals(message, progress.getMessage());
        assertEquals(size, progress.getSize());
    }
    
    @Test
    public void instantiationWithBatchTest() {
        String message = "Progress";
        Progress progress = new Progress(message, 10, 3);
        assertEquals(message, progress.getMessage());
        assertEquals(4, progress.getSize());
    }
    
}

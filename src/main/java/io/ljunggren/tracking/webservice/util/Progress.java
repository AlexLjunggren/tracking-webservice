package io.ljunggren.tracking.webservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

@Getter
public class Progress {

    private String message;
    private int size;
    private int index;
    
    private static Logger logger = LoggerFactory.getLogger(Progress.class);
    
    public Progress(String message, int size) {
        this.message = message;
        this.size = size;
    }
    
    public Progress(String message, int size, int batch) {
        this.message = message;
        this.size = (int) Math.ceil(size / (double) batch);
    }

    public void log() {
        logger.info(String.format("%s [%d of %d]", message, ++index, size));
    }
    
}

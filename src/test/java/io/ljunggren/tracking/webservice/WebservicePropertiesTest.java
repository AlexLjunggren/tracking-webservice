package io.ljunggren.tracking.webservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import io.ljunggren.fedex.api.FedexEnvironment;
import io.ljunggren.tracking.webservice.property.WebserviceProperties;

public class WebservicePropertiesTest {

    @Test
    public void getFedexOauthClientIdTest() {
        String clientId = WebserviceProperties.getFedexOauthClientId();
        assertNotNull(clientId);
    }
    
    @Test
    public void getFedexOauthClientSecretTest() {
        String clientSecret = WebserviceProperties.getFedexOauthClientSecret();
        assertNotNull(clientSecret);
    }
    
    @Test
    public void getFedexEnvironmentTest() {
        FedexEnvironment environment = WebserviceProperties.getFedexEnvironment();
        assertTrue(Arrays.asList(FedexEnvironment.values()).contains(environment));
    }
    
    @Test
    public void getFileTmpDirectory() {
        String tmpDirectory = WebserviceProperties.getFileTmpDirectory();
        assertNotNull(tmpDirectory);
    }
    
    @Test
    public void getFileOutputDirectory() {
        String outputDirectory = WebserviceProperties.getFileOutputDirectory();
        assertNotNull(outputDirectory);
    }
    
}

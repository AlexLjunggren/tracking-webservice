package io.ljunggren.tracking.webservice.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.ljunggren.dhl.api.DhlEnvironment;
import io.ljunggren.fedex.api.FedexEnvironment;
import io.ljunggren.ups.api.UpsEnvironment;
import io.ljunggren.usps.api.UspsEnvironment;

public class WebserviceProperties {

    private static Properties properties = new Properties();
    private static final String EXTERNAL_PROPERTIES_PATH = "/usr/src/tracking-webservice.properties";
    private static final String INTERNAL_PROPERTIES_PATH = "/properties/tracking-webservice.properties.local";

    static {
        try {
            InputStream inputStream = getPropertiesFile();
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration properties", e);
        }
    }

    private static InputStream getPropertiesFile() throws FileNotFoundException {
        File propertyFile = new File(EXTERNAL_PROPERTIES_PATH);
        if (propertyFile.exists()) {
            return new FileInputStream(propertyFile);
        }
        return WebserviceProperties.class.getResourceAsStream(INTERNAL_PROPERTIES_PATH);
    }
    
    public static String getEnvironment() {
        return properties.getProperty("environment");
    }
    
    public static String getFedexOauthClientId() {
        return properties.getProperty("fedex.oauth.clientId");
    }
    public static String getFedexOauthClientSecret() {
        return properties.getProperty("fedex.oauth.clientSecret");
    }
    public static FedexEnvironment getFedexEnvironment() {
        String fedexEnvironment = properties.getProperty("fedex.environment");
        return FedexEnvironment.valueOf(fedexEnvironment);
    }
    
    public static String getUpsOauthClientId() {
        return properties.getProperty("ups.oauth.clientId");
    }
    public static String getUpsOauthClientSecret() {
        return properties.getProperty("ups.oauth.clientSecret");
    }
    public static String getUpsAccountNumber() {
        return properties.getProperty("ups.account.number");
    }
    public static UpsEnvironment getUpsEnvironment() {
        String upsEnvironment = properties.getProperty("ups.environment");
        return UpsEnvironment.valueOf(upsEnvironment);
    }
    
    public static String getDhlApiKey() {
        return properties.getProperty("dhl.api.key");
    }
    public static DhlEnvironment getDhlEnvironment() {
        String dhlEnvironment = properties.getProperty("dhl.environment");
        return DhlEnvironment.valueOf(dhlEnvironment);
    }
    
    public static String getUspsUsername() {
        return properties.getProperty("usps.username");
    }
    public static UspsEnvironment getUspsEnvironment() {
        String uspsEnvironment = properties.getProperty("usps.environment");
        return UspsEnvironment.valueOf(uspsEnvironment);
    }
    
    public static String getFileTmpDirectory() {
        return properties.getProperty("file.tmp.directory");
    }
    public static String getFileOutputDirectory() {
        return properties.getProperty("file.output.directory");
    }
    public static String getFileErrorDirectory() {
        return properties.getProperty("file.error.directory");
    }
    
    public static String getAnnouncementDirectory() {
        return properties.getProperty("announcements.directory");
    }
    
}

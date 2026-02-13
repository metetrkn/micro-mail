package se.hitract.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import se.hitract.model.enums.INTEGRATION_TYPE;

@Service
@PropertySources({
	@PropertySource("classpath:application.properties")
})
public class PropertiesService {

    @Value("${app.version}")
    private String version;

    @Value("${app.environment}")
    private String environment;

    @Value("${runRedisImports:true}")
    private boolean runRedisImports;
    
    @Value("${queue.enable}")
    private boolean queueEnable;

//    @Value("${fortnox.clientId}")
//    private String FORTNOX_CLIENT_ID;
//
//    @Value("${fortnox.clientPassword}")
//    private String FORTNOX_CLIENT_PASSWORD;
//
//    @Value("${instagram.clientId}")
//    private String INSTAGRAM_CLIENT_ID;
//
//    @Value("${instagram.clientPassword}")
//    private String INSTAGRAM_CLIENT_PASSWORD;
//
//    @Value("${linkedIn.apiId}")
//    private String LINKEDIN_CLIENT_ID;
//
//    @Value("${linkedIn.secret}")
//    private String LINKEDIN_CLIENT_SECRET;
//
//    @Value("${tikTok.apiId.sandbox}")
//    private String TIKTOK_CLIENT_ID_SANDBOX;
//
//    @Value("${tikTok.secret.sandbox}")
//    private String TIKTOK_CLIENT_SECRET_SANDBOX;
//
//    @Value("${tikTok.apiId.prod}")
//    private String TIKTOK_CLIENT_ID_PROD;
//
//    @Value("${tikTok.secret.prod}")
//    private String TIKTOK_CLIENT_SECRET_PROD;

    public boolean getQueueEnable() {
    	return queueEnable;
    }
    
    public String getVersion() {
        return version;
    }

    public String getEnvironment() {
        return environment;
    }
    
    public boolean isProd() {
    	return environment.equals("PROD");
    }

    public boolean isTest() {
        return environment.equals("TEST");
    }

    public boolean isDev() {
        return environment.equals("DEV");
    }

    public boolean runRedisImports() {
        return runRedisImports;
    }

//    public String getFortnoxClientId() {
//        return FORTNOX_CLIENT_ID;
//    }
//
//    public String getFortnoxClientPassword() {
//        return FORTNOX_CLIENT_PASSWORD;
//    }
//
//    public String getInstagramClientId() {
//        return INSTAGRAM_CLIENT_ID;
//    }
//
//    public String getInstagramClientPassword() {
//        return INSTAGRAM_CLIENT_PASSWORD;
//    }
//
//    public String getLinkedinClientId() {
//        return LINKEDIN_CLIENT_ID;
//    }
//
//    public String getLinkedinClientSecret() {
//        return LINKEDIN_CLIENT_SECRET;
//    }
//
//    public String getTikTokClientId() {
//        return isProd() ? TIKTOK_CLIENT_ID_PROD : TIKTOK_CLIENT_ID_SANDBOX;
//    }
//
//    public String getTikTokClientSecret() {
//        return isProd() ? TIKTOK_CLIENT_SECRET_PROD : TIKTOK_CLIENT_SECRET_SANDBOX;
//    }


    public String getRedirectUri(INTEGRATION_TYPE integrationType) {
        String basePath = switch (getEnvironment()) {
            case "PROD" -> "https://hitclub.hitract.se/integration";
            case "DEMO" -> "https://hitclubdemo.hitract.se";
            case "TEST" -> integrationType.equals(INTEGRATION_TYPE.TIKTOK) ? "https://hitclubtest.hitract.se/integration" : "https://localhost:8083/integration";
            default -> "https://localhost:8083/integration";
        };

        String path = switch (integrationType) {
            case INSTAGRAM -> "/instagram/callback";
            case LINKEDIN -> "/linkedin/callback";
            case TIKTOK -> "/tiktok/callback";
            case FORTNOX -> "/fortnox/callback/";
            case SIMPLESIGN -> "/simplesign/callback";
        };

        return basePath + path;
    }

}

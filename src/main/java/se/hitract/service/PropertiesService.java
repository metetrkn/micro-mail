package se.hitract.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import se.hitract.model.enums.INTEGRATION_TYPE;

@Service

public class PropertiesService {

    @Value("${app.version}")
    private String version;

    @Value("${app.environment:DEV}") // Added a default here just in case
    private String environment;

    @Value("${runRedisImports:true}")
    private boolean runRedisImports;

    @Value("${queue.enable:true}")
    private boolean queueEnable;

    @Getter
    @Value("${main.node:true}") // NEW: Added this
    private boolean mainNode;

    @Value("${rqueue.scheduler.polling.interval:5000}") // NEW: Added this
    private long rqueuePollingInterval;

    @Value("${rqueue.retry.default.backoff:5000}") // NEW: Added this
    private long rqueueBackoff;


    public long getRqueuePollingInterval() {
        return rqueuePollingInterval;
    }

    public long getRqueueBackoff() {
        return rqueueBackoff;
    }

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
        return "PROD".equalsIgnoreCase(environment);
    }

    public boolean isTest() {
        return "TEST".equalsIgnoreCase(environment);
    }

    public boolean isDev() {
        return "DEV".equalsIgnoreCase(environment);
    }

    public boolean runRedisImports() {
        return runRedisImports;
    }


}

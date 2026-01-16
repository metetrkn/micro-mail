package se.hitract.config;

import com.github.sonus21.rqueue.core.Job;
import com.github.sonus21.rqueue.core.middleware.Middleware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.concurrent.Callable;

/**
 * For global RQUEU errors, that occur in the queue not in the consumer
 * */
@Component
@Slf4j
public class RqueueGlobalErrorLogger implements Middleware {

    @Override
    public void handle(Job job, Callable<Void> next) throws Exception {
        try {
            next.call();
        } catch (Exception e) {
            // Get raw message directly from RqueueMessage
            String rawPayload = job.getRqueueMessage().getMessage();

            // We use a specific prefix to make searching easier
            log.error("RQUEUE_FAILURE | Payload: {} | Error: {}", rawPayload, e.getMessage());

            throw e;
        }
    }
}
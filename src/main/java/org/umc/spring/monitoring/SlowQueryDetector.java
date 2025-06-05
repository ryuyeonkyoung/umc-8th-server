import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class SlowQueryDetector {

    private final MeterRegistry meterRegistry;

    @Value("${monitoring.slow-query.threshold:1000}")
    private long slowQueryThreshold;

    @Value("${monitoring.slow-query.alert-enabled:true}")
    private boolean alertEnabled;

    @EventListener
    public void handleSlowQuery(SlowQueryEvent event) {
        if (event.getExecutionTime() > slowQueryThreshold) {
            meterRegistry.counter("database.slow.queries",
                    "method", event.getMethodName(),
                    "type", event.getQueryType())
                    .increment();
            meterRegistry.timer("database.slow.query.histogram",
                    "method", event.getMethodName())
                    .record(event.getExecutionTime(), java.util.concurrent.TimeUnit.MILLISECONDS);
            log.warn("🐌 [SLOW QUERY DETECTED] Time: {} | Execution: {}ms | Method: {} | Type: {} | Threshold: {}ms",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    event.getExecutionTime(),
                    event.getMethodName(),
                    event.getQueryType(),
                    slowQueryThreshold);
            if (alertEnabled) {
                sendAlert(event);
            }
        }
    }

    private void sendAlert(SlowQueryEvent event) {
        if (event.getExecutionTime() > 5000) {
            log.error("🚨 [CRITICAL SLOW QUERY] {}ms in {} - Immediate attention required!",
                    event.getExecutionTime(), event.getMethodName());
        }
        log.info("📨 Alert notification prepared for slow query: {}ms in {}",
                event.getExecutionTime(), event.getMethodName());
        // Example Discord webhook call (commented out):
        // discordNotificationService.sendSlowQueryAlert(event);
    }
}
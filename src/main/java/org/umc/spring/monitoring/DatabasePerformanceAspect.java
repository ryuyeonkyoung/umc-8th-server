package org.umc.spring.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DatabasePerformanceAspect {

    private final MeterRegistry meterRegistry;
    private final ApplicationEventPublisher eventPublisher;

    // Repository 계층 모니터링: 성공/실패 및 슬로우 쿼리 감지
    @Around("execution(* org.umc.spring.repository..*Repository.*(..))")
    public Object monitorRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Timer.Sample sample = Timer.start(meterRegistry);
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            sample.stop(Timer.builder("database.repository.query.duration")
                    .tag("repository", className)
                    .tag("method", methodName)
                    .tag("status", "success")
                    .register(meterRegistry));

            if (executionTime > 1000) {
                eventPublisher.publishEvent(new SlowQueryEvent(
                    className + "." + methodName,
                    executionTime,
                    "Repository Method"
                ));
            }

            return result;
        } catch (Exception e) {
            sample.stop(Timer.builder("database.repository.query.duration")
                    .tag("repository", className)
                    .tag("method", methodName)
                    .tag("status", "error")
                    .register(meterRegistry));

            meterRegistry.counter("database.repository.query.errors",
                    "repository", className,
                    "method", methodName,
                    "exception", e.getClass().getSimpleName())
                    .increment();

            throw e;
        }
    }

    // Command/Query 서비스 계층 메서드 실행 시간 모니터링
    @Around("execution(* org.umc.spring.service..*CommandService*.*(..)) || " +
            "execution(* org.umc.spring.service..*QueryService*.*(..))")
    public Object monitorServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Timer.Sample sample = Timer.start(meterRegistry);
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String serviceType = className.contains("Command") ? "command" : "query";

        try {
            Object result = joinPoint.proceed();

            sample.stop(Timer.builder("service.execution.duration")
                    .tag("service", className)
                    .tag("method", methodName)
                    .tag("type", serviceType)
                    .tag("status", "success")
                    .register(meterRegistry));

            return result;
        } catch (Exception e) {
            sample.stop(Timer.builder("service.execution.duration")
                    .tag("service", className)
                    .tag("method", methodName)
                    .tag("type", serviceType)
                    .tag("status", "error")
                    .register(meterRegistry));

            meterRegistry.counter("service.execution.errors",
                    "service", className,
                    "method", methodName,
                    "type", serviceType,
                    "exception", e.getClass().getSimpleName())
                    .increment();

            throw e;
        }
    }

    // @Query 어노테이션이 붙은 커스텀 쿼리 모니터링
    @Around("@annotation(org.springframework.data.jpa.repository.Query)")
    public Object monitorCustomQueries(ProceedingJoinPoint joinPoint) throws Throwable {
        Timer.Sample sample = Timer.start(meterRegistry);
        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            sample.stop(Timer.builder("database.custom.query.duration")
                    .tag("method", joinPoint.getSignature().getName())
                    .register(meterRegistry));

            if (executionTime > 1000) {
                eventPublisher.publishEvent(new SlowQueryEvent(
                    joinPoint.getSignature().getName(),
                    executionTime,
                    "Custom Query"
                ));
            }

            return result;
        } catch (Exception e) {
            meterRegistry.counter("database.custom.query.errors",
                    "method", joinPoint.getSignature().getName(),
                    "exception", e.getClass().getSimpleName())
                    .increment();
            throw e;
        }
    }
}
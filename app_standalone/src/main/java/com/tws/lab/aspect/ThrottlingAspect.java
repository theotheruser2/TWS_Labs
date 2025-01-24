package com.tws.lab.aspect;

import com.tws.lab.service.ThrottlingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ThrottlingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ThrottlingAspect.class);
    private final ThrottlingService throttlingService;

    public ThrottlingAspect(ThrottlingService throttlingService) {
        this.throttlingService = throttlingService;
    }

    @Around("@annotation(com.tws.lab.annotation.Throttled)")
    public Object throttleExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Throttling aspect intercepted call to method: {}", methodName);
        
        try {
            throttlingService.acquirePermit();
            logger.info("Executing throttled method: {}", methodName);
            return joinPoint.proceed();
        } finally {
            throttlingService.releasePermit();
            logger.info("Completed execution of throttled method: {}", methodName);
        }
    }
} 
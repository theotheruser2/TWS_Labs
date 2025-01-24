package com.tws.lab.service;

import com.tws.lab.exception.ThrottlingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ThrottlingService {
    private static final Logger logger = LoggerFactory.getLogger(ThrottlingService.class);
    private final int maxConcurrentRequests;
    private final AtomicInteger currentRequests;
    private final ReentrantLock lock;

    public ThrottlingService(@Value("${app.throttling.limit:3}") int maxConcurrentRequests) {
        this.maxConcurrentRequests = maxConcurrentRequests;
        this.currentRequests = new AtomicInteger(0);
        this.lock = new ReentrantLock(true); 
        logger.info("Initialized ThrottlingService with {} max concurrent requests", maxConcurrentRequests);
    }

    public void acquirePermit() {
        lock.lock();
        try {
            int current = currentRequests.get();
            logger.info("Attempting to acquire permit. Current requests: {}/{}", current, maxConcurrentRequests);
            
            if (current >= maxConcurrentRequests) {
                logger.warn("Request throttled! Max concurrent requests reached: {}/{}", current, maxConcurrentRequests);
                throw new ThrottlingException("Request limit exceeded. Please try again later.");
            }
            
            currentRequests.incrementAndGet();
            logger.info("Permit acquired. Current requests: {}/{}", currentRequests.get(), maxConcurrentRequests);
        } finally {
            lock.unlock();
        }
    }

    public void releasePermit() {
        lock.lock();
        try {
            int current = currentRequests.get();
            if (current > 0) {
                currentRequests.decrementAndGet();
            }
            logger.info("Permit released. Current requests: {}/{}", currentRequests.get(), maxConcurrentRequests);
        } finally {
            lock.unlock();
        }
    }
} 
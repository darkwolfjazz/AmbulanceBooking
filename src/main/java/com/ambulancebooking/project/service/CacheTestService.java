package com.ambulancebooking.project.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheTestService {
    @Cacheable(value = "testCache", key = "#id")
    public String getCachedValue(Long id) {
        // Simulate delay
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
        return "Value for ID: " + id;
    }



}

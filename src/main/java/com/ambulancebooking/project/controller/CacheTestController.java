package com.ambulancebooking.project.controller;

import com.ambulancebooking.project.service.CacheTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheTestController {

    @Autowired
    private CacheTestService cacheTestService;

    @GetMapping("/cacheTest/{id}")
    public String cacheTest(@PathVariable Long id) {
        return cacheTestService.getCachedValue(id);
    }


}

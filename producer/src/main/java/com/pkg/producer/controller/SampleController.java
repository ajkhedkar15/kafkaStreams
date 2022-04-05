package com.pkg.producer.controller;

import com.pkg.producer.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @GetMapping(value = "/sample/{data}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sample(@PathVariable final String data) {
        final String response = sampleService.publishData(data);
        return ResponseEntity.ok(response);
    }
}

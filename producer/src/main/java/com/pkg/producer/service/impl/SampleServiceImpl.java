package com.pkg.producer.service.impl;

import com.pkg.producer.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SampleServiceImpl implements SampleService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public String publishData(String data) {
        String uniqueId = UUID.randomUUID().toString();
        kafkaTemplate.send("data-topic", uniqueId, data);
        return "Data published successfully!";
    }
}

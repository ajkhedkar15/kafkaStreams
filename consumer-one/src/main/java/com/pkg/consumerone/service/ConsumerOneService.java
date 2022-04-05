package com.pkg.consumerone.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class ConsumerOneService {

    public void buildPipeline() {
        log.info("Consumer one Processes started");
        // Configure the Streams application.
        final Properties streamsConfiguration = getStreamsConfiguration();
        // Define the processing topology of the Streams application.
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> dataValues = builder.stream("count-in-topic",
                Consumed.with(Serdes.String(), Serdes.String()));
        //KStream<uniqueDocId_uniquePageId, ExtractedDataJson>
        final KStream<String, Integer> countStream = dataValues.mapValues(String::length);
        countStream.to("count-out-topic", Produced.with(Serdes.String(), Serdes.Integer()));
        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.start();
        log.info("Consumer one Processes completed");
    }

    /**
     * Configure the Streams application.
     * @return Properties getStreamsConfiguration
     */
    static Properties getStreamsConfiguration() {
        final Properties streamsConfiguration = new Properties();
        // Give the Streams application a unique name.  The name must be unique in the Kafka cluster
        // against which the application is run.
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "consumer-one-service");
        // Where to find Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // Specify default (de)serializers for record keys and for record values.
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return streamsConfiguration;
    }
}

package com.pkg.functions.service;

import com.pkg.functions.dto.FunctionDto;
import com.pkg.functions.joiner.DataCountJoiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class FunctionService {

    public void buildPipeline() {
        log.info("function Processes started");
        // Configure the Streams application.
        final Properties streamsConfiguration = getStreamsConfiguration();
        // Define the processing topology of the Streams application.
        final StreamsBuilder builder = new StreamsBuilder();
        //KTable<uniqueDocId_uniquePageId, byte[]>
        final KTable<String, String> dataTable = builder.table("data-topic",
                Consumed.with(Serdes.String(), Serdes.String()),
                Materialized.as("data"));
        dataTable.toStream().to("count-in-topic", Produced.with(Serdes.String(), Serdes.String()));

        final KTable<String, Integer> countTable = builder.table("count-out-topic",
                Consumed.with(Serdes.String(), Serdes.Integer()),
                Materialized.as("count"));

        countTable.toStream().foreach((key, value) -> System.out.println(key+ " ===== "+ value));
        DataCountJoiner joiner = new DataCountJoiner();
        final KTable<String, FunctionDto> joinedTable = dataTable.join(countTable, joiner);
        joinedTable.toStream().foreach((key, value) -> System.out.println(key+ " ===== "+ value.getData()+ " === "+ value.getCount()));
        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.start();
        log.info("function Processes completed");
    }

    /**
     * Configure the Streams application.
     * @return Properties getStreamsConfiguration
     */
    static Properties getStreamsConfiguration() {
        final Properties streamsConfiguration = new Properties();
        // Give the Streams application a unique name.  The name must be unique in the Kafka cluster
        // against which the application is run.
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "functions-service");
        // Where to find Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // Specify default (de)serializers for record keys and for record values.
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return streamsConfiguration;
    }

}

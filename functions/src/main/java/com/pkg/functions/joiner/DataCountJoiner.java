package com.pkg.functions.joiner;

import com.pkg.functions.dto.FunctionDto;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class DataCountJoiner implements ValueJoiner<String, Integer, FunctionDto> {
    @Override
    public FunctionDto apply(String value1, Integer value2) {
        return FunctionDto.builder().data(value1).count(value2).build();
    }
}

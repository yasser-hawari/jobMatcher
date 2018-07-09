package com.sj.jobMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class TestUtilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtilities.class);

    public static ObjectMapper objectMapper ;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String toJson(Object worker) throws JsonProcessingException {
        return objectMapper.writeValueAsString(worker);
    }

    public static <T> T toObject(String json, Class<T> valueType) throws IOException {
        return objectMapper.readValue(json, valueType);
    }

    public static <T> T toObjectFromClasspath(String classPathLocation, Class<T> valueType) throws IOException {
        try (InputStream inputStream = TestUtilities.class.getResourceAsStream(classPathLocation)) {
            return objectMapper.readValue(inputStream, valueType);
        }
    }

    public static <T> T toObjectFromClasspath(String classPathLocation, TypeReference valueTypeRef) throws IOException {
        try (InputStream inputStream = TestUtilities.class.getResourceAsStream(classPathLocation)) {
            return objectMapper.readValue(inputStream, valueTypeRef);
        }
    }


}

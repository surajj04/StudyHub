package com.preportal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.preportal.model.Replies;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class RepliesConverter implements AttributeConverter<List<Replies>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Replies> replies) {
        try {
            return objectMapper.writeValueAsString(replies);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert list of replies to JSON string.", e);
        }
    }

    @Override
    public List<Replies> convertToEntityAttribute(String json) {
        try {
            if (json == null || json.isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Replies.class));
        } catch (IOException e) {
            throw new RuntimeException("Could not convert JSON string to list of replies.", e);
        }
    }
}

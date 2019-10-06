package com.creditsuisse.logingestion.service;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong()),
                TimeZone.getDefault().toZoneId());
//        Instant.ofEpochMilli() new LocalDateTime. (json.getAsJsonPrimitive().getAsString());
    }
}

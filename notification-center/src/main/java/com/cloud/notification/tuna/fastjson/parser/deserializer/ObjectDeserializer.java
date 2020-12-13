package com.cloud.notification.tuna.fastjson.parser.deserializer;

import com.cloud.notification.tuna.fastjson.parser.DefaultJSONParser;

import java.lang.reflect.Type;

public interface ObjectDeserializer {
    <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName);
    
    int getFastMatchToken();
}

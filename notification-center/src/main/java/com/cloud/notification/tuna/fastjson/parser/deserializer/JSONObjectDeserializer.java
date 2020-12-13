package com.cloud.notification.tuna.fastjson.parser.deserializer;

import com.cloud.notification.tuna.fastjson.parser.DefaultJSONParser;
import com.cloud.notification.tuna.fastjson.parser.JSONToken;

import java.lang.reflect.Type;

public class JSONObjectDeserializer implements ObjectDeserializer {
    public final static JSONObjectDeserializer instance = new JSONObjectDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        return (T) parser.parseObject();
    }

    public int getFastMatchToken() {
        return JSONToken.LBRACE;
    }
}

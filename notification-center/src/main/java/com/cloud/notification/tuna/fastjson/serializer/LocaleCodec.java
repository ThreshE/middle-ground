package com.cloud.notification.tuna.fastjson.serializer;

import com.cloud.notification.tuna.fastjson.parser.DefaultJSONParser;
import com.cloud.notification.tuna.fastjson.parser.JSONToken;
import com.cloud.notification.tuna.fastjson.parser.deserializer.ObjectDeserializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Locale;

public class LocaleCodec implements ObjectSerializer, ObjectDeserializer {

    public final static LocaleCodec instance = new LocaleCodec();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.writeNull();
            return;
        }

        Locale locale = (Locale) object;
        serializer.write(locale.toString());
    }
    
    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        String text = (String) parser.parse();
        
        if (text == null) {
            return null;
        }
        
        String[] items = text.split("_");
        
        if (items.length == 1) {
            return (T) new Locale(items[0]);
        }
        
        if (items.length == 2) {
            return (T) new Locale(items[0], items[1]);
        }
        
        return (T) new Locale(items[0], items[1], items[2]);
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_STRING;
    }

}

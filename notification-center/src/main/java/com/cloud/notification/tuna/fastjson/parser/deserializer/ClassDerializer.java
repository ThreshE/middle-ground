package com.cloud.notification.tuna.fastjson.parser.deserializer;

import com.cloud.notification.tuna.fastjson.JSONException;
import com.cloud.notification.tuna.fastjson.parser.DefaultJSONParser;
import com.cloud.notification.tuna.fastjson.parser.JSONLexer;
import com.cloud.notification.tuna.fastjson.parser.JSONToken;
import com.cloud.notification.tuna.fastjson.util.TypeUtils;

import java.lang.reflect.Type;

public class ClassDerializer implements ObjectDeserializer {

    public final static ClassDerializer instance = new ClassDerializer();

    public ClassDerializer(){
    }

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        JSONLexer lexer = parser.getLexer();
        
        if (lexer.token() == JSONToken.NULL) {
            lexer.nextToken();
            return null;
        }
        
        if (lexer.token() != JSONToken.LITERAL_STRING) {
            throw new JSONException("expect className");
        }
        String className = lexer.stringVal();
        lexer.nextToken(JSONToken.COMMA);

        return (T) TypeUtils.loadClass(className);
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_STRING;
    }

}

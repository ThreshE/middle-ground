package com.cloud.notification.tuna.fastjson.parser.deserializer;

import java.lang.reflect.Type;

/**
 *
 * @since 1.1.34
 */
public interface ExtraTypeProvider extends ParseProcess {

    Type getExtraType(Object object, String key);
}

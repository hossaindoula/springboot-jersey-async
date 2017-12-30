package org.doula.async.utils;

import java.util.*;

/**
 * Mohammed Hossain Doula
 *
 * @hossaindoula | @itconquest
 * <p>
 * http://hossaindoula.com
 * <p>
 * https://github.com/hossaindoula
 */
public class ImmutableMap<K, V> extends HashMap<K, V> {

    public static <K, V> Map<K, V> of(K key, V value){
        return Collections.unmodifiableMap(new HashMap<K, V>(){{
            put(key, value);
        }});
    }

    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2){
        return Collections.unmodifiableMap(new HashMap<K, V>(){{
            put(key1, value1);
            put(key2, value2);
        }});
    }

    public static Map<Object, Object> of(Object key1, Object value1, Object key2, Object value2, Object key3, Object value3){
        return Collections.unmodifiableMap(new HashMap<Object, Object>(){{
            put(key1, value1);
            put(key2, value2);
            put(key3, value3);
        }});
    }

    public static Map<Object, Object> of(Object key1, Object value1, Object key2, Object value2, Object key3, Object value3,
                               Object key4, Object value4){
        return Collections.unmodifiableMap(new HashMap<Object, Object>(){{
            put(key1, value1);
            put(key2, value2);
            put(key3, value3);
            put(key4, value4);
        }});
    }

    public static Map<Object, Object> of(Object key1, Object value1, Object key2, Object value2, Object key3, Object value3,
                               Object key4, Object value4, Object key5, Object value5){
        return Collections.unmodifiableMap(new HashMap<Object, Object>(){{
            put(key1, value1);
            put(key2, value2);
            put(key3, value3);
            put(key4, value4);
            put(key5, value5);
        }});
    }
}

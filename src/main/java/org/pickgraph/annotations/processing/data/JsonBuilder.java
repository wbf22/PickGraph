package org.pickgraph.annotations.processing.data;

import org.pickgraph.annotations.Schema.SchemaDescription;
import org.pickgraph.exception.PickGraphException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonBuilder {
    private static final String JAVA = "java.";

    public static ObjectField buildField(Field field) {

        Class<?> type = field.getType();
        if (isJavaType(type)) {
            return resolveJavaField(type, field);
        }
        else {
            return resolveNonBasicField(field);
        }
    }

    public static ObjectField resolveNonBasicField(Field field) {

        Class<?> type = field.getType();
        Object instance = buildDefaultForNonBasicClass(type);
        return new ObjectField(type, instance, getDescription(field));
    }

    private static ObjectField resolveJavaField(Class<?> type, Field field) {

        Object defaultObject = buildDefaultForJavaClass(field.getGenericType());

        return new ObjectField(
                type,
                defaultObject,
                getDescription(field)
        );
    }

    public static Object buildDefaultForNonBasicClass(Class<?> type) {

        Object instance;
        try {
            instance = type.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new PickGraphException("Missing default constructor for " + type.getName() + "\n" + e);
        }

        for (Field f : type.getDeclaredFields()) {
            boolean isPrivate = !Modifier.isPublic(f.getModifiers());
            if (isPrivate)
                ReflectionUtils.makeAccessible(f);
            Object value = (isJavaType(f.getType()))? buildDefaultForJavaClass(f.getGenericType()) : buildDefaultForNonBasicClass(f.getType());
            ReflectionUtils.setField(f, instance, value);
            if (isPrivate)
                f.setAccessible(false);
        }

        return instance;
    }

    public static Object buildDefaultForJavaClass(Type generic) {
        Type screened = (generic instanceof ParameterizedType parameterizedType)? parameterizedType.getRawType() : generic;
        Class<?> type = (Class<?>) screened;
        Object defaultObject;
        if (type == String.class) {
            defaultObject = "";
        }
        else if (type == Integer.class || type == int.class) {
            defaultObject = Integer.parseInt("0");
        }
        else if (type == Long.class || type == long.class) {
            defaultObject = Long.parseLong("0");
        }
        else if (type == Double.class || type == double.class) {
            defaultObject = Double.parseDouble("0.0");
        }
        else if (type == Float.class || type == float.class) {
            defaultObject = Float.parseFloat("0.0");
        }
        else if (type == Short.class || type == short.class) {
            defaultObject = Short.parseShort("0");
        }
        else if (type == Byte.class || type == byte.class) {
            defaultObject = Byte.parseByte("0");
        }
        else if (type == Character.class || type == char.class) {
            defaultObject = "a";
        }
        else if (type == BigDecimal.class) {
            defaultObject = BigDecimal.valueOf( Long.parseLong("0") );
        }
        else if (type == Boolean.class || type == boolean.class) {
            defaultObject = false;
        }
        else if (Map.class.isAssignableFrom(type)) {
            Class<?> keyType = null;
            if (generic instanceof ParameterizedType) {
                keyType = (Class<?>) ((ParameterizedType) generic).getActualTypeArguments()[0];
            }
            else {
                keyType = Object.class;
            }

            Class<?> valueType = null;
            if (generic instanceof ParameterizedType) {
                valueType = (Class<?>) ((ParameterizedType) generic).getActualTypeArguments()[1];
            }
            else {
                valueType = Object.class;
            }

            Object key = (isJavaType(keyType))? buildDefaultForJavaClass(keyType) : buildDefaultForNonBasicClass(keyType);
            Object value = (isJavaType(valueType))? buildDefaultForJavaClass(valueType) : buildDefaultForNonBasicClass(valueType);

            defaultObject = Map.of(key, value);
        }
        else if (Collection.class.isAssignableFrom(type)) {
            Class<?> listType = null;
            if (generic instanceof ParameterizedType) {
                listType = (Class<?>) ((ParameterizedType) generic).getActualTypeArguments()[0];
            }
            else {
                listType = Object.class;
            }
            Object instance = (isJavaType(listType))? buildDefaultForJavaClass(listType) : buildDefaultForNonBasicClass(listType);
            defaultObject = List.of(instance);
        }
        else if (Date.class.isAssignableFrom(type)) {
            try {
                defaultObject = type.getMethod("now").invoke(null);
            } catch (ReflectiveOperationException e) {
                defaultObject = LocalDate.now();
            }
        }
        else if (type == Object.class) {
            defaultObject = new Object();
        }
        else {
            throw new PickGraphException("Unsupported java type: " + type.getName());
        }

        return defaultObject;
    }

    private static String getDescription(Field field) {
        if (field.isAnnotationPresent(SchemaDescription.class)) {
            return field.getAnnotation(SchemaDescription.class).value();
        }
        return field.getType().getSimpleName();
    }

    public static boolean isJavaType(Class<?> type) {
        return isNumericClass(type) || type.getName().startsWith(JAVA);
    }

    public static boolean isNumericClass(Class<?> type) {
        return Number.class.isAssignableFrom(type) || isPrimitiveNumericClass(type);
    }

    public static boolean isPrimitiveNumericClass(Class<?> type) {
        return type == int.class || type == long.class || type == double.class
                || type == float.class || type == short.class || type == byte.class;
    }

}

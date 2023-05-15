package com.freedommuskrats.annotations.processing.data;

import com.freedommuskrats.annotations.Schema.SchemaDescription;
import com.freedommuskrats.exception.PickGraphException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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
        Object instance = resolveNonBasicClass(type);
        return new ObjectField(type, instance, getDescription(field));
    }

    public static Object resolveNonBasicClass(Class<?> type) {

        Object instance;
        try {
            instance = type.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new PickGraphException("Missing default constructor for " + type.getName() + "\n" + e);
        }

        for (Field f : type.getDeclaredFields()) {
            boolean isPrivate = Modifier.isPrivate(f.getModifiers());
            if (isPrivate)
                ReflectionUtils.makeAccessible(f);
            Object value = (isJavaType(f.getType()))? resolveJavaClass(f.getGenericType()) : resolveNonBasicClass(f.getType());
            ReflectionUtils.setField(f, instance, value);
            if (isPrivate)
                f.setAccessible(false);
        }

        return instance;
    }

    private static void callSet(ObjectField field, Object instance, Method method, Class<?> parentType) {
        try {
            Class<?> parameterType = method.getParameterTypes()[0];

            callMethodConvertingType(method, instance, field.getDefaultObject(), parameterType);

        } catch (ReflectiveOperationException | IllegalArgumentException e) {
            throw new PickGraphException("Failed to invoke setter method: '" + method +
                    "' creating schema for class: " + parentType.getName() + "() using object "
                    + field.getDefaultObject().getClass().getName() + "\n"
                    + "Probably because fields and setters are not in the same order" + e);
        }

    }

    private static void callMethodConvertingType(Method method, Object instance, Object providedParm, Class<?> paramType) throws InvocationTargetException, IllegalAccessException {
        if (Integer.class.isAssignableFrom(paramType)) {
            method.invoke(instance, Integer.parseInt(providedParm.toString()) );
        }
        else if (Long.class.isAssignableFrom(paramType)) {
            method.invoke(instance, Long.parseLong(providedParm.toString()) );
        }
        else if (Double.class.isAssignableFrom(paramType)) {
            method.invoke(instance, Double.parseDouble(providedParm.toString()) );
        }
        else if (Float.class.isAssignableFrom(paramType)) {
            method.invoke(instance, Float.parseFloat(providedParm.toString()) );
        }
        else if (Short.class.isAssignableFrom(paramType)) {
            method.invoke(instance, Short.parseShort(providedParm.toString()) );
        }
        else if (Byte.class.isAssignableFrom(paramType)) {
            method.invoke(instance, Byte.parseByte(providedParm.toString()) );
        }
        else if (Character.class.isAssignableFrom(paramType)) {
            method.invoke(instance, providedParm.toString().charAt(0));
        }
        else if (paramType == BigDecimal.class) {
            method.invoke(instance, BigDecimal.valueOf( Long.parseLong(providedParm.toString()) ));
        }
        else if (Boolean.class.isAssignableFrom(paramType)) {
            method.invoke(instance, ((boolean) providedParm) );
        }
        else {
            method.invoke(instance, providedParm);
        }


    }

    private static boolean isSetterMethod(Method method) {
        String methodName = method.getName();
        return methodName.startsWith("set") && method.getParameterCount() == 1;
    }

    private static ObjectField resolveJavaField(Class<?> type, Field field) {

        Object defaultObject = resolveJavaClass(field.getGenericType());

        return new ObjectField(
                type,
                defaultObject,
                getDescription(field)
        );
    }

    public static Object resolveJavaClass(Type generic) {
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

            Object key = (isJavaType(keyType))? resolveJavaClass(keyType) : resolveNonBasicClass(keyType);
            Object value = (isJavaType(valueType))? resolveJavaClass(valueType) : resolveNonBasicClass(valueType);

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
            Object instance = (isJavaType(listType))? resolveJavaClass(listType) : resolveNonBasicClass(listType);
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


    private static boolean isEquivalentWrapper(Class<?> type, Class<?> primitiveType) {
        return (type == Integer.class && primitiveType == int.class)
                || (type == Long.class && primitiveType == long.class)
                || (type == Double.class && primitiveType == double.class)
                || (type == Float.class && primitiveType == float.class)
                || (type == Short.class && primitiveType == short.class)
                || (type == Byte.class && primitiveType == byte.class)
                || (type == Boolean.class && primitiveType == boolean.class)
                || (type == Character.class && primitiveType == char.class);
    }

    public static boolean isNumericClass(Class<?> type) {
        return Number.class.isAssignableFrom(type) || isPrimitiveNumericClass(type);
    }

    public static boolean isPrimitiveNumericClass(Class<?> type) {
        return type == int.class || type == long.class || type == double.class
                || type == float.class || type == short.class || type == byte.class;
    }

    public static boolean isDecimalType(Class<?> type) {
        return type == double.class || type == Double.class || type == float.class
                || type == Float.class;
    }
}

package com.os.framework.os_core_api.CPU.Utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Utility class for converting between arrays and collections of various types.
 * This class provides methods to transform arrays or lists into the specified collection type.
 */
@Slf4j
public class Converter {

    /**
     * Converts an array or a list to a collection of the specified target type.
     *
     * @param input      The input array or list to be converted.
     * @param targetType The target type that defines the type of elements in the resulting collection.
     * @param <T>        The type of elements in the target collection (e.g., Integer, Long, Double).
     * @return The converted collection (either an array or a list) in the specified target type.
     * @throws IllegalArgumentException If the input type is neither an array nor a list, or if the element types are incompatible.
     */
    public static <T> Object transformToCollection(Object input, Class<T> targetType) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

        log.debug("Transforming input to collection. Input type: {}, Target type: {}", input.getClass().getName(), targetType.getName());

        if (input.getClass().isArray()) {
            return convertArray(input, targetType);
        } else if (input instanceof List<?>) {
            return convertList((List<?>) input, targetType);
        } else {
            throw new IllegalArgumentException("Invalid input type. Expected array or List.");
        }
    }

    /**
     * Converts an array to the specified target type.
     *
     * @param input      The input array to be converted.
     * @param targetType The target type of the resulting array.
     * @param <T>        The type of elements in the target array.
     * @return The converted array of the specified type.
     * @throws IllegalArgumentException If the target type is unsupported.
     */
    private static <T> Object convertArray(Object input, Class<T> targetType) {
        log.debug("Converting array of type {} to target type {}", input.getClass().getName(), targetType.getName());

        if (targetType == Integer.class) {
            return convertToIntArray(input);
        } else if (targetType == Long.class) {
            return convertToLongArray(input);
        } else if (targetType == Double.class) {
            return convertToDoubleArray(input);
        } else {
            throw new IllegalArgumentException("Unsupported target type: " + targetType.getName());
        }
    }

    /**
     * Converts a list to the specified target type.
     *
     * @param input      The input list to be converted.
     * @param targetType The target type of the resulting list.
     * @param <T>        The type of elements in the target list.
     * @return The converted list of the specified type.
     * @throws IllegalArgumentException If the target type is unsupported or elements in the list are incompatible.
     */
    private static <T> Object convertList(List<?> input, Class<T> targetType) {
        log.debug("Converting list of type {} to target type {}", input.getClass().getName(), targetType.getName());

        if (targetType == Integer.class) {
            return convertListToInt(input);
        } else if (targetType == Long.class) {
            return convertListToLong(input);
        } else if (targetType == Double.class) {
            return convertListToDouble(input);
        } else {
            throw new IllegalArgumentException("Unsupported target type: " + targetType.getName());
        }
    }

    /**
     * Converts an array of Object to an int array.
     *
     * @param input The input array to be converted.
     * @return The converted int array.
     */
    private static int[] convertToIntArray(Object input) {
        log.debug("Converting input to int array");
        return (int[]) input;
    }

    /**
     * Converts an array of Object to a long array.
     *
     * @param input The input array to be converted.
     * @return The converted long array.
     */
    private static long[] convertToLongArray(Object input) {
        log.debug("Converting input to long array");
        return (long[]) input;
    }

    /**
     * Converts an array of Object to a double array.
     *
     * @param input The input array to be converted.
     * @return The converted double array.
     */
    private static double[] convertToDoubleArray(Object input) {
        log.debug("Converting input to double array");
        return (double[]) input;
    }

    /**
     * Converts a list of Objects to a list of Long values.
     *
     * @param input The input list to be converted.
     * @return The converted list of Long values.
     */
    private static List<Long> convertListToLong(List<?> input) {
        log.debug("Converting list to list of Long values");
        List<Long> list = new java.util.ArrayList<>();
        for (Object item : input) {
            if (item instanceof Long) {
                list.add((Long) item);
            } else if (item instanceof Integer) {
                list.add(((Integer) item).longValue());
            } else {
                throw new IllegalArgumentException("List elements must be of type Long or Integer.");
            }
        }
        return list;
    }

    /**
     * Converts a list of Objects to a list of Integer values.
     *
     * @param input The input list to be converted.
     * @return The converted list of Integer values.
     */
    private static List<Integer> convertListToInt(List<?> input) {
        log.debug("Converting list to list of Integer values");
        List<Integer> list = new java.util.ArrayList<>();
        for (Object item : input) {
            if (item instanceof Integer) {
                list.add((Integer) item);
            } else {
                throw new IllegalArgumentException("List elements must be of type Integer.");
            }
        }
        return list;
    }

    /**
     * Converts a list of Objects to a list of Double values.
     *
     * @param input The input list to be converted.
     * @return The converted list of Double values.
     */
    private static List<Double> convertListToDouble(List<?> input) {
        log.debug("Converting list to list of Double values");
        List<Double> list = new java.util.ArrayList<>();
        for (Object item : input) {
            if (item instanceof Double) {
                list.add((Double) item);
            } else if (item instanceof Integer) {
                list.add(((Integer) item).doubleValue());
            } else {
                throw new IllegalArgumentException("List elements must be of type Double or Integer.");
            }
        }
        return list;
    }
}

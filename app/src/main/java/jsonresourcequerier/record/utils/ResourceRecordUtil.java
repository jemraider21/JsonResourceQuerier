package jsonresourcequerier.record.utils;

import java.lang.reflect.Field;
import java.util.Optional;

import jsonresourcequerier.record.ResourceRecord;
import jsonresourcequerier.record.fields.RecordField;

public class ResourceRecordUtil {
    private ResourceRecordUtil() {
    }

    /**
     * Get the value of a field by its name.
     * 
     * @TODO Move this to the implementation classes to avoid setting the field
     *       accessible.
     * @param record    The record to get the field from (concrete implementation of
     *                  ResourceRecord).
     * @param fieldName The field to get the value of.
     * @return The value of the field, or empty if the field does not exist.
     */
    public static Optional<Object> getFieldByName(ResourceRecord record, RecordField fieldName) {
        return getFieldByName(record, fieldName.getName());
    }

    /**
     * Get the value of a field by its name.
     * 
     * @TODO Move this to the implementation classes to avoid setting the field
     *       accessible.
     * @param record    The record to get the field from (concrete implementation
     *                  of ResourceRecord).
     * @param fieldName The name of the field to get.
     * @return The value of the field, or empty if the field does not exist.
     */
    public static Optional<Object> getFieldByName(ResourceRecord record, String fieldName) {
        try {
            Field field = record.getClass().getDeclaredField(fieldName);
            boolean originalAccessible = field.canAccess(record);
            if (!originalAccessible) {
                field.setAccessible(true);
            }
            Optional<Object> result = Optional.ofNullable(field.get(record));
            if (!originalAccessible) {
                field.setAccessible(false);
            }
            return result;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return Optional.empty();
        }
    }
}

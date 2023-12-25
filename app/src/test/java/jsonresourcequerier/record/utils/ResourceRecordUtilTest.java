package jsonresourcequerier.record.utils;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jsonresourcequerier.record.ResourceRecord;
import jsonresourcequerier.record.fields.RecordField;
import lombok.AllArgsConstructor;
import lombok.Getter;

class ResourceRecordUtilTest {

    @Test
    void testGetFieldByName() {
        String field1 = "Tester bester";
        String field2 = "Wester bester";
        String field3 = "Lester bester";
        TestRecord testRecord = new TestRecord(field1, field2, field3);

        Map<RecordField, String> testRecordFieldToValueMap = Map.of(
                TestRecordField.FIELD1, field1,
                TestRecordField.FIELD2, field2,
                TestRecordField.FIELD3, field3);

        for (Map.Entry<RecordField, String> entry : testRecordFieldToValueMap.entrySet()) {
            RecordField field = entry.getKey();
            String expectedValue = entry.getValue();

            Optional<Object> actualValue = ResourceRecordUtil.getFieldByName(testRecord, field);
            Assertions.assertTrue(actualValue.isPresent());
            Object resultValue = actualValue.get();
            Assertions.assertTrue(resultValue instanceof String);
            Assertions.assertEquals(expectedValue, resultValue);
        }
    }

    @Test
    void testGetFieldByNameFailFieldDoesNotExist() {
        ResourceRecord testRecord = new TestRecord("field1", "field2", "field3");
        Optional<Object> actualValue = ResourceRecordUtil.getFieldByName(testRecord, "field4");
        Assertions.assertFalse(actualValue.isPresent());
    }

    @Getter
    @AllArgsConstructor
    private class TestRecord implements ResourceRecord {
        private String field1;
        private String field2;
        private String field3;

        @Override
        /**
         * Not implemented.
         */
        public Optional<Object> getFieldByName(String fieldName) {
            return Optional.empty();
        }
    }

    @AllArgsConstructor
    @Getter
    private enum TestRecordField implements RecordField {
        FIELD1("field1"),
        FIELD2("field2"),
        FIELD3("field3");

        private String name;
    }
}

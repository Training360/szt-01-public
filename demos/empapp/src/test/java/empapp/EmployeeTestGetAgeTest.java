package empapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

//@Tag("entity")
@EntityTest
public class EmployeeTestGetAgeTest {

    Employee employee;

    @Nested
    class WithAge1970 {
        @BeforeEach
        void init() {
            employee = new Employee("John Doe", 1970);
        }

        @Test
        void testGetAge1970() {
        }

        @Test
        void testGetAge1970Jane() {
        }
    }

    @Nested
    class WithAge1980 {
        @BeforeEach
        void init() {
            employee = new Employee("John Doe", 1980);
        }

        @Test
        void testGetAge1970() {
        }

        @Test
        void testGetAge1970Jane() {
        }
    }
}

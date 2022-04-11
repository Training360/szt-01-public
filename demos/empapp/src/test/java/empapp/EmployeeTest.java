package empapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@Tag("entity")
@EntityTest
public class EmployeeTest implements PrintNameCapable {

    Employee employee;

    @BeforeEach
    void initEmployee() {
        employee = new Employee("John Doe", 1970);
    }

    @Test
//    @Order(1)
    @DisplayName("Test age calculation with positive number")
    void testGetAge() {
//        // Given
//        Employee employee = new Employee("John Doe", 1970);
//        // When
//        int age = employee.getAge(2000);
//        // Then
//        assertEquals(30, age);

//        assertEquals(30,
//                new Employee("John Doe", 1970).getAge(2000));

//        assertEquals(30,
//                employee.getAge(2000));

        assertNotNull(employee);

        Employee expected = new Employee("John Doe", 1970);
        assertEquals(employee, expected);
        assertNotSame(employee, expected);

        assertTrue(30 == employee.getAge(2000));

//        fail();

        assertEquals(1.0, 1.0, 0.005);

        assertAll(
                () -> assertEquals("John Doe", employee.getName()),
                () -> assertEquals(30, employee.getAge(2000))
        );
    }

    @Test
//    @Order(2)
//    @Disabled("Until fix 123 issue")
//    @DisabledOnOs(OS.WINDOWS)
//    void testGetAgeWithZero() {
    void test_Get_Age_With_Zero() {
//        assertEquals(0,
//                new Employee("John Doe", 1970).getAge(1970));

        assertEquals(0,
                employee.getAge(1970));
    }

    @Test
    void testCreateEmployeeWithYearOfBirth1700() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Employee("John Doe", 1700));

        assertEquals("Year: 1700", ex.getMessage());
    }
}

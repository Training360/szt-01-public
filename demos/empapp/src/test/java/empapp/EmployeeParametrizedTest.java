package empapp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeParametrizedTest {

    @ParameterizedTest(name = "Get age, year of birth {0}, age {1}")
    @CsvFileSource(resources = "/ages.csv")
    void testGetAge(int yearOfBirth, int age) {
        Employee employee = new Employee("John Doe", yearOfBirth);
        assertEquals(age, employee.getAge(2000));
    }

}

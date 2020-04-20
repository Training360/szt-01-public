package empapp;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static empapp.EmployeeWithNameMatcher.employeeWithName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceWithHamcrestTest {

    @Test
    void testListEmployees() {
        List<Employee> employees = new EmployeeService().listEmployees();
        assertThat(employees, hasItem(
                hasProperty("name", equalTo("John Doe"))
        ));
    }

    @Test
    void testListEmployeesWithMatcher() {
        List<Employee> employees = new EmployeeService().listEmployees();

        assertThat(employees, hasItem(
                employeeWithName(equalTo("John Doe"))
        ));

        assertThat(employees.get(0), employeeWithName(startsWith("Joh")));
    }
}

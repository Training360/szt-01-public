package empapp;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.Assumptions.assumeThat;

public class EmployeeServiceWithAssertJTest {

    @Test
    void testListEmployees() {
        List<Employee> employees = new EmployeeService().listEmployees();

        Employee employee = employees.get(0);

        assertThat(employee.getName()).isEqualTo("John Doe");
        assertThat(employee.getName())
                .startsWith("John")
                .endsWith("Doe");

        assertThat(employees)
                .hasSize(2)
                .extracting(Employee::getName)
                .contains("John Doe");

        assertThat(employees)
                .as("Contains two elements, John is the first")
                .hasSize(2)
                .extracting(Employee::getName, Employee::getYearOfBirth)
                .contains(tuple("John Doe", 1970));

//         assumeThat(employee.getName()).isEqualTo("xxx");
    }
}

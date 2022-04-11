package empapp;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static empapp.EmployeeAssert.assertThat;

public class EmployeeServiceAssertJExtensionTest {

    @Test
    void testListEmployeesWithCondition() {
        List<Employee> employees = new EmployeeService().listEmployees();
        Employee employee = employees.get(0);

        Condition<Employee> familyNameDoe =
                new Condition<>(e -> e.getName().endsWith("Doe"), "family name is Doe");
        assertThat(employee).has(familyNameDoe);
    }

    @Test
    void testListEmployeesWithExtension() {
        List<Employee> employees = new EmployeeService().listEmployees();
        Employee employee = employees.get(0);

        assertThat(employee).hasName("John Doe");
    }
}

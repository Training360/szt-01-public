package empapp;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(SoftAssertionsExtension.class)
public class EmployeeServiceWithAssertJSoftTest {

    @Test
    void testListEmployees(SoftAssertions softly) {
        List<Employee> employees = new EmployeeService().listEmployees();

        Employee employee = employees.get(0);

//        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(employee.getName()).startsWith("xxx");
//        softly.assertThat(employee.getName()).endsWith("yyy");
//        softly.assertAll();

        softly.assertThat(employee.getName()).startsWith("John");
        softly.assertThat(employee.getName()).endsWith("Doe");

    }

}

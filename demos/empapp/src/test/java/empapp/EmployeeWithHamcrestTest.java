package empapp;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeWithHamcrestTest {

    @Test
    void testAge() {
        Employee employee = new Employee("John Doe", 1970);

        //assertEquals(30, employee.getAge(2000));
        assertThat(employee.getAge(2000), equalTo(30));

        assertThat(employee.getName(), startsWith("John"));
        assertThat(employee, anyOf(hasProperty("name", startsWith("John")),
                hasProperty("yearOfBirth", equalTo(1971)))
                );
    }
}

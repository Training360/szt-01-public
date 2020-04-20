package empapp;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
public class EmployeeServiceTest {

    @Test
    void testListEmployeeNames() {
        assertEquals(List.of("John Doe", "Jane Doe"), new EmployeeService().listEmployeeNames());
    }

    @Test
    void testListEmployees() {
       assertEquals(List.of("John Doe", "Jane Doe"), new EmployeeService().listEmployees().stream()
            .map(Employee::getName).collect(Collectors.toList()), "Employee lists are different");
    }

}

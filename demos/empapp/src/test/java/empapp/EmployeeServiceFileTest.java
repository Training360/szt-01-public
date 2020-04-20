package empapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceFileTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteEmployee() throws IOException {
        Employee employee = new Employee("John Doe", 1970);
        Path file = tempDir.resolve("employee.txt");
        System.out.println(file);
        new EmployeeService().writeEmployeeToFile(employee, file);

        String content = Files.readString(file);
        assertEquals("John Doe,1970", content);
    }
}

package empapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class EmployeeService {

    public List<Employee> listEmployees() {
        return List.of(new Employee("John Doe", 1970),
                new Employee("Jane Doe", 1980));
    }

    public List<String> listEmployeeNames() {
        return List.of("John Doe", "Jane Doe");
    }

    public void calculateYearlyReport() {
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException ie) {
            throw new IllegalStateException("Interrupted", ie);
        }
    }

    public void writeEmployeeToFile(Employee employee, Path path) {
        try {
            Files.writeString(path, employee.getName() + "," + employee.getYearOfBirth());
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not write file", ioe);
        }
    }
}

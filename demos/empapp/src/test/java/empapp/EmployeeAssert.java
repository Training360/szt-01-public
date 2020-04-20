package empapp;

import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

public class EmployeeAssert extends AbstractAssert<EmployeeAssert, Employee> {

    public static EmployeeAssert assertThat(Employee employee) {
        return new EmployeeAssert(employee);
    }

    public EmployeeAssert(Employee employee) {
        super(employee, EmployeeAssert.class);
    }

    public EmployeeAssert hasName(String name) {
        if (!Objects.equals(actual.getName(), name)) {
            failWithMessage("Expected name: " + name);
        }
        return this;
    }
}

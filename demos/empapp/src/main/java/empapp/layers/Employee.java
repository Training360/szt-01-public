package empapp.layers;

import java.util.Objects;

public class Employee {

    private String name;

    private int yearOfBirth;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, int yearOfBirth) {
        this.name = name;
        if (yearOfBirth < 1800) {
            throw new IllegalArgumentException("Year: " + yearOfBirth);
        }
        this.yearOfBirth = yearOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return yearOfBirth == employee.yearOfBirth && Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, yearOfBirth);
    }
}

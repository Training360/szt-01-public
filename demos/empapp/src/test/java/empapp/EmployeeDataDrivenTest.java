package empapp;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeDataDrivenTest {

    private int[][] values = {{1970, 0}, {1980, 10}, {2000, 30}};

    @RepeatedTest(value = 3, name = "Get age {currentRepetition} / {totalRepetitions}")
    void testGetAge(RepetitionInfo repetitionInfo) {
        Employee employee = new Employee("John Doe", 1970);

        System.out.println(values[repetitionInfo.getCurrentRepetition() - 1][1] + " - "
        + values[repetitionInfo.getCurrentRepetition() - 1][0]);

        assertEquals(values[repetitionInfo.getCurrentRepetition() - 1][1],
                employee.getAge(values[repetitionInfo.getCurrentRepetition() - 1][0]));
    }
}

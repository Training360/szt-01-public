package empapp;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeDynamicTest {

    @TestFactory
    Stream<DynamicTest> testGetAge() {
        return Stream.of(new Integer[][]{{1970, 30}, {1980, 20}, {1990, 10}})
                .map(pair -> DynamicTest.dynamicTest("Test get age " +
                        pair[0],
                        () -> assertEquals(pair[1], new Employee("John Doe",
                                pair[0]).getAge(2000))
                        ));
    }
}

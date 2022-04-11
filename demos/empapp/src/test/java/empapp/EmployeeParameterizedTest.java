package empapp;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeParameterizedTest {

    @ParameterizedTest(name = "Get age, year of birth {0}, age {1}")
//    @ValueSource(ints = {1970, 1980, 1990})
//    @MethodSource("createAges")
//    @ArgumentsSource(AgeProvider.class)
//    @CsvSource({
//            "1970,30",
//            "1980,20",
//            "1990,10",
//            "2000,0"
//    })
    @CsvFileSource(resources = "/ages.csv")
    void testGetAge(int yearOfBirth, int age) {
        Employee employee = new Employee("John Doe", yearOfBirth);
//        assertEquals(30, employee.getAge(2000));
//        assertTrue(employee.getAge(2000) > 0);
        assertEquals(age, employee.getAge(2000));
    }

//    static Stream<Arguments> createAges() {
//        return Stream.of(
//                Arguments.arguments(1970, 30),
//                Arguments.arguments(1980, 20),
//                Arguments.arguments(1990, 10),
//                Arguments.arguments(2000, 0)
//        );
//    }

//    static class AgeProvider implements ArgumentsProvider {
//        @Override
//        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
//            return Stream.of(
//                    Arguments.arguments(1970, 30),
//                    Arguments.arguments(1980, 20),
//                    Arguments.arguments(1990, 10),
//                    Arguments.arguments(2000, 0)
//            );
//        }
//    }

}

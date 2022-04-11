package locations;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LocationRepeatTest {

    final double[][] locations = {{0, 50, 1}, {23, 43, 0}, {0, 32, 1}, {56, 56, 0}};

    //Tesztesetek ismétlése
    @RepeatedTest(value = 4, name = "{currentRepetition} / {totalRepetitions}")
    void testEquator(RepetitionInfo info) {
        int i = info.getCurrentRepetition() - 1;
        Location location = new Location("X", locations[i][0], locations[i][1]);

        assertEquals(locations[i][2] == 1, location.isOnEquator());
    }

    //Paraméterezett tesztek 1.
    @ParameterizedTest(name = "Latitude = {0}, Longitude = {1}, Expected = {2}")
    @MethodSource("getLocations")
    void testPrimeMeridian(int latitude, int longitude, boolean expected) {
        Location location = new Location("X", latitude, longitude);

        assertEquals(expected, location.isOnPrimeMeridian());
    }

    //Paraméterezett tesztek 2.
    @ParameterizedTest(name = "Latitude = {0}, Longitude = {1}, Expected = {2}")
    @CsvFileSource(resources = "/expected.csv")
    void testPrimeMeridianFromFile(int latitude, int longitude, boolean expected) {
        Location location = new Location("X", latitude, longitude);

        assertEquals(expected, location.isOnPrimeMeridian());
    }

    //Paraméterezett tesztek 1.
    static Stream<Arguments> getLocations() {
        return Stream.of(
                Arguments.arguments(50, 0, true),
                Arguments.arguments(23, 43, false),
                Arguments.arguments(32, 0, true),
                Arguments.arguments(56, 56, false)
        );
    }

    //Dinamikus tesztek
    @DisplayName("Dynamic test cases if Locations are on Equator.")
    @TestFactory
    Stream<DynamicTest> testIsOnEquatorDynamic() {
        Stream<Location> someLocations = Stream.of(
                new Location("X", 0, 19),
                new Location("Y", 0, 20),
                new Location("Z", 0, 21)
        );

        return someLocations
                .map(location -> DynamicTest.dynamicTest(
                        location.getName() + " is on Equator.", () -> assertTrue(location.isOnEquator())));
    }
}

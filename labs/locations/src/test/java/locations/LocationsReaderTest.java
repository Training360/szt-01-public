package locations;

import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SoftAssertionsExtension.class)
class LocationsReaderTest {

    LocationsReader locationsReader;

    @BeforeEach
    void init() {
        locationsReader = new LocationsReader();
    }

    //AssertJ 1.
    @Test
    void testReadLocations() {
        List<Location> locations = locationsReader.readLocations("src/test/resources/locations.csv");
        assertThat(locations)
                .hasSize(5)
                .extracting(Location::getName)
                .contains("Y");
    }

    //AssertJ 2-3.
    @Test
    void testfilterLocationsBeyondArcticCircle() {
        List<Location> originalLocationList = locationsReader.readLocations("src/test/resources/locations.csv");
        List<Location> filteredLocationList = locationsReader.filterLocationsBeyondArcticCircle(originalLocationList);

        assertThat(filteredLocationList)
                .extracting(Location::getName)
                .hasSize(2)
                .contains("A")
                .doesNotContain("X")
                .containsOnly("A", "B");

        assertThat(filteredLocationList)
                .filteredOn(location -> location.getLatitude() == location.getLongitude())
                .extracting(Location::getName, Location::getLatitude)
                .contains(tuple("A", 70.0));
    }

    //AssertJ 4.
    @Test
    void testSoftAssertion(SoftAssertions softly) {
        Location location = new Location("Abc", 0, 0);

        // Fail
//        SoftAssertions softAssertions = new SoftAssertions();
//        softAssertions.assertThat(location.getName()).startsWith("b");
//        softAssertions.assertThat(location.getName()).endsWith("b");
//        softAssertions.assertAll();

        // Fail
//        softly.assertThat(location.getName()).startsWith("b");
//        softly.assertThat(location.getName()).endsWith("b");
    }

    //AssertJ kiterjeszthetőség 1.
    @Test
    void testWithCondition() {
        List<Location> locations = locationsReader.readLocations("src/test/resources/locations.csv");
        Condition<Location> hasZeroCoordinate = new Condition<>(e -> e.isOnEquator() || e.isOnPrimeMeridian(),
                "Location does not have zero coordinate");
        assertThat(locations.get(0)).has(hasZeroCoordinate);
    }

    //AssertJ kiterjeszthetőség 2.
    @Test
    void testLocationsNearArcticCircle() {
        Location goodLocation = new Location("Abc", 66.57, 0);
        LocationAssert.assertThat(goodLocation).isNearTheArcticCircle();

        // Fail
        //Location badLocation = new Location("Abc", 42, 0);
        //LocationAssert.assertThat(badLocation).isNearTheArcticCircle();
    }

    @Test
    void testException() {
        assertThrows(IllegalStateException.class,
                () -> locationsReader.readLocations("src/test/resources/invalid.csv"));
    }
}

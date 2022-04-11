package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@LocationOperationsFeatureTest
class LocationOperatorsTest {

    List<Location> locationList = new ArrayList<>();

    //Assert 4.
    @BeforeEach
    void createLocationParser() {
        locationList.add(new Location("X", 47, 20));
        locationList.add(new Location("Y", -12, 20));
        locationList.add(new Location("Z", 56, 20));
    }

    //Assert 4.
    @Test
    void testNorthLocations() {
        List<String> northLocations = LocationOperators.filterOnNorth(locationList).stream()
                .map(Location::getName)
                .collect(Collectors.toList());

        assertEquals(List.of("X", "Z"), northLocations);
    }
}
package locations;

import static org.hamcrest.MatcherAssert.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

class LocationsReaderHamcrestTest {

    //Hamcrest
    @Test
    void testReadLocationsWithHamcrest() throws IOException {
        List<Location> locations = new LocationsReader().readLocations("src/test/resources/locations.csv");

        assertThat(locations.get(1).getName(), equalTo("Y"));
        assertThat(locations.get(2).getLatitude(), equalTo(32.0));
        assertThat(locations.get(3).getLongitude(), equalTo(70.0));
        assertThat(locations.size(), equalTo(5));
    }

    //Saját Hamcrest matcher implementálása
    @Test
    void testWithOwnHamcrestMatcher() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        assertThat(location, LocationWithZeroCoordinate.zeroCoordinate(equalTo(false)));
    }
}

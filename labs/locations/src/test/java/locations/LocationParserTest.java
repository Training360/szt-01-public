package locations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationParserTest {

    //Bevezetés a JUnit használatába
    @Test
    @DisplayName("Test if Location is correctly parsed from text.")
    void testParse() {
        Location location = new LocationParser().parse("Budapest,47.497912,19.040235");

        assertEquals("Budapest", location.getName());
        assertEquals(47.497912d, location.getLatitude());
        assertEquals(19.040235d, location.getLongitude());
    }
}

package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    LocationParser locationParser;

    @Test
    @DisplayName("Test if Location is correctly created.")
    void testCreate() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        assertEquals("Budapest", location.getName());
        assertEquals(47.497912, location.getLatitude());
        assertEquals(19.040235, location.getLongitude());
    }

    //Tesztesetek életciklusa
    @BeforeEach
    void createLocationParser() {
        locationParser = new LocationParser();
    }

    //Bevezetés a JUnit használatába
    @Test
    @DisplayName("Test if Location is correctly parsed from text.")
    void testParse() {
        Location location = locationParser.parse("Budapest,47.497912,19.040235");

        assertEquals("Budapest", location.getName());
        assertEquals(47.497912d, location.getLatitude());
        assertEquals(19.040235d, location.getLongitude());
    }

    @Test
    @DisplayName("Test if Exception is thrown, when parameter text has two parts.")
    void testParseWithWrongParameterTwoParts() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> locationParser.parse("Budapest47.497912,19.040235"));
        assertEquals("Something is wrong with parameter text.", ex.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown, when double numbers in parameter are wrong.")
    void testParseWithWrongParameterWrongNumbers() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> locationParser.parse("Budapest,latitude=47.497912,longitude=19.040235"));
        assertEquals("Something is wrong with parameter text.", ex.getMessage());
    }

    //Tesztesetek életciklusa
    @Test
    @DisplayName("Test if method returns true when town is on the Equator.")
    void testIsOnEquator() {
        Location location = new Location("Macapá", 0.0, -51.205999);

        assertTrue(location.isOnEquator());
    }

    @Test
    @DisplayName("Test if method returns false when town is not on the Equator.")
    void testIsOnEquatorNotOnEquator() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        assertFalse(location.isOnEquator());
    }

    @Test
    @DisplayName("Test if method returns true when town is on the Prime Meridian.")
    void testIsOnPrimeMeridian() {
        Location location = new Location("Greenwich", 51.2840, 0.0);

        assertTrue(location.isOnPrimeMeridian());
    }

    @Test
    @DisplayName("Test if method returns false when town is not on the Prime Meridian.")
    void testIsOnPrimeMeridianNotOnPrimeMeridian() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        assertFalse(location.isOnPrimeMeridian());
    }

    //Assert 1.
    @Test
    @DisplayName("Test if method returns two different objects when invocated twice.")
    void testDifferentLocationParsers() {
        LocationParser locationParser = new LocationParser();

        assertNotEquals(locationParser, this.locationParser);
        assertNotSame(locationParser, this.locationParser);
    }

    //Assert 2.
    @Test
    @DisplayName("Test if distance is correctly calculated.")
    void testDistance() {
        Location location = locationParser.parse("Budapest,47.49791,19.04023");
        Location anotherLocation = locationParser.parse("Debrecen,47.52997,21.63916");

        assertEquals(195.2d, location.distanceFrom(anotherLocation));
    }

    @Test
    @DisplayName("Test if distance is zero.")
    void testDistanceEquals() {
        Location location = locationParser.parse("Debrecen,47.52997,21.63916");
        Location anotherLocation = locationParser.parse("Debrecen,47.52997,21.63916");

        assertEquals(0d, location.distanceFrom(anotherLocation));
    }

    //Assert 3.
    @Test
    @DisplayName("Test getters in one assert.")
    void testParseAtOnce() {
        Location location = locationParser.parse("Budapest,47.497912,19.040235");

        assertAll(
                () -> assertEquals("Budapest", location.getName()),
                () -> assertEquals(47.497912d, location.getLatitude()),
                () -> assertEquals(19.040235d, location.getLongitude())
        );
    }

    //Assert 5.
    @Test
    @DisplayName("Test equals of locations.")
    void testDifferentLocations() {
        Location location = new Location("X", 0, 0);
        Location location2 = new Location("X", 0, 0);

        assertAll(
                () -> assertEquals(location, location2),
                () -> assertNotSame(location, location2)
        );
    }

    //Kivételkezelés és timeout tesztelése
    @Test
    @DisplayName("Test if Exception is thrown when latitude is too low.")
    void testCreateWithTooLowLatitude() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Location("FakeTown", -91.0, 19.040235));
        assertEquals("Out of this dimension", ex.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown when latitude is too high.")
    void testCreateWithTooHighLatitude() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Location("FakeTown", +91.0, 19.040235));
        assertEquals("Out of this dimension", ex.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown when longitude is too low.")
    void testCreateWithTooLowLongitude() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Location("FakeTown", 19.040235, -181.0));
        assertEquals("Out of this dimension", ex.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown when longitude is too high.")
    void testCreateWithTooHighLongitude() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Location("FakeTown", 19.040235, +181.0));
        assertEquals("Out of this dimension", ex.getMessage());
    }

    @Test
    @DisplayName("Test if setters work")
    void testLocationSetters() {
        Location city = new Location("Budapest", 47.49791d, 19.04023d);
        city.setName("Debrecen");
        city.setLatitude(47.52997d);
        city.setLongitude(21.63916d);

        assertAll(
                () -> assertEquals("Debrecen", city.getName()),
                () -> assertEquals(47.52997d, city.getLatitude()),
                () -> assertEquals(21.63916d, city.getLongitude())
        );
    }
}
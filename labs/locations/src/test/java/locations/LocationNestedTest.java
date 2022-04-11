package locations;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationNestedTest {

    LocationParser parser;

    @BeforeEach
    void setUp() {
        parser = new LocationParser();
    }

    @Nested
    class FirstFavouriteLocation {

        Location favouriteLocation;

        @BeforeEach
        void setUp() {
            favouriteLocation = parser.parse("In The Middle Of Sea Near Africa,0.0,0.0");
        }

        @Test
        @DisplayName("Test location is on the Equator.")
        void testIsOnEquator() {
            assertTrue(favouriteLocation.isOnEquator());
        }

        @Test
        @DisplayName("Test location is on the Prime Meridian.")
        void testIsOnPrimeMeridian() {
            assertTrue(favouriteLocation.isOnPrimeMeridian());
        }
    }

    @Nested
    class SecondFavouriteLocation {

        Location favouriteLocation;

        @BeforeEach
        void setUp() {
            favouriteLocation = parser.parse("Budapest,47.497912,19.040235");
        }

        @Test
        @DisplayName("Test location is not on the Equator.")
        void testIsOnEquatorNotOnEquator() {
            assertFalse(favouriteLocation.isOnEquator());
        }

        @Test
        @DisplayName("Test location is not on the Prime Meridian.")
        void testIsOnPrimeMeridianNotOnPrimeMeridian() {
            Assertions.assertFalse(favouriteLocation.isOnPrimeMeridian());
        }
    }
}

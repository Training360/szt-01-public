package locations;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class LocationWithZeroCoordinate extends TypeSafeMatcher<Location> {

    private Matcher<Boolean> matcher;

    public LocationWithZeroCoordinate(Matcher<Boolean> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(Location location) {
        return matcher.matches(location.getLatitude() == 0.0 || location.getLongitude() == 0.0);
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("None of the coordinates is 0.")
                .appendDescriptionOf(matcher);
    }

    public static Matcher zeroCoordinate(Matcher<Boolean> matcher) {
        return new LocationWithZeroCoordinate(matcher);
    }
}

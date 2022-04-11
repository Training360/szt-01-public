package locations;

import org.assertj.core.api.AbstractAssert;

public class LocationAssert extends AbstractAssert<LocationAssert, Location> {

    public static LocationAssert assertThat(Location location) {
        return new LocationAssert(location);
    }

    protected LocationAssert(Location location) {
        super(location, LocationAssert.class);
    }

    public LocationAssert isNearTheArcticCircle() {
        double latitude = actual.getLatitude();
        if (latitude < 64.57 || latitude > 68.57) {
            failWithMessage(
                    "Expected latitude value to be between <64.57> and <68.57>, but was <%s>",
                    latitude
            );
        }
        return this;
    }
}

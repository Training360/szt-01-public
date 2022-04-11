package locations;

import java.util.List;
import java.util.stream.Collectors;

public class LocationOperators {

    //Assert 4.
    public static List<Location> filterOnNorth(List<Location> locationList) {
        return locationList.stream()
                .filter(location -> location.getLatitude() > 0)
                .collect(Collectors.toList());
    }
}
package locations;

import java.util.Optional;

public interface LocationsRepository {

    Optional<Location> findByName(String name);

    Optional<Double> findLatitudeByName(String name);
}

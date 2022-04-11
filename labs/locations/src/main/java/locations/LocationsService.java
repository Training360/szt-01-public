package locations;

import java.util.Optional;

public class LocationsService {

    private final LocationsRepository repository;

    public LocationsService(LocationsRepository repository) {
        this.repository = repository;
    }

    public Optional<Double> calculateDistance(String name1, String name2) {
        Optional<Location> firstOptional = repository.findByName(name1);
        Optional<Location> secondOptional = repository.findByName(name2);
        if (firstOptional.isEmpty() || secondOptional.isEmpty()) {
            return Optional.empty();
        } else {
            Location firstLocation = firstOptional.get();
            Location secondLocation = secondOptional.get();
            return Optional.of(firstLocation.distanceFrom(secondLocation));
        }
    }

    public boolean isOnNorthernHemisphere(String name) {
        Optional<Double> latitude = repository.findLatitudeByName(name);
        if (latitude.isEmpty()) {
            throw new IllegalArgumentException("No name, no party");
        } else {
            return latitude.get() > 0d;
        }
    }
}

package locations;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationsReader {

    //Hamcrest
    //AssertJ 1.
    public List<Location> readLocations(String path) {
        try {
            Path file = Paths.get(path);
            List<String> lines = Files.readAllLines(file);
            List<Location> locations = new ArrayList<>();
            for (String line : lines) {
                String[] split = line.split(",");
                locations.add(new Location(split[0], Double.parseDouble(split[1]), Double.parseDouble(split[2])));
            }
            return locations;
        } catch (Exception e) {
            throw new IllegalStateException("Can not read file", e);
        }
    }

    //AssertJ 2.
    public List<Location> filterLocationsBeyondArcticCircle(List<Location> locations) {
        return locations.stream()
                .filter(location -> location.getLatitude() > 66.57)
                .collect(Collectors.toList());
    }
}

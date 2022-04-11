package locations;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LocationsWriter {

    public static final String SEPARATOR = ",";

    public void writeLocations(Path file, List<Location> locations) {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file))) {
            for (Location location : locations) {
                writer.print(location.getName());
                writer.print(SEPARATOR);
                writer.print(location.getLatitude());
                writer.print(SEPARATOR);
                writer.println(location.getLongitude());
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Can not write file", ioe);
        }
    }
}

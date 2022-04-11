package locations;

import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

class LocationsJUnit4Test {

    @Test
    public void testCreate() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        assertEquals("Budapest", location.getName());
        assertEquals(47.497912, location.getLatitude(), 0.001);
        assertEquals(19.040235, location.getLongitude(), 0.001);
    }

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testWriteLocations() throws IOException {
        Path path = temporaryFolder.newFile("names.txt").toPath();
        List<Location> locations =
                Arrays.asList(new Location("Budapest", 47.497912, 19.040235),
                        new Location("Debrecen", 47.52997, 21.63916));
        new LocationsWriter().writeLocations(path, locations);

        List<String> lines = Files.readAllLines(path);
        assertEquals("Debrecen,47.52997,21.63916", lines.get(1));
    }
}

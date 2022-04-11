package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationsWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteLocations() throws IOException {
        LocationsWriter writer = new LocationsWriter();
        Path file = tempDir.resolve("locations.txt");
        writer.writeLocations(file, List.of(new Location("Budapest", 47.497912, 19.040235), new Location("Debrecen", 47.52997, 21.63916)));

        List<String> lines = Files.readAllLines(file);
        assertEquals("Debrecen,47.52997,21.63916", lines.get(1));
    }
}

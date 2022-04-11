package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class LocationsServiceTest {

    @Mock
    LocationsRepository locationsRepository;

    @InjectMocks
    LocationsService locationsService;

    @Test
    void testCalculateDistanceNoLocations() {
        LocationsRepository repository = new LocationsRepository() {
            @Override
            public Optional<Location> findByName(String name) {
                return Optional.empty();
            }

            @Override
            public Optional<Double> findLatitudeByName(String name) {
                return Optional.empty();
            }
        };
        LocationsService service = new LocationsService(repository);

        assertEquals(Optional.empty(), locationsService.calculateDistance("Budapest", "Paris"));
    }

    @Test
    void testCalculateDistanceNoFirstLocation() {
        when(locationsRepository.findByName("Budapest")).thenReturn(
                Optional.empty()
        );
        when(locationsRepository.findByName("Paris")).thenReturn(
                Optional.of(new Location("Paris", 48.87376, 2.25120))
        );

        assertEquals(Optional.empty(), locationsService.calculateDistance("Budapest", "Paris"));
        verify(locationsRepository).findByName(argThat(locationName -> locationName.equals("Budapest")));
        verify(locationsRepository).findByName(argThat(locationName -> locationName.equals("Paris")));
    }

    @Test
    void testCalculateDistanceNoSecondLocation() {
        when(locationsRepository.findByName("Budapest")).thenReturn(
                Optional.of(new Location("Budapest", 47.497912, 19.040235))
        );
        when(locationsRepository.findByName("Paris")).thenReturn(
                Optional.empty()
        );

        assertEquals(Optional.empty(), locationsService.calculateDistance("Budapest", "Paris"));
        verify(locationsRepository).findByName(argThat(locationName -> locationName.equals("Budapest")));
        verify(locationsRepository).findByName(argThat(locationName -> locationName.equals("Paris")));
    }

    @Test
    void testCalculateDistanceWithSameLocations() {
        when(locationsRepository.findByName("Budapest")).thenReturn(
                Optional.of(new Location("Budapest", 47.497912, 19.040235))
        );

        assertEquals(Optional.of(0.0), locationsService.calculateDistance("Budapest", "Budapest"));
        verify(locationsRepository, times(2)).findByName(argThat(locationName -> locationName.equals("Budapest")));
        verify(locationsRepository, never()).findByName(argThat(locationName -> locationName.equals("Paris")));
    }

    @Test
    void testCalculateDistance() {
        assertFalse(locationsService.calculateDistance("Budapest", "Debrecen").isPresent());

        when(locationsRepository.findByName("Budapest")).thenReturn(
                Optional.of(new Location("Budapest", 47.49791d, 19.04023d))
        );
        when(locationsRepository.findByName("Debrecen")).thenReturn(
                Optional.of(new Location("Debrecen", 47.52997d, 21.63916d))
        );

        assertEquals(195.2d, locationsService.calculateDistance("Budapest", "Debrecen").get());
    }

    @Test
    public void testIsOnNorthernHemisphereTrue() {
        when(locationsRepository.findLatitudeByName(any()))
                .thenReturn(Optional.of(42d));
        assertTrue(locationsService.isOnNorthernHemisphere("Budapest"));
    }

    @Test
    public void testIsOnNorthernHemisphereFalse() {
        when(locationsRepository.findLatitudeByName(any()))
                .thenReturn(Optional.of(-42d));
        assertFalse(locationsService.isOnNorthernHemisphere("Budapest"));
    }

    @Test
    public void testIsOnNorthernHemisphereException() {
        when(locationsRepository.findLatitudeByName(any()))
                .thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> locationsService.isOnNorthernHemisphere("Budapest"));
        verify(locationsRepository).findLatitudeByName("Budapest");
    }
}

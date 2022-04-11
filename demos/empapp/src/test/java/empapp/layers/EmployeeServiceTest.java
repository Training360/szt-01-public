package empapp.layers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void testSaveEmployee() {
        assertTrue(employeeService.createEmployee("   John Doe    ", 1970));

        verify(employeeRepository).saveEmployee(any());
        verify(employeeRepository).saveEmployee(argThat(e -> e.getName().equals("John Doe")));
    }

    @Test
    void testSaveEmployeeAlreadyExists() {
        when(employeeRepository.findEmployeeByName(any())).thenReturn(
                Optional.of(new Employee("John Doe", 1970))
        );

        boolean result = employeeService.createEmployee("John Doe", 1970);
        verify(employeeRepository, never()).saveEmployee(any());
        assertFalse(result);
    }
}

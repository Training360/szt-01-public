package empapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public interface PrintNameCapable {

    @BeforeEach
    default void printName(TestInfo testInfo) {
        System.out.println("Name: " + testInfo.getDisplayName());
    }
}

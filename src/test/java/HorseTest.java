import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
class HorseTest {
    static Horse horse;
    static Horse horseWithTwoParams;
    @BeforeAll
    static void setUp() {
        horse = new Horse("SuperIgogo", 20, 10);
        horseWithTwoParams = new Horse("Igogo", 10);
    }

    @Test
    public void constructor_whenFirstParamIsNull_thenThrowIllegalArgExc() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1.0, 2.0));
        assertEquals("Name cannot be null.", thrown.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "' '",
            "'\t'",
            "'\n'",
            "'\r'"
    })

    public void constructor_whenFirstParamIsEmptyStringOrHasWhitespaceCharacters_thenThrownIllegalArgExc(String name) {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 10, 10));
        assertEquals("Name cannot be blank.", thrown.getMessage());
    }
    @Test
    public void constructor_whenSecondParamIsNegativeNumber_thenTrowIllegalArgExc() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new Horse("ImSuperSlowlyHorse", -10, 10));
        assertEquals("Speed cannot be negative.", thrown.getMessage());
    }

    @Test
    public void constructor_whenThirdParamIsNegativeNumber_thenTrowIllegalArgExc() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Igogo", 10, -10));
        assertEquals("Distance cannot be negative.", thrown.getMessage());
    }

    @Test
    void getName_whenCalled_thenReturnNameSetInConstructor() {
        assertEquals("SuperIgogo", horse.getName());
    }

    @Test
    void getSpeed_whenCalled_thenReturnSpeedSetInConstructor() {
        assertEquals(20, horse.getSpeed());
    }

    @Test
    void getDistance_whenCalled_thenReturnDistanceSetInConstructor() {
        assertEquals(10, horse.getDistance());
    }

    @Test
    void getDistance_whenCalledWithTwoParams_thenReturnZero() {
        assertEquals(0, horseWithTwoParams.getDistance());
    }

    @Test
    void move_whenCalled_shouldCallGetRandomDoubleWithParams() {
        try (MockedStatic<Horse> mockedStaticMethods = Mockito.mockStatic(Horse.class)) {
            horse.move();
            mockedStaticMethods.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.4, 0.6, 0.8})
    void move_whenCalled_shouldUpdateDistanceCorrectly(double correctDistance) {
        try (MockedStatic<Horse> mockedStaticMethods = Mockito.mockStatic(Horse.class)) {
            mockedStaticMethods.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(correctDistance);

            double speed = horse.getSpeed();
            double initialDistance = horse.getDistance();

            horse.move();

            assertEquals(initialDistance + speed * correctDistance, horse.getDistance(), 0.001);
        }
    }
}
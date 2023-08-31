import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    public void constructor_whenParamIsNull_thenThrownIllegalArgumentExc() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", thrown.getMessage());
    }

    @Test
    public void constructor_whenParamIsEmptyList_thenThrownIllegalArgumentExc() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<Horse>()));
        assertEquals("Horses cannot be empty.", thrown.getMessage());
    }

    @Test
    void getHorses_whenCalled_thenReturnCorrectHorses() {
        List<Horse> horses = createHorses(30);
        Hippodrome hippodrome = new Hippodrome(horses);

        for (int i = 0; i < 30; i++) {
            assertSame(horses.get(i), hippodrome.getHorses().get(i));
        }
    }

    @Test
    public void move_whenCalled_shouldCallMoveOnAllHorses() {
        List<Horse> mockedHorses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            mockedHorses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodromeWithMockedHorses = new Hippodrome(mockedHorses);
        hippodromeWithMockedHorses.move();

        for (Horse horse : mockedHorses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner_whenCalled_thenReturnHorseWithMaxDistance() {
        List<Horse> horses = createHorses(30);
        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(39.0, hippodrome.getWinner().getDistance());
    }

    private List<Horse> createHorses(int number) {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            horses.add(new Horse("Horse" + i, i + 1.0, i + 10.0));
        }
        return horses;
    }
}

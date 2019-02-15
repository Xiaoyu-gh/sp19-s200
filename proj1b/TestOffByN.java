import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByFour = new OffByN(4);

    // Your tests go here.
    @Test
    public void testequalChars() {
        assertTrue(offByFour.equalChars('s', 'w'));
        assertFalse(offByFour.equalChars('a', 'a'));
        assertFalse(offByFour.equalChars('a', 'E'));
        assertTrue(offByFour.equalChars('%', '!'));
    }
}

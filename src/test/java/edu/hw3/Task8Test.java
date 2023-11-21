package edu.hw3;
import edu.hw3.Task8.BackwardIterator;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class Task8Test {

    @Test
    public void testBackwardIterator() {
        BackwardIterator<Integer> iterator = new BackwardIterator<>(List.of(1, 2, 3));

        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}

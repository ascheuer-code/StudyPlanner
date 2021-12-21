package Model;

import com.calendarfx.model.Entry;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EntryTest {

    @Test
    void testIdUnique() {
        ArrayList<Entry> test = new ArrayList<Entry>();
        ArrayList<Integer> intArray = new ArrayList<>();

        for (int i = 0; i < 5000000; i++) {

            Entry testEntry = new Entry("Test");
            test.add(testEntry);
        }

        List<Integer> list = intArray.stream().distinct().collect(Collectors.toList());

        assertEquals(0,list.size());
    }
}

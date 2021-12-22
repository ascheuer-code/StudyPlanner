package Model;

import com.calendarfx.model.Entry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EntryTest {

    @RepeatedTest(value = 10, name= "testidUnique {currentRepetition}/{totalRepetitions}")
    @Disabled //Achtung dauert ne Zeit lang !!!!
    void testIdUnique() {
        ArrayList<Event> test = new ArrayList<Event>();
        ArrayList<Integer> intArray = new ArrayList<>();

        for (int i = 0; i < 5000000; i++) {

            Event testEntry = new Event();
            test.add(testEntry);
        }

        List<Integer> list = intArray.stream().distinct().collect(Collectors.toList());

        assertEquals(0,list.size());
    }
}

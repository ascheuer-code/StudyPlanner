package DataAccess.Model;

import Model.Event;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
public class EventTest {

    /**
     * Constructor Test from Event
     * Condition:
     * Any Event gets a UUID
     */
    @Test
    void ConstructorTest(){
        Event event = new Event();
        assertTrue(!event.getId().equals(null)&& !event.getId().equals(""));
        System.out.println(event.getId());
    }

}

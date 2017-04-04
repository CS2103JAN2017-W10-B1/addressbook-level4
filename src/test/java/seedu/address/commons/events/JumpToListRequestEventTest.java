//@@author A0143409J
package seedu.address.commons.events;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

import seedu.address.commons.events.ui.JumpToListRequestEvent;

public class JumpToListRequestEventTest {
    @Test
    public void checkClassName_validInt_successfulChecking() {
        String className = "JumpToListRequestEvent";
        JumpToListRequestEvent event = new JumpToListRequestEvent(1);

        assertEquals(className, event.toString());
    }

    @Test
    public void checkTargetIndex_validInt_successfulChecking() throws Exception {
        Field field = JumpToListRequestEvent.class.getDeclaredField("targetIndex");
        field.setAccessible(true);

        JumpToListRequestEvent event = new JumpToListRequestEvent(1);
        assertEquals(1, field.get(event));
    }
}

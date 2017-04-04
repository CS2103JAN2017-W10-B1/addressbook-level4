//@@author A0143409J
package seedu.address.commons.events;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

import seedu.address.commons.events.ui.JumpToTagListRequestEvent;

public class JumpToTagListRequestEventTest {
    @Test
    public void checkClassName_validInt_successfulChecking() {
        String className = "JumpToTagListRequestEvent";
        JumpToTagListRequestEvent event = new JumpToTagListRequestEvent(1);

        assertEquals(className, event.toString());
    }

    @Test
    public void checkTargetIndex_validInt_successfulChecking() throws Exception {
        Field field = JumpToTagListRequestEvent.class.getDeclaredField("targetIndex");
        field.setAccessible(true);

        JumpToTagListRequestEvent event = new JumpToTagListRequestEvent(1);

        assertEquals(1, field.get(event));
    }
}

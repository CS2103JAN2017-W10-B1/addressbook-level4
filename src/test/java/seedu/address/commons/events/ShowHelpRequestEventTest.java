//@@author A0143409J
package seedu.address.commons.events;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.ui.ShowHelpRequestEvent;

public class ShowHelpRequestEventTest {
    @Test
    public void checkToString() {
        String className = "ShowHelpRequestEvent";
        ShowHelpRequestEvent event = new ShowHelpRequestEvent();

        assertEquals(className, event.toString());
    }
}

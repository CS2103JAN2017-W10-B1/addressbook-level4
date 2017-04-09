//@@author A0143409J-reused
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new change in Dueue is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;

    public NewResultAvailableEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

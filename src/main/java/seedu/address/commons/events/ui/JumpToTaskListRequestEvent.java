//@@author A0147996E
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tags
 */
public class JumpToTaskListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTaskListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

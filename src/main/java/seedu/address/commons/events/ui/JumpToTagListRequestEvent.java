//@@author A0147996E
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to a tag in tag list
 */
public class JumpToTagListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTagListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

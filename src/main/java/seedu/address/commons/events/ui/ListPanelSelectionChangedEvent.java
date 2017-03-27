//@@author A0147996E
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.tag.Tag;

/**
 * Represents a selection change in the Task List Panel
 */
public class ListPanelSelectionChangedEvent extends BaseEvent {


    private final Tag newSelection;

    public ListPanelSelectionChangedEvent(Tag newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Tag getNewSelection() {
        return newSelection;
    }
}

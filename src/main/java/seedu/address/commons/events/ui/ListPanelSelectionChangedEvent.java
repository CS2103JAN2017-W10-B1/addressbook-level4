//@@author Matilda_yxx A0147996E
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.tasklist.TaskList;

/**
 * Represents a selection change in the Task List Panel
 */
public class ListPanelSelectionChangedEvent extends BaseEvent {


    private final TaskList newSelection;

    public ListPanelSelectionChangedEvent(TaskList newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskList getNewSelection() {
        return newSelection;
    }
}

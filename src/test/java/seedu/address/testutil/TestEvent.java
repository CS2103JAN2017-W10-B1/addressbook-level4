//@@author A0147996E
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;

/**
 * A mutable event object. For testing only.
 */
public class TestEvent extends TestTask {

    private TaskDate startDate;
    private TaskTime startTime;

    public TestEvent() {}

    /**
     * Creates a copy of {@code eventToCopy}.
     */
    public TestEvent(TestEvent eventToCopy) {
        super(eventToCopy);
        this.startDate = eventToCopy.getStartDate();
        this.startTime = eventToCopy.getStartTime();
    }

    public void setStartDate(String date) throws IllegalValueException {
        assert date != null;
        this.startDate = new TaskDate(date);
    }

    public TaskDate getStartDate() {
        return startDate;
    }

    public void setStartTime(String time) throws IllegalValueException {
        assert time != null;
        this.startTime = new TaskTime(time);
    }

    public TaskTime getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getStartDate() != null) {
            builder.append("  Start Date:");
            builder.append(getStartDate());
        }
        if (getStartTime() != null) {
            builder.append("  Start Time:");
            builder.append(getStartTime());
        }
        if (getDate() != null) {
            builder.append("  Due Date:");
            builder.append(getDate());
        }
        if (getTime() != null) {
            builder.append("  Due Time:");
            builder.append(getTime());
        }
        if (getDescription() != null) {
            builder.append("  Description:");
            builder.append(getDescription());
        }
        if (getTag() != null) {
            builder.append("  List:");
            builder.append(getTag());
        }
        if (getVenue() != null) {
            builder.append("  Venue:");
            builder.append(getVenue());
        }
        assert getPriority() != null;
        builder.append("  Priority:");
        builder.append(getPriority());
        if (isFavorite()) {
            builder.append(" favorite");
        }
        if (isFinished()) {
            builder.append(" finished");
        }
        return builder.toString();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getAddCommand());
        sb.append("start/" + this.getStartDate().getValue() + " ");
        sb.append("startT/" + this.getStartTime().getValue() + " ");
        return sb.toString();
    }
}

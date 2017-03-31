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

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getAddCommand());
        sb.append("start/" + this.getStartDate().getValue() + " ");
        sb.append("startT/" + this.getStartTime().getValue() + " ");
        return sb.toString();
    }
}

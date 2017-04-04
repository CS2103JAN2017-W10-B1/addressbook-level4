package seedu.address.testutil;

import seedu.address.model.task.ReadOnlyRecurringTask;

/**
 * A mutable task object. For testing only.
 */
public class TestRecurringTask extends TestTask implements ReadOnlyRecurringTask {

    private RecurringProperty isRecurring;
    private RecurringMode mode;

    public TestRecurringTask() {}

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestRecurringTask(TestRecurringTask taskToCopy) {
        super(taskToCopy);
        this.isRecurring = taskToCopy.getRecurringProperty();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(" " + getRecurringPeriod());
        return builder.toString();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getAddCommand());
        sb.append("f/" + this.getRecurringPeriod() + " ");
        return sb.toString();
    }


    @Override
    public boolean isRecurring() {
        return this.isRecurring == RecurringProperty.RECURRING;
    }

    @Override
    public RecurringProperty getRecurringProperty() {
        return isRecurring;
    }

    @Override
    public void finishOnce() {
        this.date.addPeriod(mode);
    }

    @Override
    public String getRecurringPeriod() {
        return mode.toString();
    }

    @Override
    public RecurringMode getMode() {
        return mode;
    }
}

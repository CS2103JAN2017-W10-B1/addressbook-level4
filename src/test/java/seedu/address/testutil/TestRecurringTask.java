package seedu.address.testutil;

import seedu.address.model.task.ReadOnlyRecurringTask;

/**
 * A mutable task object. For testing only.
 */
public class TestRecurringTask extends TestTask implements ReadOnlyRecurringTask {
    public static final String PERIOD_DAILY = "every day";
    public static final String PERIOD_WEEKLY = "every week";
    public static final String PERIOD_MONTHLY = "every month";

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
        sb.append(" f/" + this.getRecurringPeriod() + " ");
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
    public RecurringMode getMode() {
        return mode;
    }

    public void setMode (RecurringMode mode) {
        this.mode = mode;
    }

    public void setRecurring (RecurringProperty isRecurring) {
        this.isRecurring = isRecurring;
    }

    @Override
    public String getRecurringPeriod() {
        if (mode.equals(RecurringMode.DAY)) {
            return PERIOD_DAILY;
        } else if (mode.equals(RecurringMode.WEEK)) {
            return PERIOD_WEEKLY;
        } else if (mode.equals(RecurringMode.MONTH)) {
            return PERIOD_MONTHLY;
        } else {
            assert false;
            return null;
        }
    }

    @Override
    public void undoFinishOnce() {
        this.date.addPeriod(mode, -1);
    }
}

package seedu.address.model.task;

import java.util.Queue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class RecurringTask extends Task implements ReadOnlyRecurringTask {

    public static final String PERIOD_DAILY = "every day";
    public static final String PERIOD_WEEKLY = "every week";
    public static final String PERIOD_MONTHLY = "every month";

    protected RecurringMode mode;
    protected Queue<TaskDate> queue;

    /**
     * Every field must not be null.
     */
    public RecurringTask(Name name, TaskDate date, TaskTime time, Description description, Tag tag, Venue venue,
            Priority priority, boolean isFavorite, RecurringMode mode) {
        super(name, date, time, description, tag, venue, priority, isFavorite);
        this.isRecurring = RecurringProperty.RECURRING;
        this.mode = mode;
    }

    /**
     * Constructor with flag isFinished
     */
    public RecurringTask(Name name, TaskDate date, TaskTime time, Description description, Tag tag, Venue venue,
            Priority priority, boolean isFavorite, FinishProperty isFinished, RecurringMode mode) {
        this(name, date, time, description, tag, venue, priority, isFavorite, mode);
        this.isFinished = isFinished;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     * @throws IllegalValueException
     */
    public RecurringTask(ReadOnlyTask source) throws IllegalValueException {
        super(new Name(source.getName().fullName), new TaskDate(source.getDate().getValue()),
                new TaskTime(source.getTime().getValue()), new Description(source.getDescription().getValue()),
                new Tag(source.getTag().tagName), new Venue(source.getVenue().getValue()),
                new Priority(source.getPriority().getValue()), source.isFavorite(), source.getFinished(),
                source.getEventProperty(), source.getRecurringProperty());
        assert this.isRecurring == RecurringProperty.RECURRING;
        this.mode = ((ReadOnlyRecurringTask) source).getMode();
    }

    @Override
    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        super.resetData(replacement);
        assert this.isRecurring == RecurringProperty.RECURRING;
        this.mode = ((ReadOnlyRecurringTask) replacement).getMode();
    }

    @Override
    public void finishOnce() {
        this.date.addPeriod(mode);
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

    public void setRecurringMode(RecurringMode mode) {
        this.mode = mode;
    }

    @Override
    public RecurringMode getMode() {
        return this.mode;
    }
}

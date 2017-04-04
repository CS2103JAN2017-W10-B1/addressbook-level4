//@@author A0147984L
package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class RecurringTask extends Task implements ReadOnlyRecurringTask {

    public static final String PERIOD_DAILY = "every day";
    public static final String PERIOD_WEEKLY = "every week";
    public static final String PERIOD_MONTHLY = "every month";

    public static final String PERIOD_DAY_REGEX = "(?i)((every)?(\\s)*(day))|(daily)";
    public static final String PERIOD_WEEK_REGEX = "(?i)((every)?(\\s)*(week))|(weekly)";
    public static final String PERIOD_MONTH_REGEX = "(?i)((every)?(\\s)*(month))|(monthly)";

    protected RecurringMode mode;

    /**
     * Every field must not be null.
     */
    public RecurringTask(Name name, TaskDate date, TaskTime time, Description description, Tag tag, Venue venue,
            Priority priority, boolean isFavorite, RecurringMode mode) {
        super(name, date, time, description, tag, venue, priority, isFavorite);
        this.isRecurring = RecurringProperty.RECURRING;
        this.mode = mode;
        checkDateForRecurring();
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
        super(new Name(source.getName().getValue()), new TaskDate(source.getDate().getValue()),
                new TaskTime(source.getTime().getValue()), new Description(source.getDescription().getValue()),
                new Tag(source.getTag().getValue()), new Venue(source.getVenue().getValue()),
                new Priority(source.getPriority().getValue()), source.isFavorite(), source.getFinished(),
                source.getEventProperty(), source.getRecurringProperty());
        assert this.isRecurring == RecurringProperty.RECURRING;
        this.mode = ((ReadOnlyRecurringTask) source).getMode();
        checkDateForRecurring();
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
        checkDateForRecurring();
    }

    /**
     * Set date to be day if date is not specified
     */
    private void checkDateForRecurring() {
        if (this.date.getValue().isEmpty()) {
            try {
                this.date = new TaskDate("today");
            } catch (IllegalValueException e) {
                assert false;
            }
        }
    }

    @Override
    public void finishOnce() {
        this.date.addPeriod(mode);
    }

    @Override
    public void undoFinishOnce() {
        this.date.addPeriod(mode, -1);
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

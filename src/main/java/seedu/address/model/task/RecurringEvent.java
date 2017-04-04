package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class RecurringEvent extends Event implements ReadOnlyRecurringEvent {

    public static final String PERIOD_DAILY = "every day";
    public static final String PERIOD_WEEKLY = "every week";
    public static final String PERIOD_MONTHLY = "every month";

    public static final String PERIOD_DAY_REGEX = "(?i)((every)?(\\s)*(day))|(daily)";
    public static final String PERIOD_WEEK_REGEX = "(?i)((every)?(\\s)*(week))|(weekly)";
    public static final String PERIOD_MONTH_REGEX = "(?i)((every)?(\\s)*(month))|(monthly)";

    protected RecurringMode mode;

    /**
     * Every field should not be null
     * @throws IllegalValueException
     */
    public RecurringEvent(Name name, TaskDate startDate, TaskTime startTime, TaskDate endDate,
            TaskTime endTime, Description description, Tag tag, Venue venue,
            Priority priority, boolean isFavorite, RecurringMode mode)
            throws IllegalValueException {
        super(name, startDate, startTime, endDate, endTime, description, tag, venue, priority, isFavorite);
        this.isRecurring = RecurringProperty.RECURRING;
        this.mode = mode;
        checkDateForRecurring();
    }

    /**
     *  Constructor of event with flag on isFinshed
     * @throws IllegalValueException
     */
    public RecurringEvent(Name name, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime,
            Description description, Tag tag, Venue venue, Priority priority,
            boolean isFavorite, FinishProperty isFinished, RecurringMode mode)
                    throws IllegalValueException {
        this(name, startDate, startTime, endDate, endTime, description, tag, venue,  priority, isFavorite, mode);
        this.isFinished = isFinished;
    }

    /**
     *  Constructor of event with only one date given
     * @throws IllegalValueException
     */
    public RecurringEvent(Name name, TaskDate date, TaskTime startTime, TaskTime endTime,
            Description description, Tag tag, Venue venue, Priority priority,
            boolean isFavorite, RecurringMode mode)
                    throws IllegalValueException {
        this(name, date, startTime, date, endTime, description, tag, venue,  priority, isFavorite, mode);
    }

    /**
     *  Constructor of event with only one date given, with flag on isFinished
     * @throws IllegalValueException
     */
    public RecurringEvent(Name name, TaskDate date, TaskTime startTime, TaskTime endTime,
            Description description, Tag tag, Venue venue, Priority priority,
            boolean isFavorite, FinishProperty isFinished, RecurringMode mode)
                    throws IllegalValueException {
        this(name, date, startTime, date, endTime, description, tag, venue,
                priority, isFavorite, isFinished, mode);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     * @throws IllegalValueException
     */
    public RecurringEvent(ReadOnlyTask source) throws IllegalValueException {
        super(source);
        assert this.isRecurring();
        this.mode = ((ReadOnlyRecurringEvent) source).getMode();
        checkDateForRecurring();
    }

    @Override
    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        super.resetData(replacement);
        assert this.isRecurring();
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
        this.startDate.addPeriod(mode);
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

//@@author A0147984L
package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class Event extends Task implements ReadOnlyEvent {

    public static final String MESSAGE_EVENT_CONSTRAINT = "End time should be latter than start time";
    private TaskDate startDate;
    private TaskTime startTime;

    /**
     * Every field must be present and not null.
     * @throws IllegalValueException
     */
    public Event(Name name, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime,
            Description description, Tag tag, Venue venue, Priority priority, boolean isFavorite)
                    throws IllegalValueException {
        super(name, endDate, endTime, description, tag, venue, priority, isFavorite);
        this.isEvent = EventProperty.EVENT;

        if (startDate.compareTo(endDate) > 0) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINT);
        } else {
            this.startDate = startDate;
            this.startTime = startTime;
        }

        if ((startDate.compareTo(endDate) == 0) && (startTime.compareTo(endTime) > 0)) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINT);
        } else {
            this.startDate = startDate;
            this.startTime = startTime;
        }
    }

    /**
     *  Constructor of event with flag on isFinshed
     * @throws IllegalValueException
     */
    public Event(Name name, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime,
            Description description, Tag tag, Venue venue,
            Priority priority, boolean isFavorite, FinishProperty isFinished)
                    throws IllegalValueException {
        this(name, startDate, startTime, endDate, endTime, description, tag, venue,  priority, isFavorite);
        this.isFinished = isFinished;
    }

    /**
     *  Constructor of event with only one date given
     * @throws IllegalValueException
     */
    public Event(Name name, TaskDate date, TaskTime startTime, TaskTime endTime,
            Description description, Tag tag, Venue venue, Priority priority, boolean isFavorite)
                    throws IllegalValueException {
        this(name, date, startTime, date, endTime, description, tag, venue,  priority, isFavorite);
    }

    /**
     *  Constructor of event with only one date given, with flag on isFinished
     * @throws IllegalValueException
     */
    public Event(Name name, TaskDate date, TaskTime startTime, TaskTime endTime,
            Description description, Tag tag, Venue venue, Priority priority,
            boolean isFavorite, FinishProperty isFinished)
                    throws IllegalValueException {
        this(name, date, startTime, date, endTime, description, tag, venue,  priority, isFavorite, isFinished);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     * @throws IllegalValueException
     */
    public Event(ReadOnlyTask source) throws IllegalValueException {
        this(source.getName(), ((ReadOnlyEvent) source).getStartDate(), ((ReadOnlyEvent) source).getStartTime(),
                source.getDate(), source.getTime(), source.getDescription(), source.getTag(),
                source.getVenue(), source.getPriority(), source.isFavorite(), source.getFinished());
        assert this.isEvent == EventProperty.EVENT;
    }

    @Override
    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        super.resetData(replacement);
        assert this.isEvent == EventProperty.EVENT;
        this.setStartTime(((ReadOnlyEvent) replacement).getStartTime());
        this.setStartDate(((ReadOnlyEvent) replacement).getStartDate());
    }

    @Override
    public TaskDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public TaskTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }
}


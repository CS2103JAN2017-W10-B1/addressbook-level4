package seedu.address.model.task;

import java.util.Objects;

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
            Description description, Tag tag, Venue venue, Priority priority, boolean isFavorite, boolean isFinished)
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
     * Creates a copy of the given ReadOnlyTask.
     * @throws IllegalValueException 
     */
    public Event(ReadOnlyEvent source) throws IllegalValueException {
        this(source.getName(), source.getStartDate(), source.getStartTime(), source.getDate(), source.getTime(),
                source.getDescription(),
                source.getTag(), source.getVenue(), source.getPriority(), source.isFavorite(), source.isFinished());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setDate(TaskDate date) {
        this.date = date;
    }

    @Override
    public TaskDate getDate() {
        return date;
    }

    public void setTime(TaskTime time) {
        this.time = time;
    }

    @Override
    public TaskTime getTime() {
        return time;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        assert tag != null;
        this.tag = tag;
    }

    @Override
    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public String getFavoriteText() {
        if (isFavorite) {
            return "Favorite";
        } else {
            return "";
        }
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String getFinishedText() {
        if (isFinished) {
            return "Finished";
        } else {
            return "Unfinished";
        }
    }

    public void setFinish(boolean isFinished) {
        this.isFinished = isFinished;
    }


    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setDate(replacement.getDate());
        this.setTime(replacement.getTime());
        this.setDescription(replacement.getDescription());
        this.setTag(replacement.getTag());
        this.setVenue(replacement.getVenue());
        this.setPriority(replacement.getPriority());
        this.setFavorite(replacement.isFavorite());
        this.setFinish(replacement.isFinished());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, time, description, tag, venue, priority, isFavorite, isFinished);
    }

    @Override
    public String toString() {
        return getAsText();
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


//@@author A0147984L
package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    protected Name name;
    protected TaskDate date;
    protected TaskTime time;
    protected Description description;
    protected Venue venue;
    protected Priority priority;
    protected boolean isFavorite;
    protected FinishProperty isFinished;
    protected Tag tag;
    protected EventProperty isEvent;
    protected RecurringProperty isRecurring;

    /**
     * Every field must not be null.
     */
    public Task(Name name, TaskDate date, TaskTime time, Description description, Tag tag,
            Venue venue, Priority priority, boolean isFavorite) {
        //assert !CollectionUtil.isAnyNull(name, date, time, description, tag, venue, priority, isFavorite);
        assert !CollectionUtil.isAnyNull(name, date, time, description, tag,
                venue, priority, isFavorite);
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.tag = tag;
        this.venue = venue;
        this.priority = priority;
        this.isFavorite = isFavorite;
        this.isFinished = FinishProperty.UNFINISHED;
        this.isEvent = EventProperty.NON_EVENT;
        this.isRecurring = RecurringProperty.NON_RECURRING;
    }

    /**
     * Constructor with flag on isFavorite
     */
    public Task(Name name, TaskDate date, TaskTime time, Description description, Tag tag,
            Venue venue, Priority priority, boolean isFavorite, FinishProperty isFinished) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.tag =  tag;
        this.venue = venue;
        this.priority = priority;
        this.isFavorite = isFavorite;
        this.isFinished = isFinished;
        this.isEvent = EventProperty.NON_EVENT;
        this.isRecurring = RecurringProperty.NON_RECURRING;
    }

    /**
     *  Constructor of task with flag on isFinshed, flag on isEvent, flag on isRecurring
     */
    public Task(Name name, TaskDate date, TaskTime time, Description description, Tag tag,
            Venue venue, Priority priority, boolean isFavorite, FinishProperty isFinished,
            EventProperty isEvent, RecurringProperty isRecurring) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.tag =  tag;
        this.venue = venue;
        this.priority = priority;
        this.isFavorite = isFavorite;
        this.isFinished = isFinished;
        this.isEvent = isEvent;
        this.isRecurring = isRecurring;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     * @throws IllegalValueException
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(new Name(source.getName().getValue()), new TaskDate(source.getDate().getValue()),
                new TaskTime(source.getTime().getValue()), new Description(source.getDescription().getValue()),
                new Tag(source.getTag().getValue()), new Venue(source.getVenue().getValue()),
                new Priority(source.getPriority().getValue()), source.isFavorite(), source.getFinished(),
                source.getEventProperty(), source.getRecurringProperty());
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
            return "Favorite \u2764";
        } else {
            return "";
        }
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public boolean isFinished() {
        return isFinished == FinishProperty.FINISHED;
    }

    @Override
    public String getFinishedText() {
        if (isFinished()) {
            return "Finished";
        } else {
            return "Unfinished";
        }
    }

    public void setFinish(Boolean isFinished) {
        if (isFinished) {
            this.isFinished = FinishProperty.FINISHED;
        } else {
            this.isFinished = FinishProperty.UNFINISHED;
        }
    }


    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        try {
            this.setName(new Name(replacement.getName().getValue()));
            this.setDate(new TaskDate(replacement.getDate().getValue()));
            this.setTime(new TaskTime(replacement.getTime().getValue()));
            this.setDescription(new Description(replacement.getDescription().getValue()));
            this.setTag(new Tag(replacement.getTag().getValue()));
            this.setVenue(new Venue(replacement.getVenue().getValue()));
            this.setPriority(new Priority(replacement.getPriority().getValue()));
            this.setFavorite(replacement.isFavorite());
            this.setFinish(replacement.isFinished());
            this.setIsEvent(replacement.getEventProperty());
            this.setIsRecurring(replacement.getRecurringProperty());
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    private void setIsEvent(EventProperty isEvent) {
        this.isEvent = isEvent;
    }

    private void setIsRecurring(RecurringProperty isRecurring) {
        this.isRecurring = isRecurring;
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
    public boolean isEvent() {
        return this.isEvent == EventProperty.EVENT;
    }

    @Override
    public boolean isRecurring() {
        return this.isRecurring == RecurringProperty.RECURRING;
    }

    @Override
    public FinishProperty getFinished() {
        return this.isFinished;
    }

    @Override
    public EventProperty getEventProperty() {
        return this.isEvent;
    }

    public void setRecurringProperty(RecurringProperty isRecurring) {
        this.isRecurring = isRecurring;
    }

    @Override
    public RecurringProperty getRecurringProperty() {
        return this.isRecurring;
    }
}

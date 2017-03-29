//@@author A0147996E
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.Venue;

/**
 * A mutable event object. For testing only.
 */
public class TestEvent extends TestTask {

    private Name name;
    private TaskDate date;
    private TaskTime time;
    private TaskDate startDate;
    private TaskTime startTime;
    private Description description;
    private Venue venue;
    private Priority priority;
    private Tag tag;
    private boolean isFavorite;
    private FinishProperty isFinished;
    private EventProperty isEvent;

    public TestEvent() {}

    /**
     * Creates a copy of {@code eventToCopy}.
     */
    public TestEvent(TestEvent eventToCopy) {
        this.name = eventToCopy.getName();
        this.date = eventToCopy.getDate();
        this.time = eventToCopy.getTime();
        this.startDate = eventToCopy.getDate();
        this.startTime = eventToCopy.getTime();
        this.tag = eventToCopy.getTag();
        this.description = eventToCopy.getDescription();
        this.venue = eventToCopy.getVenue();
        this.priority = eventToCopy.getPriority();
        this.isFavorite = eventToCopy.isFavorite();
        this.isFinished = eventToCopy.getFinished();
        this.isEvent = eventToCopy.getEventProperty();
    }

    public void setName(String name) throws IllegalValueException {
        assert name != null;
        this.name = new Name(name);
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setDate(String date) throws IllegalValueException {
        assert date != null;
        this.date = new TaskDate(date);
    }

    @Override
    public TaskDate getDate() {
        return date;
    }

    public void setStartDate(String date) throws IllegalValueException {
        assert date != null;
        this.startDate = new TaskDate(date);
    }

    public TaskDate getStartDate() {
        return startDate;
    }

    public void setTime(String time) throws IllegalValueException {
        assert time != null;
        this.time = new TaskTime(time);
    }

    @Override
    public TaskTime getTime() {
        return time;
    }

    public void setStartTime(String time) throws IllegalValueException {
        assert time != null;
        this.startTime = new TaskTime(time);
    }

    public TaskTime getStartTime() {
        return startTime;
    }

    public void setDescription(String description) throws IllegalValueException {
        assert description != null;
        this.description = new Description(description);
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Tag getTag() {
        return tag;
    }

    public void setTag(String tag) throws IllegalValueException {
        assert tag != null;
        this.tag = new Tag(tag);
    }

    @Override
    public Venue getVenue() {
        return venue;
    }

    public void setVenue(String venue) throws IllegalValueException {
        assert venue != null;
        this.venue = new Venue(venue);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(String priority) throws IllegalValueException {
        assert priority != null;
        this.priority = new Priority(priority);
    }

    @Override
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public boolean isFinished() {
        return isFinished == FinishProperty.FINISHED;
    }

    public void setFinished(boolean isFinished) {
        if (isFinished) {
            this.isFinished = FinishProperty.FINISHED;
        } else {
            this.isFinished = FinishProperty.UNFINISHED;
        }
    }

    public void setEvent(boolean isEvent) {
        if (isEvent) {
            this.isEvent = EventProperty.EVENT;
        } else {
            this.isEvent = EventProperty.NON_EVENT;
        }
    }

    @Override
    public String getFavoriteText() {
        if (isFavorite) {
            return "Favorite";
        } else {
            return "";
        }
    }

    @Override
    public String getFinishedText() {
        if (isFinished == FinishProperty.FINISHED) {
            return "Finished";
        } else {
            return "Unfinished";
        }
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().toString() + " ");
        sb.append("due/" + this.getDate().getValue() + " ");
        sb.append("dueT/" + this.getTime().getValue() + " ");
        sb.append("start/" + this.getStartDate().getValue() + " ");
        sb.append("startT/" + this.getStartTime().getValue() + " ");
        sb.append("d/" + this.getDescription().getValue() + " ");
        sb.append("#" + this.getTag().getName() + " ");
        sb.append("@" + this.getVenue().getValue() + " ");
        sb.append("p/" + this.getPriority().getValue() + " ");
        if (this.isFavorite()) {
            sb.append("*f" + " ");
        }
        return sb.toString();
    }
    @Override
    public boolean isEvent() {
        return isEvent == EventProperty.EVENT;
    }

    @Override
    public FinishProperty getFinished() {
        return this.isFinished;
    }

    @Override
    public EventProperty getEventProperty() {
        return this.isEvent;
    }

}

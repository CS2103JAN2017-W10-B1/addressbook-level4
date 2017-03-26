package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.Venue;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    //@@author A0147984L
    private Name name;
    private TaskDate date;
    private TaskTime time;
    private Description description;
    private Venue venue;
    private Priority priority;
    private Tag tag;
    private boolean isFavorite;
    private FinishProperty isFinished;
    private boolean isEvent;

    public TestTask() {}

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.date = taskToCopy.getDate();
        this.time = taskToCopy.getTime();
        this.tag = taskToCopy.getTag();
        this.description = taskToCopy.getDescription();
        this.venue = taskToCopy.getVenue();
        this.priority = taskToCopy.getPriority();
        this.isFavorite = taskToCopy.isFavorite();
        this.isFinished = taskToCopy.getFinished();
        this.isEvent = taskToCopy.isEvent();
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

    public void setTime(String time) throws IllegalValueException {
        assert time != null;
        this.time = new TaskTime(time);
    }

    @Override
    public TaskTime getTime() {
        return time;
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
    public Boolean isFinished() {
        return isFinished == FinishProperty.Finished;
    }
//@@ author A0147996E
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
        if (isFinished == FinishProperty.Finished) {
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
        sb.append("d/" + this.getDescription().getValue() + " ");
        sb.append("#" + this.getTag().getName() + " ");
        sb.append("@" + this.getVenue().getValue() + " ");
        sb.append("p/" + this.getPriority().getValue() + " ");
        if (this.isFavorite()) {
            sb.append("*f" + " ");
        }
        return sb.toString();
    }
    //@@author A0138474X
    @Override
    public boolean isEvent() {
        return isEvent;
    }

    @Override
    public FinishProperty getFinished() {
        return this.isFinished;
    }
}

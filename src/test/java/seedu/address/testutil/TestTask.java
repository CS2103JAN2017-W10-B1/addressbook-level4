package seedu.address.testutil;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.Date;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Time;
import seedu.address.model.task.Venue;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Date date;
    private Time time;
    private Description description;
    private Venue venue;
    private Priority priority;
    private boolean isFavorite;
    private Tag tag;

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
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setDate(Date date) {
        assert date != null;
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setTime(Time time) {
        assert time != null;
        this.time = time;
    }

    @Override
    public Time getTime() {
        return time;
    }

    public void setDescription(Description description) {
        assert description != null;
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
    public Venue getVenue(){
        return venue;
    }

    public void setVenue(Venue venue){
        assert venue != null;
        this.venue = venue;
    }

    @Override
    public Priority getPriority(){
        return priority;
    }

    public void setPriority(Priority priority){
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public boolean isFavorite(){
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite){
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        return null;
        //TODO: add test cases
    }
}

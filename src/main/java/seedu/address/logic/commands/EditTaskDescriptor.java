package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.Venue;

/**
 * Stores the details to edit the task with. Each non-empty field value will replace the
 * corresponding field value of the task.
 */
public class EditTaskDescriptor {
    private Optional<Name> name = Optional.empty();
    private Optional<TaskDate> due = Optional.empty();
    private Optional<TaskTime> dueTime = Optional.empty();
    private Optional<TaskDate> start = Optional.empty();
    private Optional<TaskTime> startTime = Optional.empty();
    private Optional<Description> description = Optional.empty();
    private Optional<Tag> tag = Optional.empty();
    private Optional<Venue> venue = Optional.empty();
    private Optional<Priority> priority = Optional.empty();
    private RecurringMode recurringMode;
    private boolean isFavourite;

    private boolean isFavouriteEdited;

    public EditTaskDescriptor() {}

    public EditTaskDescriptor(EditTaskDescriptor toCopy) {
        this.name = toCopy.getName();
        this.due = toCopy.getDue();
        this.dueTime = toCopy.getDueTime();
        this.start = toCopy.getStart();
        this.startTime = toCopy.getStartTime();
        this.description = toCopy.getDescription();
        this.tag = toCopy.getTag();
        this.venue = toCopy.getVenue();
        this.priority = toCopy.getPriority();
        this.isFavourite = toCopy.getFavourite();
        this.isFavouriteEdited = toCopy.getIsFavouriteEdited();
        this.recurringMode = toCopy.getRecurringMode();
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyPresent(
                this.name, this.due, this.dueTime, this.start, this.startTime,
                this.description, this.tag, this.venue, this.priority) ||
                this.isFavouriteEdited || (this.recurringMode != null);
    }

    public void setName(Optional<Name> name) {
        assert name != null;
        this.name = name;
    }

    public Optional<Name> getName() {
        return name;
    }

    public void setDue(Optional<TaskDate> due) {
        assert due != null;
        this.due = due;
    }

    public void setStart(Optional<TaskDate> start) {
        assert start != null;
        this.start = start;
    }

    public Optional<TaskDate> getDue() {
        return due;
    }

    public Optional<TaskDate> getStart() {
        return start;
    }

    public void setDueTime(Optional<TaskTime> dueTime) {
        assert dueTime != null;
        this.dueTime = dueTime;
    }

    public void setStartTime(Optional<TaskTime> startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    public Optional<TaskTime> getDueTime() {
        return dueTime;
    }

    public Optional<TaskTime> getStartTime() {
        return startTime;
    }

    public void setDescription(Optional<Description> description) {
        assert description != null;
        this.description = description;
    }

    public Optional<Description> getDescription() {
        return description;
    }

    public void setTag(Optional<Tag> tag) {
        assert tag != null;
        this.tag = tag;
    }

    public Optional<Tag> getTag() {
        return tag;
    }

    public void setVenue(Optional<Venue> venue) {
        assert venue != null;
        this.venue = venue;
    }

    public Optional<Venue> getVenue() {
        return venue;
    }

    public void setPriority(Optional<Priority> priority) {
        assert priority != null;
        this.priority = priority;
    }

    public Optional<Priority> getPriority() {
        return priority;
    }

    public void setIsFavourite(boolean isFavourite) {
        if (isFavourite) {
            this.isFavouriteEdited = true;
            this.isFavourite = true;
        }
    }

    protected boolean getFavourite() {
        return this.isFavourite;
    }

    protected boolean getIsFavouriteEdited() {
        return this.isFavouriteEdited;
    }

    public void setIsUnfavourite (boolean isUnFavourite) {
        if (isUnFavourite) {
            this.isFavouriteEdited = true;
            this.isFavourite = false;
        }
    }

    public boolean updatedEvent(Optional<TaskDate> start) {
        return start.isPresent();
    }

    //@@author A0147984L
    public void setRecurringMode(Optional<String> ocurrence) {
        if (ocurrence.isPresent()) {
            String ocurring = ocurrence.orElse("");
            if (ocurring.matches(RecurringTask.PERIOD_DAY_REGEX)) {
                this.recurringMode = RecurringMode.DAY;
            } else if (ocurring.matches(RecurringTask.PERIOD_WEEK_REGEX)) {
                this.recurringMode = RecurringMode.WEEK;
            } else if (ocurring.matches(RecurringTask.PERIOD_MONTH_REGEX)) {
                this.recurringMode = RecurringMode.MONTH;
            }
        } else {
            this.recurringMode = null;
        }
    }

    public RecurringMode getRecurringMode() {
        return this.recurringMode;
    }
    //@@author
}

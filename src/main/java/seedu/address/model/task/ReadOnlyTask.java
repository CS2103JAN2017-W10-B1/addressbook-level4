//@@author A0147984L
package seedu.address.model.task;

import seedu.address.model.tag.Tag;

/**
 * A read-only immutable interface for a Task in the TaskManager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {
    public enum FinishProperty {
        FINISHED, UNFINISHED
    }
    public enum EventProperty {
        EVENT, NON_EVENT
    }
    public enum RecurringProperty {
        RECURRING, NON_RECURRING
    }

    Name getName();
    TaskDate getDate();
    TaskTime getTime();
    Tag getTag();
    Description getDescription();
    Venue getVenue();
    Priority getPriority();
    boolean isFavorite();
    boolean isFinished();
    String getFavoriteText();
    String getFinishedText();
    boolean isEvent();
    boolean isRecurring();

    /**
     * Returns true if both have the same state. (interfaces cannot override equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        if (this.isRecurring() ^ other.isRecurring()) {
            return false;
        }
        if (this.isEvent() ^ other.isEvent()) {
            return false;
        }
        boolean state = other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && checkEqual(this.getName(), other.getName())
                && checkEqual(this.getDate(), other.getDate())
                && checkEqual(this.getTime(), other.getTime())
                && checkEqual(this.getTag(), other.getTag())
                && ((other.getFinished() == null && this.getFinished() == null)
                        || (other.getFinished() != null && other.getFinished().equals(this.getFinished()))));
                    // state checks here onwards
        if (this.isEvent()) {
            state = state && (other instanceof ReadOnlyEvent)
                    && checkEqual(((ReadOnlyEvent) this).getStartDate(), ((ReadOnlyEvent) other).getStartDate())
                    && checkEqual(((ReadOnlyEvent) this).getStartTime(), ((ReadOnlyEvent) other).getStartTime());
                    // state checks here onwards
        }
        if (this.isRecurring() && !this.isEvent()) {
            state = state && (other instanceof ReadOnlyRecurringTask)
                    && ((((ReadOnlyRecurringTask) this).getRecurringPeriod()).equals(
                            ((ReadOnlyRecurringTask) other).getRecurringPeriod()));
        }
        return state;

    }
//@@author A0147996E
    /**
     * Comparing two tasks in GUItests,
     * to check if the list view matches desired list view.
     *
     * @param other A ReadOnlyTask for comparison.
     * @return whether the two Tasks are considered equivalent for list view.
     */
    default boolean isSameCardAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && checkEqual(this.getName(), other.getName())
                && checkEqual(this.getDate(), other.getDate())
                && checkEqual(this.getTime(), other.getTime())
                && checkEqual(this.getTag(), other.getTag())
                && ((other.getFinished() == null && this.getFinished() == null)
                        || (other.getFinished() != null && other.getFinished().equals(this.getFinished()))));
    }

//@@author A0143409J

    /* return the FinishProperty of a Task */
    FinishProperty getFinished();

    /* return the EventProperty of a Task */
    EventProperty getEventProperty();

    /* return the RecurringProperty of a Task */
    RecurringProperty getRecurringProperty();

    /**
<<<<<<< HEAD
     * Ensure there is no null pointer exception when comparing two TaskFields
=======
     * Check for equivalence of two TaskField objects.
     * Refrain from NullPointerException.
     *
     * @return whether they are equivalent.
>>>>>>> bb480ab433c1d473cd41b5044c618ee07581ab0d
     */
    default boolean checkEqual(TaskField mine, TaskField other) {
        if (mine == null) {
            return other == null;
        } else {
            return mine.equals(other);
        }
    }
//@@author
    /**
     * Formats a task into text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getDate() != null) {
            builder.append("  Due Date:");
            builder.append(getDate());
        }
        if (getTime() != null) {
            builder.append("  Time:");
            builder.append(getTime());
        }
        if (getDescription() != null) {
            builder.append("  Description:");
            builder.append(getDescription());
        }
        if (getTag() != null) {
            builder.append("  List:");
            builder.append(getTag());
        }
        if (getVenue() != null) {
            builder.append("  Venue:");
            builder.append(getVenue());
        }
        assert getPriority() != null;
        builder.append("  Priority:");
        builder.append(getPriority());
        if (isFavorite()) {
            builder.append(" favorite");
        }
        if (isFinished()) {
            builder.append(" finished");
        }
        return builder.toString();
    }
}

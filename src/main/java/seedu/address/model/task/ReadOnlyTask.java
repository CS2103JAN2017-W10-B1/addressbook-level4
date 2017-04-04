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
            state = state
                    && checkEqual(((ReadOnlyEvent) this).getStartDate(), ((ReadOnlyEvent) other).getStartDate())
                    && checkEqual(((ReadOnlyEvent) this).getStartTime(), ((ReadOnlyEvent) other).getStartTime());
                    // state checks here onwards
        }
        if (this.isRecurring()) {
            state = state
                    && ((((ReadOnlyRecurringTask) this).getRecurringPeriod()).equals(
                            ((ReadOnlyRecurringTask) this).getRecurringPeriod()));
        }
        return state;

    }
//@@author A0147996E
    /**
     * For comparing two tasks in GUItests, to check if the list view matches desired list view.
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
    /*
     * Get the FinishProperty instead of boolean
     */
    FinishProperty getFinished();
    EventProperty getEventProperty();
    RecurringProperty getRecurringProperty();

    /*
     * Ensure there is no null pointer exception when comparing two TaskFields
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
     * Formats the person as text, showing all contact details.
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

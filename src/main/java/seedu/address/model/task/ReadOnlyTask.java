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

    //@@author A0147984L
    /**
     * Returns true if both have the same state. (interfaces cannot override equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        if (other.isFinished() == true || this.isFinished() == true) {
            return false;
        } // finished task are always treated as different
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && checkEqual(this.getName(), other.getName())
                && checkEqual(this.getDate(), other.getDate())
                && checkEqual(this.getTime(), other.getTime())
                && checkEqual(this.getTag(), other.getTag())); // state checks here onwards
    }

    //@@ author A0143409J
    /*
     * Get the FinishProperty instead of boolean
     */
    FinishProperty getFinished();
    EventProperty getEventProperty();

    default boolean checkEqual(TaskField mine, TaskField other) {
        if (mine == null) {
            return other == null;
        } else {
            return mine.equals(other);
        }
    }
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

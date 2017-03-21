package seedu.address.model.task;

import seedu.address.model.tag.Tag;

/**
 * A read-only immutable interface for a Task in the TaskManager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

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

    //@@author A0147984L
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())
                && other.getDate().equals(this.getDate())
                && other.getTime().equals(this.getTime())
                && other.getTag().equals(this.getTag())); // state checks here onwards
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

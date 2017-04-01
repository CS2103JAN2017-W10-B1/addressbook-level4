package seedu.address.model.task;

public interface ReadOnlyRecurringTask extends ReadOnlyTask {
    public enum RecurringMode {
        DAY, WEEK, MONTH
    }

    void finishOnce();
    String getRecurringPeriod();
    RecurringMode getMode();

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
            builder.append("  Due Time:");
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
        if (true) {
            builder.append(getRecurringPeriod());
        }
        return builder.toString();
    }
}

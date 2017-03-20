package seedu.address.model.task;

public interface ReadOnlyEvent extends ReadOnlyTask{
    
    TaskDate getStartDate();
    Time getStartTime();
    
    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getStartDate() != null) {
            builder.append("  Start Date:");
            builder.append(getDate());
        }
        if (getStartTime() != null) {
            builder.append("  Start Time:");
            builder.append(getTime());
        }
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
        return builder.toString();
    }
}

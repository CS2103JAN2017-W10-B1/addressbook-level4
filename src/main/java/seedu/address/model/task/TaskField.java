//@@author A0143409J
/*
 * A read-only immutable interface for any field of a task.
 */
package seedu.address.model.task;

/**
 * A read-only interface for all the fields of a Task.
 */
public interface TaskField {

    /**
     * @return the message for display of a particular field.
     */
    public abstract String getDisplayText ();

    /**
     * @return the {@code String} value of the field.
     */
    public abstract String getValue();

}

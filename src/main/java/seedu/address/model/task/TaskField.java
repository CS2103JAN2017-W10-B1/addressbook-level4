//@@author A0143409J
/*
 * A read-only immutable interface for any field of a task.
 */
package seedu.address.model.task;

public interface TaskField {

    String getDisplayText ();

    String getValue();

}

package seedu.address.model.tasklist;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Represents a List in Dueue.
 * Guarantees: name is valid as declared in {@link #isValidListName(String)}
 */
public class TaskList{
    
    public static final String MESSAGE_LIST_CONSTRAINTS = "List name should not begin with digits.";
    //TODO: public static final String LIST_VALIDATION_REGEX = ;
    
    private String name;
    private UniqueTaskList tasks;
    
    /**
     * Validates given list name.
     *
     * @throws IllegalValueException if the given list name string is invalid.
     */
    public TaskList(String name) throws IllegalValueException{
        assert name != null;
        String trimmedName = name.trim();
        if (!isValidListName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_LIST_CONSTRAINTS);
        }
        this.name = trimmedName;
        this.tasks = new UniqueTaskList();
    }

    /**
     * Returns true if a given string is a valid list name.
     */
    public static boolean isValidListName(String trimmedName) {
        // TODO Auto-generated method stub
        return true;
    }

    public void add(Task task) throws DuplicateTaskException{
        assert task != null;
        tasks.add(task);
    }
    
    public String getName(){
        return name;
    }
    
    public UniqueTaskList getTasks() {
        return tasks;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && this.name.equals(((TaskList) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '{' + name + '}';
    }

    /**
     * Reset the TaskList with a new TaskList as the name
     */
    public void resetData(TaskList editedList) {
        assert editedList != null;
        this.name = editedList.name;
        this.tasks = editedList.tasks;
    }

}

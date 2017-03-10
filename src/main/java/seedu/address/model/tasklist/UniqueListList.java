package seedu.address.model.tasklist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * A list of lists that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Tag#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueListList implements Iterable<TaskList> {

    private final ObservableList<TaskList> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ListList.
     */
    public UniqueListList() {}

    /**
     * Creates a UniqueListList using given String lists.
     * Enforces no nulls or duplicates.
     */
    public UniqueListList(String... lists) throws DuplicateListException, IllegalValueException {
        final List<TaskList> listList = new ArrayList<TaskList>();
        for (String list : lists) {
            listList.add(new TaskList(list));
        }
        setLists(listList);
    }

    /**
     * Creates a UniqueListList using given lists.
     * Enforces no nulls or duplicates.
     */
    public UniqueListList(TaskList... lists) throws DuplicateListException {
        assert !CollectionUtil.isAnyNull((Object[]) lists);
        final List<TaskList> initialLists = Arrays.asList(lists);
        if (!CollectionUtil.elementsAreUnique(initialLists)) {
            throw new DuplicateListException();
        }
        internalList.addAll(initialLists);
    }

    /**
     * Creates a UniqueListList using given lists.
     * Enforces no null or duplicate elements.
     */
    public UniqueListList(Collection<TaskList> lists) throws DuplicateListException {
        this();
        setLists(lists);
    }

    /**
     * Creates a UniqueListList using given lists.
     * Enforces no nulls.
     */
    public UniqueListList(Set<TaskList> lists) {
        assert !CollectionUtil.isAnyNull(lists);
        internalList.addAll(lists);
    }

    /**
     * Creates a copy of the given list.
     * Insulates from changes in source.
     */
    public UniqueListList(UniqueListList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * Returns all lists in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<TaskList> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setLists(UniqueListList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setLists(Collection<TaskList> lists) throws DuplicateListException {
        assert !CollectionUtil.isAnyNull(lists);
        if (!CollectionUtil.elementsAreUnique(lists)) {
            throw new DuplicateListException();
        }
        internalList.setAll(lists);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueListList from) {
        final Set<TaskList> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(tag -> !alreadyInside.contains(tag))
                .forEach(internalList::add);
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(TaskList toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a List to the list.
     *
     * @throws DuplicateTagException if the List to add is a duplicate of an existing List in the list.
     */
    public void add(TaskList toAdd) throws DuplicateListException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateListException();
        }
        internalList.add(toAdd);
    }


    /**
     * Updates the list in the list at position {@code index} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the list's details causes the list to be equivalent to
     *      another existing list in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, TaskList editedList) throws DuplicateListException {
        assert editedList != null;

        TaskList listToUpdate = internalList.get(index);
        if (!listToUpdate.equals(editedList) && internalList.contains(editedList)) {
            throw new DuplicateListException();
        }

        listToUpdate.resetData(editedList);
        // TODO: The code below is just a workaround to notify observers of the updated task.
        // The right way is to implement observable properties in the Task class.
        // Then, TaskCard should then bind its text labels to those observable properties.
        internalList.set(index, listToUpdate);
    }

    /**
     * Removes the equivalent lsit from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(TaskList toRemove) throws ListNotFoundException {
        assert toRemove != null;
        final boolean listFoundAndDeleted = internalList.remove(toRemove);
        if (!listFoundAndDeleted) {
            throw new ListNotFoundException();
        }
        return listFoundAndDeleted;
    }
    
    @Override
    public Iterator<TaskList> iterator() {
        return internalList.iterator();
    }

    public UnmodifiableObservableList<TaskList> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueListList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueListList) other).internalList));
    }

    public boolean equalsOrderInsensitive(UniqueListList other) {
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateListException extends DuplicateDataException {
        protected DuplicateListException() {
            super("Operation would result in duplicate lists");
        }
    }
    
    public static class ListNotFoundException extends Exception{
        public ListNotFoundException() {
            super("The list requested is not found");
        }
    }

}

package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {

    public enum FinishedState {
        ALL, FINISHED, UNFINISHED
        }
    public enum DueMode {
        BY, ON
        }

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered list list as an {@code UnmodifiableObservableList<TaskList>} */
    UnmodifiableObservableList<Tag> getFilteredTagList();

    // Task-level filter
    /**
     * Update the filter of the filter task given specific fields
     *
     * @param nameKeywords indicates the name field
     * @param tagKeywords indicates the tag search field
     * @param finishedState indicates the finished search field (ALL/UNFINISHED/FINISHED)
     * @param isFavorite indicates the favorite search field
     * @param dueMode indicates the view mode on due (ON/BY)
     * @param days indicates the days until due
     */
    void updateFilteredTaskList(Set<String> nameKeywords, Set<String> tagKeywords,
            FinishedState finishedState, boolean isFavorite,
            DueMode dueMode, String days);

    /** Updates the filter of the filtered task list to show all favorite tasks */
    void updateFilteredListToShowAllFavoriteTasks();

    /** Updates the filter of the filtered task list to show all unfinished tasks */
    void updateFilteredListToShowAllUnfinishedTasks();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAllTasks();

    /** Updates the filter of the filtered task list to show all finished tasks */
    void updateFilteredListToShowAllFinishedTasks();

    /** Updates the filter of the unfinished filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskListFinished(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskListAll(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskListFavorite(Set<String> keywords);

    /** Updates the filter of the unfinished filtered task list to filter by the given list name*/
    void updateFilteredTaskListGivenListName(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given list name*/
    void updateFilteredTaskListGivenListNameAll(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the days to due*/
    void updateFilteredTaskListGivenDaysToDueBy(String days);

    /** Updates the filter of the filtered task list to filter on the days to due*/
    void updateFilteredTaskListGivenDaysToDueOn(String days);

    // Tag-level filter

    /** Adds the given list */
    void addList(Tag tag) throws UniqueTagList.DuplicateTagException;

    /** Updates the filter of the filtered tag list to show all tasks */
    void updateFilteredTagListToShowAllTags();

    /** Updates the filter of the filtered tag list to filter by the given keywords*/
    void updateFilteredListList(Set<String> keywords);

    /** Check whether the listname exist*/
    boolean isListExist(Set<String> listNames);

}

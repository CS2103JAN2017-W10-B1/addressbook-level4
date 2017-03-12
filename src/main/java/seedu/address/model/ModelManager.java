package seedu.address.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.DueueChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.tasklist.TaskList;
import seedu.address.model.tasklist.UniqueListList;
import seedu.address.model.tasklist.UniqueListList.DuplicateListException;
import seedu.address.model.tasklist.UniqueListList.ListNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<TaskList> filteredLists;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with address book: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        filteredLists = new FilteredList<>(this.taskManager.getListList());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new DueueChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) {
        try {
            taskManager.addTask(task);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        updateFilteredListToShowAllTasks();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void addList(TaskList list) throws UniqueListList.DuplicateListException {
        assert list != null;
        taskManager.addList(list);
        updateFilteredListToShowAllLists();
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void removeList(TaskList target) throws ListNotFoundException {
        taskManager.removeList(target);
        indicateTaskManagerChanged();
    }
    
    @Override
    public void updateList(int filteredListListIndex, TaskList editedList) throws
            UniqueListList.DuplicateListException {
        assert editedList != null;

        int taskManagerIndex = filteredLists.getSourceIndex(filteredListListIndex);
        taskManager.updateList(taskManagerIndex, editedList);
        indicateTaskManagerChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAllTasks() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //=========== Filtered List List Accessors ==============================================================

    @Override
    public UnmodifiableObservableList<TaskList> getFilteredListList() {
        return new UnmodifiableObservableList<>(filteredLists);
    }
    
    @Override
    public void updateFilteredListToShowAllLists() {
        filteredLists.setPredicate(null);
    }

    @Override
    public void updateFilteredListList(Set<String> keywords) {
        updateFilteredListList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredListList(Expression expression) {
        filteredLists.setPredicate(expression::satisfies);
    }
    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        boolean satisfies(TaskList list);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public boolean satisfies(TaskList list){
            return qualifier.run(list);
        }
        
        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        boolean run(TaskList list);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }
        
        @Override
        public boolean run(TaskList list) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(list.getName(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}

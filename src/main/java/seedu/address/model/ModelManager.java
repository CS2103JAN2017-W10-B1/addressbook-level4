package seedu.address.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.DueueChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<Tag> filteredTag;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with address book: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        filteredTag = new FilteredList<>(this.taskManager.getTagList());
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

    //================== Task Level Operation ===========================================================

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAllUnfinishedTasks();
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

  //================== List Level Operation ===========================================================

    //@@author A0147984L
    @Override
    public void addList(Tag list) throws UniqueTagList.DuplicateTagException {
        assert list != null;

        taskManager.addTag(list);
        indicateTaskManagerChanged();
    }

    //@@author A0143409J
    @Override
    public boolean isListExist(String listName) {
        assert listName != null;

        ObservableList<Tag> tagList = taskManager.getTagList();
        for (Tag tag : tagList) {
            if (tag.getName().toString() == listName) {
                return false;
            }
        }
        return true;
    }

    //@@authorA0147984L

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0147984L
    @Override
    public void updateFilteredListToShowAllUnfinishedTasks() {
        updateFilteredTaskList(new PredicateExpression(new UnfinishedQualifier()));
    }

    @Override
    public void updateFilteredListToShowAllTasks() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredListToShowAllFinishedTasks() {
        updateFilteredTaskList(new PredicateExpression(new FinishedQualifier()));
    }

    @Override
    public void updateFilteredListToShowAllFavoriteTasks() {
        updateFilteredTaskList(new PredicateExpression(new FavoriteQualifier()));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskListAll(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords), new FinishedQualifier()));
    }

    @Override
    public void updateFilteredTaskListGivenListName(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keywords), new UnfinishedQualifier()));
    }

    @Override
    public void updateFilteredTaskListGivenListNameAll(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListGivenDaysToDueBy(String days) {
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(days), new UnfinishedQualifier()));
    }

    @Override
    public void updateFilteredTaskListGivenDaysToDueOn(String days) {
        updateFilteredTaskList(new PredicateExpression(new DateQualifierOn(days), new UnfinishedQualifier()));
    }
    //@@author

    //=========== Filtered List Accessors =============================================================
    //@@author A0147984L
    public UnmodifiableObservableList<Tag> getFilteredTagList() {
        return new UnmodifiableObservableList<>(filteredTag);
    }

    public void updateFilteredTagListToShowAllTags() {
        filteredTag.setPredicate(null);
    }

    public void updateFilteredListList(Set<String> keywords) {
        updateFilteredListList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredListList(Expression expression) {
        filteredTag.setPredicate(expression::satisfies);
    }
    //@@author

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        boolean satisfies(Tag list);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final HashSet<Qualifier> qualifiers;

        PredicateExpression(Qualifier...qualifiers) {
            this.qualifiers = new HashSet<>();
            for (Qualifier qualifier : qualifiers) {
                this.qualifiers.add(qualifier);
            }
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifiers.stream().
                    filter(qualifier -> qualifier.run(task)).count()
                    == this.qualifiers.size();
        }

        @Override
        public boolean satisfies(Tag list) {
            return qualifiers.stream().
                    filter(qualifier -> qualifier.run(list)).count()
                    == this.qualifiers.size();
        }

        @Override
        public String toString() {
            String returnString = "";
            for (Qualifier qualifier : this.qualifiers) {
                returnString += qualifier.toString();
            }
            return returnString;
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        boolean run(Tag list);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        protected Set<String> nameKeyWords;

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
        public boolean run(Tag list) {
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

    //@@author A0147984L
    private class UnfinishedQualifier implements Qualifier {

        UnfinishedQualifier() {}

        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.isFinished();
        }

        @Override
        public boolean run(Tag list) {
            return false;
        }

        @Override
        public String toString() {
            return "name=" + "unfinished";
        }
    }

    //@@ author A0147996E
    private class FinishedQualifier implements Qualifier {

        FinishedQualifier() {}

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isFinished();
        }

        @Override
        public boolean run(Tag list) {
            return false;
        }

        @Override
        public String toString() {
            return "name=" + "finished";
        }
    }

    private class FavoriteQualifier implements Qualifier {

        FavoriteQualifier() {}

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isFavorite();
        }

        @Override
        public boolean run(Tag list) {
            return false;
        }

        @Override
        public String toString() {
            return "name=" + "favorite";
        }
    }
//@@ author

    private class TagQualifier implements Qualifier {
        protected Set<String> tagKeyWords;

        TagQualifier(Set<String> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return tagKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTag().getName(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public boolean run(Tag list) {
            return false;
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", tagKeyWords);
        }
    }

    private class DateQualifier implements Qualifier {
        protected int daysToDue;
        protected Calendar today;

        DateQualifier(String days) {
            this.daysToDue = Integer.parseInt(days);
            today = Calendar.getInstance();
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            long diff = task.getDate().date.getTime() - today.getTime().getTime();
            return (daysToDue >= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
                    &&
                    (0 <= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        }

        @Override
        public boolean run(Tag list) {
            return false;
        }

        @Override
        public String toString() {
            return "daysToDue=" + daysToDue;
        }
    }

    private class DateQualifierOn extends DateQualifier implements Qualifier {
        protected int daysToDue;
        protected Calendar today;

        DateQualifierOn(String days) {
            super(days);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            long diff = task.getDate().date.getTime() - today.getTime().getTime();
            return daysToDue == TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }
    }
}

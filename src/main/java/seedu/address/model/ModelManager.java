package seedu.address.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.DueueChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
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
        this(TaskManager.getInstance(), new UserPrefs());
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

    //@@author A0147984L
    @Override
    public void finishTaskOnce(ReadOnlyTask recurringTask)
            throws DuplicateTaskException {
        assert recurringTask != null;

        taskManager.finishTaskOnce(recurringTask);
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTaskOnOccurance(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTaskOnce(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }
  //@@author

  //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0147984L
    @Override
    public void updateFilteredTaskList(Set<String> nameKeywords, Set<String> tagKeywords,
            FinishedState finishedState, boolean isFavorite,
            DueMode dueMode, String days) {
        NameQualifier nameQualifier = nameKeywords == null ? null : new NameQualifier(nameKeywords);
        TagQualifier tagQualifier = tagKeywords == null ? null : new TagQualifier(tagKeywords);
        FinishedQualifier finishedQualifier = new FinishedQualifier(finishedState);
        FavoriteQualifier favoriteQualifier = isFavorite ? new FavoriteQualifier() : null;
        DateQualifier dateQualifier;
        if (dueMode == null) {
            dateQualifier = null;
        } else if (dueMode.equals(DueMode.BY)) {
            dateQualifier = new DateQualifierBy(days);
        } else if (dueMode.equals(DueMode.ON)) {
            dateQualifier = new DateQualifierOn(days);
        } else {
            dateQualifier = null;
        }
        updateFilteredTaskList(new PredicateExpression(
                nameQualifier, tagQualifier, finishedQualifier, favoriteQualifier, dateQualifier));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(keywords, null, FinishedState.UNFINISHED, false, null, null);
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredListToShowAllUnfinishedTasks() {
        updateFilteredTaskList(null, null, FinishedState.UNFINISHED, false, null, null);
    }

    @Override
    public void updateFilteredListToShowAllTasks() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredListToShowAllFinishedTasks() {
        updateFilteredTaskList(null, null, FinishedState.FINISHED, false, null, null);
    }

    @Override
    public void updateFilteredListToShowAllFavoriteTasks() {
        updateFilteredTaskList(null, null, FinishedState.ALL, true, null, null);
    }

    @Override
    public void updateFilteredTaskListFinished(Set<String> keywords) {
        updateFilteredTaskList(keywords, null, FinishedState.FINISHED, false, null, null);
    }

    @Override
    public void updateFilteredTaskListAll(Set<String> keywords) {
        updateFilteredTaskList(keywords, null, FinishedState.ALL, false, null, null);
    }

    @Override
    public void updateFilteredTaskListFavorite(Set<String> keywords) {
        updateFilteredTaskList(keywords, null, FinishedState.UNFINISHED, true, null, null);
    }

    @Override
    public void updateFilteredTaskListGivenListName(Set<String> keywords) {
        updateFilteredTaskList(null, keywords, FinishedState.UNFINISHED, false, null, null);
    }

    @Override
    public void updateFilteredTaskListGivenListNameAll(Set<String> keywords) {
        updateFilteredTaskList(null, keywords, FinishedState.ALL, false, null, null);
    }

    @Override
    public void updateFilteredTaskListGivenListNameFinished(Set<String> keywords) {
        updateFilteredTaskList(null, keywords, FinishedState.FINISHED, false, null, null);
    }

    @Override
    public void updateFilteredTaskListGivenListNameAllFavorite(Set<String> keywords) {
        updateFilteredTaskList(null, keywords, FinishedState.ALL, true, null, null);
    }

    @Override
    public void updateFilteredTaskListGivenDaysToDueBy(String days) {
        updateFilteredTaskList(null, null, FinishedState.UNFINISHED, false, DueMode.BY, days);
    }

    @Override
    public void updateFilteredTaskListGivenDaysToDueOn(String days) {
        updateFilteredTaskList(null, null, FinishedState.UNFINISHED, false, DueMode.ON, days);
    }

    //=========== Filtered List Accessors =============================================================
    //@@author A0147984L
    public UnmodifiableObservableList<Tag> getFilteredTagList() {
        return new UnmodifiableObservableList<>(filteredTag);
    }

    public void updateFilteredTagListToShowAllTags() {
        filteredTag.setPredicate(null);
    }

    private void updateFilteredTagList(Expression expression) {
        filteredTag.setPredicate(expression::satisfies);
    }
    //@@author

    //@@author A0143409J
    @Override
    public boolean isListExist(Set<String> listNames) {
        updateFilteredTagList(new PredicateExpression(new NameQualifier(listNames)));
        boolean isListExist = filteredTag.size() > 0;
        updateFilteredTagListToShowAllTags();
        return isListExist;
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        boolean satisfies(Tag list);
        String toString();
    }

    //@@author A0147984L
    private class PredicateExpression implements Expression {

        private final HashSet<Qualifier> qualifiers;

        PredicateExpression(Qualifier...qualifiers) {
            this.qualifiers = new HashSet<>();
            for (Qualifier qualifier : qualifiers) {
                if (qualifier != null) {
                    this.qualifiers.add(qualifier);
                }
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
    //@@author

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
    private class FinishedQualifier implements Qualifier {
        protected FinishedState state;

        FinishedQualifier(FinishedState state) {
            this.state = state;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (state.equals(FinishedState.FINISHED)) {
                return task.isFinished();
            } else if (state.equals(FinishedState.UNFINISHED)) {
                return !task.isFinished();
            } else {
                return true;
            }
        }

//@@author A0147996E
        @Override
        public boolean run(Tag list) {
            return false;
        }

        @Override
        public String toString() {
            if (state.equals(FinishedState.FINISHED)) {
                return "name=" + " finished";
            } else if (state.equals(FinishedState.UNFINISHED)) {
                return "name=" + " unfinished";
            } else {
                return "name=" + " all";
            }
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
//@@author A0147984L

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

    private abstract class DateQualifier implements Qualifier {
        protected int daysToDue;
        protected Calendar today;

        DateQualifier(String days) {
            this.daysToDue = Integer.parseInt(days);
            today = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
        }

        @Override
        public abstract boolean run(ReadOnlyTask task);

        @Override
        public boolean run(Tag list) {
            return false;
        }

        @Override
        public String toString() {
            return "daysToDue=" + daysToDue;
        }
    }

    private class DateQualifierOn extends DateQualifier {

        DateQualifierOn(String days) {
            super(days);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            long diff = task.getDate().date.getTime() - today.getTime().getTime();
            return daysToDue == TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }
    }

    private class DateQualifierBy extends DateQualifier {

        DateQualifierBy(String days) {
            super(days);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            long diff = task.getDate().date.getTime() - today.getTime().getTime();
            return (daysToDue >= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
                    &&
                    (0 <= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        }
    }

}

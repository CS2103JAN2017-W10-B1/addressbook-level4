//@@author A0147984L
package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.testutil.TypicalTestTasks;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();
    private final TypicalTestTasks testUtil = new TypicalTestTasks();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
        assertEquals(Collections.emptyList(), taskManager.getTagList());
    }

    @Test
    public void resetDataNullThrowsAssertionError() {
        thrown.expect(AssertionError.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetDataWithValidReadOnlyTaskManagerReplacesData() {
        TaskManager newData = new TypicalTestTasks().getTypicalTaskManager();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void emptyManager() {
        TaskManager emptyManager = new TaskManager();
        assertEquals(emptyManager.getTagList().size(), 0);
    }

    @Test
    public void addTask() throws IllegalValueException {
        TaskManager newManager = new TaskManager();
        Task gym = new Task(testUtil.gym);
        Task cs2103 = new Task(testUtil.cs2103);
        Task birthday = new Task(testUtil.birthday);

        newManager.addTask(gym);
        assertEquals(newManager.getTaskList().size(), 1);
        assertEquals(newManager.getTagList().size(), 1);
        assertEquals(newManager.getTaskList().get(0), gym);

        newManager.addTask(birthday);
        assertEquals(newManager.getTaskList().size(), 2);
        assertEquals(newManager.getTagList().size(), 1);

        newManager.addTask(cs2103);
        assertEquals(newManager.getTaskList().size(), 3);
        assertEquals(newManager.getTagList().size(), 2);
    }

    @Test
    public void deleteTask() throws IllegalValueException, TaskNotFoundException {
        TaskManager newManager = new TaskManager();
        Task gym = new Task(testUtil.gym);
        Task cs2103 = new Task(testUtil.cs2103);
        Task birthday = new Task(testUtil.birthday);

        newManager.addTask(gym);
        newManager.addTask(cs2103);
        newManager.addTask(birthday);
        newManager.removeTask(gym);
        assertEquals(newManager.getTaskList().size(), 2);

        newManager.removeTask(birthday);
        assertEquals(newManager.getTaskList().size(), 1);
    }

    @Test
    public void udpateTask() throws IllegalValueException {
        TaskManager newManager = new TaskManager();
        Task gym = new Task(testUtil.gym);
        Task cs2103 = new Task(testUtil.cs2103);
        Task birthday = new Task(testUtil.birthday);
        ReadOnlyTask date = new Task(testUtil.date);
        ReadOnlyTask familyDinner = new Task(testUtil.familyDinner);

        newManager.addTask(gym);
        newManager.addTask(cs2103);
        newManager.addTask(birthday);
        newManager.updateTask(1, date);
        assertEquals(newManager.getTaskList().size(), 3);
        assertEquals(newManager.getTagList().size(), 2);

        newManager.updateTask(1, familyDinner);
        assertEquals(newManager.getTaskList().size(), 3);
        assertEquals(newManager.getTagList().size(), 3);
    }

    //@@author
    @Test
    public void resetDataWithDuplicateTasksThrowsAssertionError() throws DuplicateTagException {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.gym), new Task(td.gym));
        UniqueTagList newTags = new UniqueTagList();
        newTags.add(td.gym.getTag());
        TaskManagerStub newData = new TaskManagerStub(newTasks, newTags.asObservableList());

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    @Test
    public void resetDataWithDuplicateTagsThrowsAssertionError() {
        TaskManager typicalTaskManager = new TypicalTestTasks().getTypicalTaskManager();
        List<ReadOnlyTask> newTasks = typicalTaskManager.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalTaskManager.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TaskManagerStub newData = new TaskManagerStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks and tags lists can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TaskManagerStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }
}
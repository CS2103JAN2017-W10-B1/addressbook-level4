//@@author A0147984L
package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.testutil.TypicalTestTasks;

public class TaskManagerTest {

    private static TaskManager taskManager;
    private static TypicalTestTasks testUtil;
    private static Task gym;
    private static Task cs2103;
    private static Task assignment;
    private static ReadOnlyTask date;
    private static ReadOnlyTask familyDinner;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void oneTimeSetup() {
        taskManager = TaskManager.getStub();
        testUtil = new TypicalTestTasks();
        gym = new Task(testUtil.gym);
        cs2103 = new Task(testUtil.cs2103);
        assignment = new Task(testUtil.assignment);
        date = new Task(testUtil.date);
        familyDinner = new Task(testUtil.familyDinner);
    }

    @Before
    public void setup() {
        taskManager = TaskManager.getStub();
    }

    @Test
    public void constructor_defaultConstructor_emptyTaskManagerConstructed() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
        assertEquals(Collections.emptyList(), taskManager.getTagList());
    }

    @Test
    public void resetData_null_exceptionThrown() {
        thrown.expect(AssertionError.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetData_validReadOnlyTaskManager_dataReset() {
        TaskManager newData = new TypicalTestTasks().getTypicalTaskManager();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void addTask_validTask_taskAdded() throws DuplicateTaskException {

        taskManager.addTask(gym);
        assertEquals(taskManager.getTaskList().size(), 1);
        assertEquals(taskManager.getTagList().size(), 1);
        assertEquals(taskManager.getTaskList().get(0), gym);

        taskManager.addTask(assignment);
        assertEquals(taskManager.getTaskList().size(), 2);
        assertEquals(taskManager.getTagList().size(), 2);

        taskManager.addTask(cs2103);
        assertEquals(taskManager.getTaskList().size(), 3);
        assertEquals(taskManager.getTagList().size(), 3);
    }

    @Test
    public void removeTask_validTask_taskRemoved() throws TaskNotFoundException, DuplicateTaskException {

        taskManager.addTask(gym);
        taskManager.addTask(cs2103);
        taskManager.addTask(assignment);
        taskManager.removeTask(gym);
        assertEquals(taskManager.getTaskList().size(), 2);

        taskManager.removeTask(assignment);
        assertEquals(taskManager.getTaskList().size(), 1);
    }

    @Test
    public void removeTask_nonExistingTask_exceptionThrown() throws DuplicateTaskException, TaskNotFoundException {
        thrown.expect(TaskNotFoundException.class);

        taskManager.addTask(gym);
        taskManager.addTask(cs2103);
        taskManager.removeTask(assignment);
    }

    @Test
    public void updateTask_validIndex_taskUpdated() throws IllegalValueException {

        taskManager.addTask(gym);
        taskManager.addTask(cs2103);
        taskManager.addTask(assignment);
        taskManager.updateTask(1, date);
        assertEquals(taskManager.getTaskList().size(), 3);
        assertEquals(taskManager.getTagList().size(), 3);

        taskManager.updateTask(1, familyDinner);
        assertEquals(taskManager.getTaskList().size(), 3);
        assertEquals(taskManager.getTagList().size(), 3);
    }

    //@@author
    @Test
    public void resetData_duplicateTasks_assertionErrorThrown() throws DuplicateTagException {

        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(gym, gym);
        UniqueTagList newTags = new UniqueTagList();
        newTags.add(gym.getTag());
        TaskManagerStub newData = new TaskManagerStub(newTasks, newTags.asObservableList());

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    @Test
    public void resetData_duplicateTags_assertionErrorThrown() {
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

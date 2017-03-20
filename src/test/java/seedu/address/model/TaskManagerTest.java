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
import seedu.address.model.tasklist.TaskList;
import seedu.address.testutil.TypicalTestTasks;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();

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
        Task sample = new Task(new Name("sampleName"), null, null, null, new Tag("sampleTag"), null, null, false);
        newManager.addTask(sample);
        assertEquals(newManager.getTaskList().size(), 1);
        assertEquals(newManager.getTagList().size(), 1);
        assertEquals(newManager.getTaskList().get(0), sample);
        Task sample2 = new Task(new Name("2"), null, null, null, new Tag("sampleTag"), null, null, false);
        newManager.addTask(sample2);
        assertEquals(newManager.getTaskList().size(), 2);
        assertEquals(newManager.getTagList().size(), 1);
        Task sample3 = new Task(new Name("3"), null, null, null, new Tag("sampleTag2"), null, null, false);
        newManager.addTask(sample3);
        assertEquals(newManager.getTaskList().size(), 3);
        assertEquals(newManager.getTagList().size(), 2);
    }

    @Test
    public void deleteTask() throws IllegalValueException, TaskNotFoundException {
        TaskManager newManager = new TaskManager();
        Task sample1 = new Task(new Name("sampleName1"), null, null, null, new Tag("sampleTag1"), null, null, false);
        Task sample2 = new Task(new Name("sampleName2"), null, null, null, new Tag("sampleTag1"), null, null, false);
        Task sample3 = new Task(new Name("sampleName3"), null, null, null, new Tag("sampleTag3"), null, null, false);
        newManager.addTask(sample1);
        newManager.addTask(sample2);
        newManager.addTask(sample3);
        newManager.removeTask(sample1);
        assertEquals(newManager.getTaskList().size(), 2);
        newManager.removeTask(sample3);
        assertEquals(newManager.getTaskList().size(), 1);
    }

    @Test
    public void udpateTask() throws IllegalValueException {
        TaskManager newManager = new TaskManager();
        Task sample1 = new Task(new Name("sampleName1"), null, null, null, new Tag("sampleTag1"), null, null, false);
        Task sample2 = new Task(new Name("sampleName2"), null, null, null, new Tag("sampleTag1"), null, null, false);
        Task sample3 = new Task(new Name("sampleName3"), null, null, null, new Tag("sampleTag3"), null, null, false);
        ReadOnlyTask sample4 = new Task(new Name("sampleName4"), null, null, null, new Tag(
                "sampleTag3"), null, null, false);
        ReadOnlyTask sample5 = new Task(new Name("sampleName5"), null, null, null, new Tag(
                "sampleTag5"), null, null, false);
        newManager.addTask(sample1);
        newManager.addTask(sample2);
        newManager.addTask(sample3);
        newManager.updateTask(1, sample4);
        assertEquals(newManager.getTaskList().size(), 3);
        assertEquals(newManager.getTagList().size(), 2);
        newManager.updateTask(1, sample5);
        assertEquals(newManager.getTaskList().size(), 3);
        assertEquals(newManager.getTagList().size(), 3);
    }

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
        private final ObservableList<TaskList> lists = FXCollections.observableArrayList();

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

        @Override
        public ObservableList<TaskList> getList() {
            return lists;
        }
    }

}

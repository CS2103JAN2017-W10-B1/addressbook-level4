package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TypicalTestTasks;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class ModelManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();
    private final TypicalTestTasks testUtil = new TypicalTestTasks();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), modelManager.getFilteredTaskList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        modelManager.resetData(null);
    }

    @Test
    public void resetData() {
        modelManager.resetData(testUtil.getTypicalTaskManager());
        assertEquals(modelManager.getTaskManager(), testUtil.getTypicalTaskManager());
        assertEquals(modelManager.getFilteredTaskList().size(), 3);
        assertEquals(modelManager.getFilteredListList().size(), 2);
    }

    @Test
    public void testFilterName() {
        modelManager.resetData(testUtil.getTypicalTaskManager());
        Set<String> keywords = new HashSet<String>();
        keywords.add("Gym");
        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 1);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "gym");

        modelManager.updateFilteredListToShowAllTasks();
        assertEquals(modelManager.getFilteredTaskList().size(), 3);

        keywords.add("cs2103");
        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 2);

        modelManager = new ModelManager();
        modelManager.resetData(testUtil.getTypicalTaskManager());
        keywords.add("cs2103");
        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 2);
    }

    @Test
    public void testFilterTag() {
        modelManager.resetData(testUtil.getTypicalTaskManager());
        assertEquals(modelManager.getFilteredTaskList().size(), 3);
        Set<String> keywords = new HashSet<String>();
        keywords.add("personal");
        modelManager.updateFilteredTaskListGivenListName(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 2);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "gym");

        modelManager = new ModelManager();
        modelManager.resetData(testUtil.getTypicalTaskManager());
        keywords.clear();
        keywords.add("School");
        modelManager.updateFilteredTaskListGivenListName(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 1);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "cs2103");

        modelManager = new ModelManager();
        modelManager.resetData(testUtil.getTypicalTaskManager());
        keywords.clear();
        keywords.add("inbox");
        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 0);
    }

    @Test
    public void addTask() throws DuplicateTaskException {
        modelManager = new ModelManager();
        Task task1 = new Task(testUtil.gym);
        Task task2 = new Task(testUtil.cs2103);
        Task task3 = new Task(testUtil.study);

        modelManager.addTask(task1);
        assertEquals(modelManager.getFilteredTaskList().size(), 1);
        assertEquals(modelManager.getFilteredListList().size(), 1);

        //TODO: add testcases for task2

        modelManager.addTask(task3);
        assertEquals(modelManager.getFilteredTaskList().size(), 2);
        assertEquals(modelManager.getFilteredListList().size(), 1);
    }
}

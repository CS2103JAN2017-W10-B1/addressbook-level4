//@@author A0147984L
package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.testutil.TypicalTestTasks;

public class ModelManagerTest {


    private static ModelManager modelManager;
    private static TypicalTestTasks testUtil;
    private static Task task1;
    private static Task task2;
    private static Task task3;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void oneTimeSetup() {
        modelManager = new ModelManager();
        testUtil = new TypicalTestTasks();
        task1 = new Task(testUtil.gym);
        task2 = new Task(testUtil.cs2103);
        task3 = new Task(testUtil.study);
    }

    @After
    public void tearDown() {
        modelManager = new ModelManager();
    }

    @Test
    public void constructor_defaultConstructor_emptyModelManagerConstructed() {
        assertEquals(Collections.emptyList(), modelManager.getFilteredTaskList());
    }

    @Test
    public void resetData_null_exceptionThrown() {
        thrown.expect(AssertionError.class);
        modelManager.resetData(null);
    }

    @Test
    public void resetData_validTaskManager_dataReset() {
        modelManager.resetData(testUtil.getTypicalTaskManager());
        assertEquals(modelManager.getTaskManager(), testUtil.getTypicalTaskManager());
        assertEquals(modelManager.getFilteredTaskList().size(), 7);
        assertEquals(modelManager.getFilteredTagList().size(), 3);
    }

    @Test
    public void updateFilteredTaskList_keywordsOfName_filterTaskListReturned() {
        modelManager.resetData(testUtil.getTypicalTaskManager());
        Set<String> keywords = new HashSet<String>();
        keywords.add("Gym");

        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 3);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "gym");

        modelManager.updateFilteredListToShowAllUnfinishedTasks();
        assertEquals(modelManager.getFilteredTaskList().size(), 7);

        keywords.add("cs2103");
        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 4);

        modelManager.updateFilteredTaskListFinished(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 0);

        modelManager.updateFilteredTaskListAll(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 4);

        modelManager.updateFilteredTaskListFavorite(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 2);
    }

    @Test
    public void updateFilteredTaskListGivenDaysToDue_daysToDue_filteredTaskListReturned() {
        modelManager.resetData(testUtil.getTypicalTaskManager());

        assertEquals(modelManager.getFilteredTaskList().size(), 7);

        modelManager.updateFilteredTaskListGivenDaysToDueBy("0");
        assertEquals(modelManager.getFilteredTaskList().size(), 0);

        modelManager.updateFilteredTaskListGivenDaysToDueOn("0");
        assertEquals(modelManager.getFilteredTaskList().size(), 0);

        modelManager.updateFilteredTaskListGivenDaysToDueBy("300");
        assertEquals(modelManager.getFilteredTaskList().size(), 4);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "assignment");

        modelManager.updateFilteredTaskListGivenDaysToDueBy("365");
        assertEquals(modelManager.getFilteredTaskList().size(), 7);

        modelManager.updateFilteredTaskListGivenDaysToDueBy("700");
        assertEquals(modelManager.getFilteredTaskList().size(), 7);
    }

    @Test
    public void updateFilteredTaskListGivenListName_keywordsOfList_filteredTaskListReturned() {
        modelManager.resetData(testUtil.getTypicalTaskManager());
        Set<String> keywords = new HashSet<String>();
        keywords.add("personal");

        modelManager.updateFilteredTaskListGivenListName(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 4);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "gym");

        modelManager.updateFilteredTaskListGivenListNameAll(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 4);

        keywords.clear();
        keywords.add("School");

        modelManager.updateFilteredTaskListGivenListName(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 2);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "cs2103");

        keywords.clear();
        keywords.add("Inbox");

        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 0);
    }

    @Test
    public void addTask_validTask_taskAdded() throws DuplicateTaskException {

        modelManager.addTask(task1);
        assertEquals(modelManager.getFilteredTaskList().size(), 1);
        assertEquals(modelManager.getFilteredTagList().size(), 1);

        modelManager.addTask(task2);
        assertEquals(modelManager.getFilteredTaskList().size(), 2);
        assertEquals(modelManager.getFilteredTagList().size(), 2);

        modelManager.addTask(task3);
        assertEquals(modelManager.getFilteredTaskList().size(), 3);
        assertEquals(modelManager.getFilteredTagList().size(), 2);
    }
}

//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.testutil.TypicalTestEvents;
import seedu.address.testutil.TypicalTestTasks;

public class UniqueTaskListTest {

    private static UniqueTaskList tester;
    private static TypicalTestTasks testUtil;
    private static Task shoppingTask;
    private static Task familyDinnerTask;
    private static Task meetingTask;
    private static Task travelTask;
    private static Task testEvent;

    @BeforeClass
    public static void oneTimeSetup() throws IllegalValueException {

        tester = new UniqueTaskList();
        testUtil = new TypicalTestTasks();
        shoppingTask = new Task(testUtil.shopping);
        familyDinnerTask = new Task(testUtil.familyDinner);
        meetingTask = new Task(testUtil.meeting);
        travelTask = new Task(testUtil.travel);
        testEvent = new Event(new TypicalTestEvents().gym);
    }

    @AfterClass
    public static void oneTimeTearDown() {
        tester = new UniqueTaskList();
    }

    @Before
    public void setup() throws DuplicateTaskException {

        tester.add(familyDinnerTask);
        tester.add(meetingTask);
        tester.add(travelTask);
        tester.add(shoppingTask);
    }

    @After
    public void tearDown() {
        tester = new UniqueTaskList();
    }

    @Test
    public void sort_validTasks_taskListSorted() {

        tester.sort();

        assertEquals(tester.get(0).getName().fullName, "meeting");
        assertEquals(tester.get(1).getName().fullName, "family dinner");
        assertEquals(tester.get(2).getName().fullName, "shopping");
        assertEquals(tester.get(3).getName().fullName, "travel");
    }

    @Test
    public void delete_deleteShoppingTask_shoppingTaskDeleted() throws DuplicateTaskException {

        tester.delete(shoppingTask);

        tester.sort();

        assertEquals(tester.get(0).getName().fullName, "meeting");
        assertEquals(tester.get(1).getName().fullName, "family dinner");
        assertEquals(tester.get(2).getName().fullName, "travel");
    }

    @Test
    public void update_updateTaskOneWithShoppingTask_taskOneUpdated() throws DuplicateTaskException {

        tester.delete(shoppingTask);
        tester.sort();

        tester.updateTask(1, shoppingTask);

        assertEquals(tester.get(0).getName().fullName, "meeting");
        assertEquals(tester.get(1).getName().fullName, "shopping");
        assertEquals(tester.get(2).getName().fullName, "travel");
    }

    @Test
    public void update_updateTaskOneWithTestEvent_TaskOneUpdated() throws IllegalValueException {

        tester.sort();
        tester.updateTask(1, testEvent);

        assertEquals(tester.get(0).getName().fullName, "meeting");
        assertEquals(tester.get(1).getName().fullName, "gym");
        assertEquals(tester.get(2).getName().fullName, "shopping");
    }
}

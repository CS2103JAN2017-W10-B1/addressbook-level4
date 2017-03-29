//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.testutil.TypicalTestTasks;

public class UniqueTaskListTest {

    private UniqueTaskList tester = new UniqueTaskList();
    private final TypicalTestTasks testUtil = new TypicalTestTasks();

    @Test
    public void sort() throws DuplicateTaskException {

        tester.add(new Task(testUtil.familyDinner));
        tester.add(new Task(testUtil.meeting));
        tester.add(new Task(testUtil.travel));
        tester.add(new Task(testUtil.shopping));

        tester.sort();

        assertEquals(tester.get(0).getName().fullName, "meeting");
        assertEquals(tester.get(1).getName().fullName, "family dinner");
        assertEquals(tester.get(2).getName().fullName, "shopping");
        assertEquals(tester.get(3).getName().fullName, "travel");
    }

    @Test
    public void deleteTest() throws DuplicateTaskException {

        Task shoppingTask = new Task(testUtil.shopping);

        tester.add(shoppingTask);
        tester.add(new Task(testUtil.familyDinner));
        tester.add(new Task(testUtil.meeting));
        tester.add(new Task(testUtil.travel));
        tester.delete(shoppingTask);

        tester.sort();

        assertEquals(tester.get(0).getName().fullName, "meeting");
        assertEquals(tester.get(1).getName().fullName, "family dinner");
        assertEquals(tester.get(2).getName().fullName, "travel");
    }

    @Test
    public void updateValidTest() throws DuplicateTaskException {

        Task shoppingTask = new Task(testUtil.shopping);

        tester.add(new Task(testUtil.familyDinner));
        tester.add(new Task(testUtil.meeting));
        tester.add(new Task(testUtil.travel));
        tester.sort();
        tester.updateTask(1, shoppingTask);

        assertEquals(tester.get(0).getName().fullName, "meeting");
        assertEquals(tester.get(1).getName().fullName, "shopping");
        assertEquals(tester.get(2).getName().fullName, "travel");
    }

    @Test
    public void updateValidEventTest() throws IllegalValueException {
        Event testEvent = new Event(new Name("test"), new TaskDate("15/4"), new TaskTime("20:00"),
                new TaskDate("15/4"), new TaskTime("21:00"), null, null, null, null, false);

        tester.add(new Task(testUtil.familyDinner));
        tester.add(new Task(testUtil.meeting));
        tester.add(new Task(testUtil.travel));
        tester.sort();
        tester.updateTask(1, testEvent);

        assertEquals(tester.get(0).getName().fullName, "meeting");
        assertEquals(tester.get(1).getName().fullName, "test");
        assertEquals(tester.get(2).getName().fullName, "travel");
    }
}

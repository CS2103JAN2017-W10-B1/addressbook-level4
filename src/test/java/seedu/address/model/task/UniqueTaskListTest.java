//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

        assertEquals(tester.get(0).getName().fullName, "family dinner");
        assertEquals(tester.get(1).getName().fullName, "shopping");
        assertEquals(tester.get(2).getName().fullName, "travel");
        assertEquals(tester.get(3).getName().fullName, "meeting");
    }
}

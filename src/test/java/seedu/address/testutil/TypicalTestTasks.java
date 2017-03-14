package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask gym, cs2103, study;
    public TestTask birthday;
    public TestTask assignment;

    public TypicalTestTasks() {
        try {
            gym = new TaskBuilder().withName("gym").withDescription("50min workout").withTag("personal").
                    withPriority("2").withFavorite(true).build();
            cs2103 = new TaskBuilder().withName("cs2103").withTime("16:00").withTag("School").withFavorite(true).build();
            study = new TaskBuilder().withName("study").withTag("personal").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{gym, cs2103, study};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

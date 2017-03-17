package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

//@@author Matilda_yxx A0147996E
public class TypicalTestTasks {
    public TestTask gym, cs2103, study, laundry, birthday, assignment, date, meeting;

    public TypicalTestTasks() {
        try {
            gym = new TaskBuilder().withName("gym").withDate("20/12").withTime("20:00").withDescription("50min workout").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(true).build();
            cs2103 = new TaskBuilder().withName("cs2103").withDate("").withTime("16:00").withDescription("").
                    withTag("school").withVenue("").withPriority("1").withFavorite(true).build();
            study = new TaskBuilder().withName("study").withDate("").withTime("").withDescription("").
                    withTag("").withVenue("Central lib").withPriority("").withFavorite(false).build();
            laundry = new TaskBuilder().withName("laundry").withDate("").withTime("").withDescription("Weekly task").
                    withTag("personal").withVenue("").withPriority("").withFavorite(false).build();
            birthday = new TaskBuilder().withName("birthday").withDate("04/09").withTime("14:00").withDescription("").
                    withTag("").withVenue("").withPriority("3").withFavorite(true).build();
            assignment = new TaskBuilder().withName("assignment").withDate("").withTime("").withDescription("").
                    withTag("study").withVenue("Utown").withPriority("2").withFavorite(false).build();
            date  = new TaskBuilder().withName("date").withDate("14/02").withTime("12:00").withDescription("Most important day").
                    withTag("personal").withVenue("Gardens by the bay").withPriority("3").withFavorite(true).build();
            meeting = new TaskBuilder().withName("meeting").withDate("").withTime("").withDescription("").
                    withTag("school").withVenue("PGP").withPriority("").withFavorite(false).build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                tm.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{gym, cs2103, study, laundry, birthday};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}

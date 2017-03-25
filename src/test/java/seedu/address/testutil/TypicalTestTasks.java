//@@author Matilda_yxx A0147996E
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

public class TypicalTestTasks {
    public TestTask gym, cs2103, study, laundry, birthday, assignment, date, date2, date3, date4, meeting,
        familyDinner, travel, shopping;

    public TypicalTestTasks() {
        try {
            gym = new TaskBuilder().withName("gym").withDate("20/12/2018").withTime(
                    "20:00").withDescription("50min workout").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(true).build();
            cs2103 = new TaskBuilder().withName("cs2103").withDate("01/01").withTime(
                    "16:00").withDescription("Crazy but useful mod").
                    withTag("school").withVenue("icube").withPriority("1").withFavorite(true).build();
            study = new TaskBuilder().withName("study").withDate("31/12").withTime(
                    "15:34").withDescription("Study for MTE").
                    withTag("school").withVenue("Central lib").withPriority("2").withFavorite(false).build();
            laundry = new TaskBuilder().withName("laundry").withDate("03/04").withTime(
                    "13:00").withDescription("Weekly task").
                    withTag("personal").withVenue("home").withPriority("2").withFavorite(false).build();
            birthday = new TaskBuilder().withName("birthday").withDate("04/09").withTime(
                    "14:00").withDescription("Bf's birthday").
                    withTag("personal").withVenue("home").withPriority("3").withFavorite(true).build();
            assignment = new TaskBuilder().withName("assignment").withDate("10/12/2018").withTime(
                    "10:00").withDescription("IE2150").
                    withTag("study").withVenue("Utown").withPriority("2").withFavorite(true).build();
            //The test tasks below are for duplicate task testing
            date  = new TaskBuilder().withName("date").withDate("14/02/2020").withTime(
                    "12:00").withDescription("Most important day").
                    withTag("personal").withVenue("Gardens by the bay").withPriority("3").withFavorite(false).build();
            date2  = new TaskBuilder().withName("date").withDate("14/02/2020").withTime(
                    "12:00").withDescription("Most important day").
                    withTag("private").withVenue("Gardens by the bay").withPriority("3").withFavorite(false).build();
            date3  = new TaskBuilder().withName("date").withDate("15/02/2020").withTime(
                    "12:00").withDescription("Most important day").
                    withTag("personal").withVenue("Gardens by the bay").withPriority("3").withFavorite(false).build();
            date4  = new TaskBuilder().withName("date").withDate("14/02/2020").withTime(
                    "13:00").withDescription("Most important day").
                    withTag("personal").withVenue("Gardens by the bay").withPriority("3").withFavorite(false).build();
          //The test tasks above are for duplicate task testing
            meeting = new TaskBuilder().withName("meeting").withDate("01/01").withTime(
                    "12:00").withDescription("Meeting old friends").
                    withTag("school").withVenue("PGP").withPriority("2").withFavorite(false).build();
            familyDinner = new TaskBuilder().withName("family dinner").withDate("1/1").withTime(
                    "20:00").withDescription("Meeting families").
                    withTag("family").withVenue("home").withPriority("important").withFavorite(true).build();
            travel = new TaskBuilder().withName("travel").withDate("1/01").withTime(
                    "21:00").withDescription("To Africa").
                    withTag("personal").withVenue("Africa").withPriority("important").withFavorite(true).build();
            shopping = new TaskBuilder().withName("shopping").withDate("1/01").withTime(
                    "21:00").withDescription("Shopping in Airport").
                    withTag("personal").withVenue("Airport").withPriority("3").withFavorite(true).build();
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
        return new TestTask[]{gym, cs2103};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}

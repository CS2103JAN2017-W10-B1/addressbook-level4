//@@author A0147996E
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

public class TypicalTestTasks {
    public TestTask gym, gym2, gym3, cs2103, study, assignment, date, date2, date3, date4, meeting,
        familyDinner, travel, shopping;

    public TypicalTestTasks() {
        try {
            gym = new TaskBuilder().withName("gym").withDate("20/12/2018").withTime(
                    "20:00").withDescription("50min workout").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(true).build();
            //gym2 and gym3 are built for findCommandTest
            gym2 = new TaskBuilder().withName("gym").withDate("21/12/2018").withTime(
                    "20:00").withDescription("50min workout").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(true).build();
            gym3 = new TaskBuilder().withName("gym").withDate("22/12/2018").withTime(
                    "20:00").withDescription("50min workout").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(true).build();
            cs2103 = new TaskBuilder().withName("cs2103").withDate("01/01").withTime(
                    "16:00").withDescription("Crazy but useful mod").
                    withTag("school").withVenue("icube").withPriority("1").withFavorite(true).build();
            study = new TaskBuilder().withName("study").withDate("31/12").withTime(
                    "15:34").withDescription("Study for MTE").
                    withTag("school").withVenue("Central lib").withPriority("2").withFavorite(true).build();
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
        TestTask[] tasks = {gym, gym2, gym3, date, cs2103, study, assignment};
        List<TestTask> listOfTasks = asList(tasks);
        listOfTasks = sort(listOfTasks);
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    private <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    private static List<TestTask> sort(List<TestTask> list) {
        Collections.sort(list, (TestTask t1, TestTask t2) -> t1.getTag().compareTo(t2.getTag()));
        Collections.sort(list, (TestTask t1, TestTask t2) -> t1.getName().compareTo(t2.getName()));
        Collections.sort(list, (TestTask t1, TestTask t2) -> t1.getTime().compareTo(t2.getTime()));
        Collections.sort(list, (TestTask t1, TestTask t2) -> -t1.getPriority().compareTo(t2.getPriority()));
        Collections.sort(list, (TestTask t1, TestTask t2) -> t1.getDate().compareTo(t2.getDate()));
        return list;
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}

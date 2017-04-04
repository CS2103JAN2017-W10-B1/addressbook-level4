//@@author A0147996E
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskList;

public class TypicalRecurringTasks {
    public TestRecurringTask homework, homework2, gym, gym2, gym3, cs2103;
    private RecurringMode mode1 = RecurringMode.DAY;
    private RecurringMode mode2 = RecurringMode.WEEK;
    private RecurringMode mode3 = RecurringMode.MONTH;

    public TypicalRecurringTasks() {
        try {
            TaskDate date1 = new TaskDate("tmr");
            homework = new RecurringTaskBuilder().withName("homework").withDate(date1.toString()).withTime(
                    "20:00").withDescription("Crazy but useful mod")
                    .withTag("personal").withVenue("gym").withPriority("2").withFavorite(true).withRecurring(true)
                    .withMode(mode1).withFinished(false).build();
            homework2 = new RecurringTaskBuilder().withName("homework").withDate(date1.toString()).withTime(
                    "16:00").withDescription("Crazy but useful mod").
                    withTag("school").withVenue("icube").withPriority("1").withFavorite(true).withRecurring(true)
                    .withMode(mode1)
                    .withFinished(false).build();
            gym = new RecurringTaskBuilder().withName("gym").withDate("20/12/2017").withTime(
                    "20:00").withDescription("50min workout").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(true)
                    .withFinished(false).withRecurring(true).withMode(mode2).build();
            //gym2 and gym3 are built for findCommandTest
            gym2 = new RecurringTaskBuilder().withName("gym").withDate("07/06/2017").withTime(
                    "20:00").withDescription("50min workout").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(false).withRecurring(true)
                    .withMode(mode2)
                    .withFinished(false).build();
            gym3 = new RecurringTaskBuilder().withName("gym").withDate("08/06/2017").withTime(
                    "20:00").withDescription("50min workout").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(false).withRecurring(true)
                    .withMode(mode3)
                    .withFinished(false).build();
            cs2103 = new RecurringTaskBuilder().withName("cs2103").withDate("01/02").withTime(
                    "16:00").withDescription("Crazy but useful mod").
                    withTag("school").withVenue("icube").withPriority("1").withFavorite(true).withRecurring(true)
                    .withMode(mode3)
                    .withFinished(false).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : new TypicalRecurringTasks().getTypicalRecurringTasks()) {
            try {
                tm.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalRecurringTasks() {
        TestTask[] tasks = {homework, homework2, gym, gym2, gym3, cs2103};
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
        TaskManager tm = TaskManager.getStub();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}

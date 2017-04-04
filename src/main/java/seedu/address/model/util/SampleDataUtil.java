package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.Venue;

public class SampleDataUtil {

    //@@author A0147984L
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("pay tuition fee"), new TaskDate("25/3/2017"), new TaskTime(""),
                        new Description("don't forget!!!!"), new Tag(""),
                        new Venue(""), new Priority("3"), false),
                new Task(new Name("cs 2103"), new TaskDate("today"), new TaskTime("9:00"),
                        new Description("demo"), new Tag("study"),
                        new Venue("COM1"), new Priority("3"), true),
                new Event(new Name("cs final"), new TaskDate("4/5"), new TaskTime("15:00"),
                        new TaskTime("17:00"), new Description("cannot bring helpsheet"), new Tag("study"),
                        new Venue("MPSH"), new Priority("3"), true),
                new Task(new Name("clean up room"), new TaskDate("4/5"), new TaskTime("9:00"),
                        new Description("get ready to go home"), new Tag("personal"),
                        new Venue(""), new Priority("1"), false),
                new Task(new Name("Gym"), new TaskDate("12/3"), new TaskTime("11:00"),
                        new Description("50 mins workout"), new Tag("personal"),
                        new Venue("MPSH"), new Priority("2"), true),
                new Task(new Name("study hard"), new TaskDate(""), new TaskTime(""),
                        new Description("goal in my life"), new Tag("study"),
                        new Venue(""), new Priority("3"), true)
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    //@@author
    public static ReadOnlyTaskManager getSampleTaskManager() throws IllegalValueException {
        try {
            TaskManager sampleAB = TaskManager.getInstance();
            sampleAB.clear();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}

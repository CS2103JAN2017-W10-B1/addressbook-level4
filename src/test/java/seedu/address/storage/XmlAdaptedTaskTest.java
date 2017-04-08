//@@author A0138474X
package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;
import seedu.address.model.task.ReadOnlyTask.FinishProperty;
import seedu.address.model.task.RecurringEvent;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.Venue;

public class XmlAdaptedTaskTest {

    @Test
    public void floatingTaskTest() {
        Task task = null;
        try {
            task = new Task(new Name("recurring Event"), new TaskDate(""), new TaskTime(""),
                    new Description(""), new Tag(""), new Venue(""), new Priority(""), false,
                    FinishProperty.UNFINISHED);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XmlAdaptedTask storedTask = new XmlAdaptedTask(task);
        storeTaskTest(task, storedTask);
        readtaskTest(task, storedTask);
    }

    @Test
    public void taskTest() {
        Task task = null;
        try {
            task = new Task(new Name("recurring Event"), new TaskDate("24/3"), new TaskTime("11:00"),
                    new Description(""), new Tag(""), new Venue(""), new Priority(""), false,
                    FinishProperty.UNFINISHED);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XmlAdaptedTask storedTask = new XmlAdaptedTask(task);
        storeTaskTest(task, storedTask);
        readtaskTest(task, storedTask);
    }

    @Test
    public void eventTest() {
        Event task = null;
        try {
            task =  new Event(new Name("recurring Event"), new TaskDate("23/3"), new TaskTime("10:00"),
                    new TaskDate("24/3"), new TaskTime("11:00"), new Description(""), new Tag(""), new Venue(""),
                    new Priority(""), false, FinishProperty.UNFINISHED);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XmlAdaptedTask storedTask = new XmlAdaptedTask(task);
        storeTaskTest(task, storedTask);
        readtaskTest(task, storedTask);
    }

    @Test
    public void taskRecurringTest() {
        RecurringTask task = null;
        try {
            task = new RecurringTask(new Name("recurring Event"), new TaskDate("24/3"), new TaskTime("11:00"),
                    new Description(""), new Tag(""), new Venue(""), new Priority(""), false,
                    FinishProperty.UNFINISHED, RecurringMode.DAY);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XmlAdaptedTask storedTask = new XmlAdaptedTask(task);
        storeTaskTest(task, storedTask);
        readtaskTest(task, storedTask);
    }

    @Test
    public void eventRecurringTest() {
        RecurringEvent task = null;
        try {
            task = new RecurringEvent(new Name("recurring Event"), new TaskDate("23/3"), new TaskTime("10:00"),
                    new TaskDate("24/3"), new TaskTime("11:00"), new Description(""), new Tag(""), new Venue(""),
                    new Priority(""), false, FinishProperty.UNFINISHED, RecurringMode.DAY);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XmlAdaptedTask storedTask = new XmlAdaptedTask(task);
        storeTaskTest(task, storedTask);
        readtaskTest(task, storedTask);
    }
    private void readtaskTest(Task task, XmlAdaptedTask storedTask) {
        Task readTask = null;
        try {
            readTask = storedTask.toModelType();
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(readTask, task);
    }

    private void storeTaskTest(Task task, XmlAdaptedTask storedTask) {
        checkField(task, storedTask);
    }

    public void checkField(Task task, XmlAdaptedTask storedTask) {
        assertEquals(task.getName().getValue(), storedTask.name);
        assertEquals(task.getDate().getValue(), storedTask.date);
        assertEquals(task.getTime().getValue(), storedTask.time);
        assertEquals(task.getDescription().getValue(), storedTask.description);
        assertEquals(task.getTag().getValue(), storedTask.tag);
        assertEquals(task.getVenue().getValue(), storedTask.description);
        assertEquals(task.getPriority().getValue(), storedTask.priority);
        assertEquals(task.isFavorite(), storedTask.isFavourite);
        assertEquals(task.getFinished(), storedTask.isFinished);

        if (task.isEvent() && !task.isRecurring()) {
            assertEquals(((Event) task).getStartDate().getValue(), storedTask.startDate);
            assertEquals(((Event) task).getStartTime().getValue(), storedTask.startTime);
        } else if (task.isRecurring() && !task.isEvent()) {
            assertEquals(((RecurringTask) task).getRecurringPeriod(), storedTask.recurringMode);
        } else if (task.isRecurring() && task.isEvent()) {
            assertEquals(((RecurringEvent) task).getStartDate().getValue(), storedTask.startDate);
            assertEquals(((RecurringEvent) task).getStartTime().getValue(), storedTask.startTime);
            assertEquals(((RecurringEvent) task).getRecurringPeriod(), storedTask.recurringMode);
        }
    }
}

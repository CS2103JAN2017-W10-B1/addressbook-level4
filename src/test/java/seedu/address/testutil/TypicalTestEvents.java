//@@author A0147996E
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

public class TypicalTestEvents {
    public TestEvent gym, gym2, gym3, cs2103, study, assignment, date, date2, date3, date4, meeting,
        familyDinner, travel, shopping, shopping2;

    public TypicalTestEvents() {
        try {
            gym = new EventBuilder().withName("gym").withDate("20/12/2017").withTime(
                    "20:00").withDescription("50min workout").withStartDate("20/11/2017").withStartTime("19:00").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(true)
                    .withFinished(false).withEvent(true).build();
            //gym2 and gym3 are built for findCommandTest
            gym2 = new EventBuilder().withName("gym").withDate("21/12/2017").withTime(
                    "20:00").withDescription("50min workout").withStartDate("20/11/2017").withStartTime("").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(false)
                    .withFinished(false).withEvent(true).build();
            gym3 = new EventBuilder().withName("gym").withDate("22/12/2017").withTime(
                    "20:00").withDescription("50min workout").withStartDate("20/11/2017").withStartTime("").
                    withTag("personal").withVenue("gym").withPriority("2").withFavorite(false)
                    .withFinished(false).withEvent(true).build();
            cs2103 = new EventBuilder().withName("cs2103").withDate("01/02").withTime(
                    "16:00").withDescription("Crazy but useful mod").
                    withTag("school").withVenue("icube").withPriority("1").withFavorite(true)
                    .withStartDate("20/12/2017").withStartTime("19:00").withFinished(false).withEvent(true).build();
            study = new EventBuilder().withName("study").withDate("21/03").withTime(
                    "15:34").withDescription("Study for MTE").
                    withTag("school").withVenue("Central lib").withPriority("2").withFavorite(true)
                    .withStartDate("20/12/2017").withStartTime("19:00").withFinished(false).withEvent(true).build();
            assignment = new EventBuilder().withName("assignment").withDate("10/12/2017").withTime(
                    "10:00").withDescription("IE2150").
                    withTag("study").withVenue("Utown").withPriority("2").withFavorite(true)
                    .withStartDate("02/12/2017").withStartTime("19:00").withFinished(false).withEvent(true).build();
            //The test tasks below are for duplicate task testing
            date  = new EventBuilder().withName("date").withDate("14/02/2018").withTime(
                    "12:00").withDescription("Most important day").withFinished(false).
                    withTag("personal").withVenue("Gardens by the bay").withPriority("3")
                    .withStartDate("11/02/2018").withStartTime("08:00").withFavorite(false).withEvent(true).build();
            date2  = new EventBuilder().withName("date").withDate("14/02/2020").withTime(
                    "12:00").withDescription("Most important day").withFinished(false).
                    withTag("personal").withVenue("Gardens by the bay").withPriority("3")
                    .withStartDate("tmr").withStartTime("19:00").withFavorite(false).withEvent(true).build();
            date3  = new EventBuilder().withName("date").withDate("14/02/2018").withTime(
                    "12:00").withDescription("Most important day").withFinished(false).
                    withTag("personal").withVenue("Gardens by the bay").withPriority("3")
                    .withStartDate("11/02/2018").withStartTime("09:00").withFavorite(false).withEvent(true).build();
            date4  = new EventBuilder().withName("date").withDate("14/02/2018").withTime(
                    "12:00").withDescription("Most important day").
                    withTag("private").withVenue("Gardens by the bay").withPriority("3").withFinished(false)
                    .withStartDate("11/02/2018").withStartTime("08:00").withFavorite(false).withEvent(true).build();
          //The test tasks above are for duplicate task testing
            meeting = new EventBuilder().withName("meeting").withDate("27/04/2017").withTime(
                    "12:00").withDescription("Meeting old friends").
                    withTag("school").withVenue("PGP").withPriority("2").withFavorite(false)
                    .withStartDate("25/04/2017").withStartTime("19:00").withFinished(false).withEvent(true).build();
            familyDinner = new EventBuilder().withName("family dinner").withDate("1/1").withTime(
                    "20:00").withDescription("Meeting families").
                    withTag("family").withVenue("home").withPriority("important").withFavorite(true)
                    .withStartDate("20/12/2017").withStartTime("19:00").withFinished(false).withEvent(true).build();
            travel = new EventBuilder().withName("travel").withDate("1/01/2018").withTime(
                    "").withDescription("To Africa").
                    withTag("personal").withVenue("Africa").withPriority("important").withFinished(false)
                    .withStartDate("20/12/2017").withStartTime("").withFavorite(true).withEvent(true).build();
            //shopping violates start date constraint
            shopping = new EventBuilder().withName("shopping").withDate("1/01/2018").withTime(
                    "").withDescription("Shopping in Airport").
                    withTag("personal").withVenue("Airport").withPriority("3").withFavorite(true)
                    .withStartDate("20/12/2018").withStartTime("").withFinished(false).withEvent(true).build();
        } catch (IllegalValueException e) {
            throw new IllegalStateException("Unable to build task");
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestEvent task : new TypicalTestEvents().getTypicalEvents()) {
            try {
                tm.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public TestEvent[] getTypicalEvents() {
        TestEvent[] events = {gym, gym2, gym3, cs2103, study, assignment, date};
        List<TestEvent> listOfEvents = asList(events);
        listOfEvents = sort(listOfEvents);
        return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
    }

    private <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    private static List<TestEvent> sort(List<TestEvent> list) {
        Collections.sort(list, (TestEvent t1, TestEvent t2) -> t1.getTag().compareTo(t2.getTag()));
        Collections.sort(list, (TestEvent t1, TestEvent t2) -> t1.getName().compareTo(t2.getName()));
        Collections.sort(list, (TestEvent t1, TestEvent t2) -> t1.getTime().compareTo(t2.getTime()));
        Collections.sort(list, (TestEvent t1, TestEvent t2) -> -t1.getPriority().compareTo(t2.getPriority()));
        Collections.sort(list, (TestEvent t1, TestEvent t2) -> t1.getDate().compareTo(t2.getDate()));
        return list;
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = TaskManager.getStub();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}

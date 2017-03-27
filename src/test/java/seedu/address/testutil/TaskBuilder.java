//@@author A0147984L
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;

public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(name);
        return this;
    }

    public TaskBuilder withTag(String tag) throws IllegalValueException {
        task.setTag(tag);
        return this;
    }

    public TaskBuilder withDate(String date) throws IllegalValueException {
        this.task.setDate(date);
        return this;
    }

    public TaskBuilder withTime(String time) throws IllegalValueException {
        this.task.setTime(time);
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(description);
        return this;
    }

    public TaskBuilder withVenue(String venue) throws IllegalValueException {
        this.task.setVenue(venue);
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(priority);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

    public TaskBuilder withFavorite(boolean isFavorite) throws IllegalValueException {
        this.task.setFavorite(isFavorite);
        return this;
    }

    public TaskBuilder withFinished(boolean isFinished) throws IllegalValueException {
        this.task.setFinished(isFinished);
        return this;
    }
}

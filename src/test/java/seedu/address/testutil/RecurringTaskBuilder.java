//@@author A0147996E
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;

public class RecurringTaskBuilder extends TaskBuilder {

    private TestRecurringTask task;

    public RecurringTaskBuilder() {
        this.task = new TestRecurringTask();
    }
    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public RecurringTaskBuilder(TestRecurringTask taskToCopy) {
        this.task = new TestRecurringTask(taskToCopy);
    }

    public RecurringTaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(name);
        return this;
    }

    public RecurringTaskBuilder withTag(String tag) throws IllegalValueException {
        task.setTag(tag);
        return this;
    }

    public RecurringTaskBuilder withDate(String date) throws IllegalValueException {
        this.task.setDate(date);
        return this;
    }

    public RecurringTaskBuilder withTime(String time) throws IllegalValueException {
        this.task.setTime(time);
        return this;
    }

    public RecurringTaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(description);
        return this;
    }

    public RecurringTaskBuilder withVenue(String venue) throws IllegalValueException {
        this.task.setVenue(venue);
        return this;
    }

    public RecurringTaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(priority);
        return this;
    }

    public TestRecurringTask build() {
        return this.task;
    }

    public RecurringTaskBuilder withFavorite(boolean isFavorite) throws IllegalValueException {
        this.task.setFavorite(isFavorite);
        return this;
    }

    public RecurringTaskBuilder withFinished(boolean isFinished) throws IllegalValueException {
        this.task.setFinished(isFinished);
        return this;
    }
    public RecurringTaskBuilder withMode(RecurringMode mode) throws IllegalValueException {
        this.task.setMode(mode);
        return this;
    }
    public RecurringTaskBuilder withRecurring(boolean isRecurring) throws IllegalValueException {
        this.task.setRecurring(isRecurring);
        return this;
    }
}

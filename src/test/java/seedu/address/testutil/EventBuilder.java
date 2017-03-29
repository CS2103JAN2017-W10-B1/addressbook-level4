//@@author A0147996E
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;

public class EventBuilder extends TaskBuilder {

    private TestEvent event;

    public EventBuilder() {
        this.event = new TestEvent();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(TestEvent eventToCopy) {
        this.event = new TestEvent(eventToCopy);
    }

    public EventBuilder withName(String name) throws IllegalValueException {
        this.event.setName(name);
        return this;
    }

    public EventBuilder withTag(String tag) throws IllegalValueException {
        event.setTag(tag);
        return this;
    }

    public EventBuilder withDate(String date) throws IllegalValueException {
        this.event.setDate(date);
        return this;
    }

    public EventBuilder withTime(String time) throws IllegalValueException {
        this.event.setTime(time);
        return this;
    }

    public EventBuilder withStartDate(String date) throws IllegalValueException {
        this.event.setStartDate(date);
        return this;
    }

    public EventBuilder withStartTime(String time) throws IllegalValueException {
        this.event.setStartTime(time);
        return this;
    }
    public EventBuilder withDescription(String description) throws IllegalValueException {
        this.event.setDescription(description);
        return this;
    }

    public EventBuilder withVenue(String venue) throws IllegalValueException {
        this.event.setVenue(venue);
        return this;
    }

    public EventBuilder withPriority(String priority) throws IllegalValueException {
        this.event.setPriority(priority);
        return this;
    }

    public TestEvent build() {
        return this.event;
    }

    public EventBuilder withFavorite(boolean isFavorite) throws IllegalValueException {
        this.event.setFavorite(isFavorite);
        return this;
    }

    public EventBuilder withFinished(boolean isFinished) throws IllegalValueException {
        this.event.setFinished(isFinished);
        return this;
    }

    public EventBuilder withEvent(boolean isEvent) throws IllegalValueException {
        this.event.setEvent(isEvent);
        return this;
    }
}

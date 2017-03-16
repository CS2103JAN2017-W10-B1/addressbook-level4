package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Date;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;
import seedu.address.model.task.Venue;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = false)
    private String date;
    @XmlElement(required = false)
    private String time;
    @XmlElement(required = false)
    private String tag;
    @XmlElement(required = false)
    private String description;
    @XmlElement(required = false)
    private String venue;
    @XmlElement(required = false)
    private String priority;
    @XmlElement(required = false)
    private boolean isFinished;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        date = source.getDate().getValue();
        time = source.getTime().getValue();
        tag = source.getTag().tagName;
        description = source.getDescription().getValue();
        venue = source.getVenue().getValue();
        priority = source.getPriority().getValue();
        isFinished = source.isFinished();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
    	final Name name = new Name(this.name);
        final Date date = new Date(this.date);
        final Time time = new Time(this.time);
        final Description description = new Description(this.description);
        final Tag tag = new Tag(this.tag);
        final Venue venue = new Venue(this.venue);
        final Priority priority = new Priority(this.priority);
        return new Task(name, date, time, description, tag, venue, priority, false, isFinished);
    }
}

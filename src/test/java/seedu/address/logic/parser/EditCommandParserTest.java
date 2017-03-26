//@@author A0143409J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.Venue;

public class EditCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public EditTaskDescriptor editTaskDescriptor;

    @Before
    public void setUp() {
        editTaskDescriptor = new EditTaskDescriptor();
    }

    @Test
    public void nameOnlyEditTaskTest() throws Exception {

        Field field = EditCommand.class.getDeclaredField("editTaskDescriptor");
        field.setAccessible(true);

        Name sampleName = new Name("CS2103 Lecture");
        Optional<Name> optionalName = Optional.of(sampleName);
        editTaskDescriptor.setName(optionalName);
        EditCommand editCommand =  (EditCommand) EditCommandParser.parse("1 n/CS2103 Lecture \n");
        assertEqualEditTaskDescriptor((EditTaskDescriptor) field.get(editCommand), editTaskDescriptor);
    }

    @Test
    public void nameAndDateTaskTest() throws Exception {

        Field field = EditCommand.class.getDeclaredField("editTaskDescriptor");
        field.setAccessible(true);

        EditCommand editCommand =  (EditCommand) EditCommandParser.parse("1 n/CS2103 Lecture due/10/3 \n");
        Name sampleName = new Name("CS2103 Lecture");
        Optional<Name> optionalName = Optional.of(sampleName);
        editTaskDescriptor.setName(optionalName);
        TaskDate editTaskDescriptorDate = new TaskDate("10/3");
        Optional<TaskDate> optionalDate = Optional.of(editTaskDescriptorDate);
        editTaskDescriptor.setDate(optionalDate);
        assertEqualEditTaskDescriptor((EditTaskDescriptor) field.get(editCommand), editTaskDescriptor);
    }

    @Test
    public void nameDateTimeTaskTest() throws Exception {

        Field field = EditCommand.class.getDeclaredField("editTaskDescriptor");
        field.setAccessible(true);

        EditCommand editCommand =  (EditCommand) EditCommandParser.parse("1 n/CS2103 Lecture due/10/3 dueT/16:00 \n");
        Name sampleName = new Name("CS2103 Lecture");
        Optional<Name> optionalName = Optional.of(sampleName);
        editTaskDescriptor.setName(optionalName);
        TaskDate sampleDate = new TaskDate("10/3");
        Optional<TaskDate> optionalDate = Optional.of(sampleDate);
        editTaskDescriptor.setDate(optionalDate);
        TaskTime sampleTime = new TaskTime("16:00");
        Optional<TaskTime> optionalTime = Optional.of(sampleTime);
        editTaskDescriptor.setTime(optionalTime);
        assertEqualEditTaskDescriptor((EditTaskDescriptor) field.get(editCommand), editTaskDescriptor);
    }

    @Test
    public void nameDateTimeDescriptionTaskTest() throws Exception {

        Field field = EditCommand.class.getDeclaredField("editTaskDescriptor");
        field.setAccessible(true);

        EditCommand editCommand =  (EditCommand) EditCommandParser.parse("1 n/CS2103 Lecture "
                + "due/10/3 dueT/16:00 d/Interesting \n");
        Name sampleName = new Name("CS2103 Lecture");
        Optional<Name> optionalName = Optional.of(sampleName);
        editTaskDescriptor.setName(optionalName);
        TaskDate sampleDate = new TaskDate("10/3");
        Optional<TaskDate> optionalDate = Optional.of(sampleDate);
        editTaskDescriptor.setDate(optionalDate);
        TaskTime sampleTime = new TaskTime("16:00");
        Optional<TaskTime> optionalTime = Optional.of(sampleTime);
        editTaskDescriptor.setTime(optionalTime);
        Description sampleDescription = new Description("Interesting");
        Optional<Description> optionalDescription = Optional.of(sampleDescription);
        editTaskDescriptor.setDescription(optionalDescription);
        assertEqualEditTaskDescriptor((EditTaskDescriptor) field.get(editCommand), editTaskDescriptor);
    }

    @Test
    public void nameDateTimeDescriptionTagTaskTest() throws Exception {

        Field field = EditCommand.class.getDeclaredField("editTaskDescriptor");
        field.setAccessible(true);

        EditCommand editCommand =  (EditCommand) EditCommandParser.parse("1 n/CS2103 Lecture "
                + "due/10/3 dueT/16:00 d/Interesting #CS2103 \n");
        Name sampleName = new Name("CS2103 Lecture");
        Optional<Name> optionalName = Optional.of(sampleName);
        editTaskDescriptor.setName(optionalName);
        TaskDate sampleDate = new TaskDate("10/3");
        Optional<TaskDate> optionalDate = Optional.of(sampleDate);
        editTaskDescriptor.setDate(optionalDate);
        TaskTime sampleTime = new TaskTime("16:00");
        Optional<TaskTime> optionalTime = Optional.of(sampleTime);
        editTaskDescriptor.setTime(optionalTime);
        Description sampleDescription = new Description("Interesting");
        Optional<Description> optionalDescription = Optional.of(sampleDescription);
        editTaskDescriptor.setDescription(optionalDescription);
        Tag sampleTag = new Tag("CS2103");
        Optional<Tag> optionalTag = Optional.of(sampleTag);
        editTaskDescriptor.setTag(optionalTag);
        assertEqualEditTaskDescriptor((EditTaskDescriptor) field.get(editCommand), editTaskDescriptor);
    }

    @Test
    public void nameDateTimeDescriptionTagVenueTaskTest() throws Exception {

        Field field = EditCommand.class.getDeclaredField("editTaskDescriptor");
        field.setAccessible(true);

        EditCommand editCommand =  (EditCommand) EditCommandParser.parse("1 n/CS2103 Lecture "
                + "due/10/3 dueT/16:00 d/Interesting #CS2103 @I3 \n");
        Name sampleName = new Name("CS2103 Lecture");
        Optional<Name> optionalName = Optional.of(sampleName);
        editTaskDescriptor.setName(optionalName);
        TaskDate sampleDate = new TaskDate("10/3");
        Optional<TaskDate> optionalDate = Optional.of(sampleDate);
        editTaskDescriptor.setDate(optionalDate);
        TaskTime sampleTime = new TaskTime("16:00");
        Optional<TaskTime> optionalTime = Optional.of(sampleTime);
        editTaskDescriptor.setTime(optionalTime);
        Description sampleDescription = new Description("Interesting");
        Optional<Description> optionalDescription = Optional.of(sampleDescription);
        editTaskDescriptor.setDescription(optionalDescription);
        Tag sampleTag = new Tag("CS2103");
        Optional<Tag> optionalTag = Optional.of(sampleTag);
        editTaskDescriptor.setTag(optionalTag);
        Venue sampleVenue = new Venue("I3");
        Optional<Venue> optionalVenue = Optional.of(sampleVenue);
        editTaskDescriptor.setVenue(optionalVenue);
        assertEqualEditTaskDescriptor((EditTaskDescriptor) field.get(editCommand), editTaskDescriptor);
    }

    @Test
    public void nameDateTimeDescriptionTagVenuePriorityTaskTest() throws Exception {

        Field field = EditCommand.class.getDeclaredField("editTaskDescriptor");
        field.setAccessible(true);

        EditCommand editCommand =  (EditCommand) EditCommandParser.parse("1 n/CS2103 Lecture "
                + "due/10/3 dueT/16:00 d/Interesting #CS2103 @I3 p/3 \n");
        Name sampleName = new Name("CS2103 Lecture");
        Optional<Name> optionalName = Optional.of(sampleName);
        editTaskDescriptor.setName(optionalName);
        TaskDate sampleDate = new TaskDate("10/3");
        Optional<TaskDate> optionalDate = Optional.of(sampleDate);
        editTaskDescriptor.setDate(optionalDate);
        TaskTime sampleTime = new TaskTime("16:00");
        Optional<TaskTime> optionalTime = Optional.of(sampleTime);
        editTaskDescriptor.setTime(optionalTime);
        Description sampleDescription = new Description("Interesting");
        Optional<Description> optionalDescription = Optional.of(sampleDescription);
        editTaskDescriptor.setDescription(optionalDescription);
        Tag sampleTag = new Tag("CS2103");
        Optional<Tag> optionalTag = Optional.of(sampleTag);
        editTaskDescriptor.setTag(optionalTag);
        Venue sampleVenue = new Venue("I3");
        Optional<Venue> optionalVenue = Optional.of(sampleVenue);
        editTaskDescriptor.setVenue(optionalVenue);
        Priority samplePriority = new Priority("3");
        Optional<Priority> optionalPriority = Optional.of(samplePriority);
        editTaskDescriptor.setPriority(optionalPriority);
        assertEqualEditTaskDescriptor((EditTaskDescriptor) field.get(editCommand), editTaskDescriptor);
    }

    private void assertEqualEditTaskDescriptor(EditTaskDescriptor descriptor1, EditTaskDescriptor descriptor2) {
        assertEquals(descriptor1.getName(), descriptor2.getName());
        assertEquals(descriptor1.getDate(), descriptor2.getDate());
        assertEquals(descriptor1.getTime(), descriptor2.getTime());
        assertEquals(descriptor1.getDescription(), descriptor2.getDescription());
        assertEquals(descriptor1.getTag(), descriptor2.getTag());
        assertEquals(descriptor1.getVenue(), descriptor2.getVenue());
        assertEquals(descriptor1.getPriority(), descriptor2.getPriority());
    }
}

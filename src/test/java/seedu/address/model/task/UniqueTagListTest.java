//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertTrue;

import java.util.Set;
//@@author A0143049J
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.testutil.TypicalTestTasks;

public class UniqueTagListTest {

    private UniqueTagList tester = new UniqueTagList();
    private final TypicalTestTasks testUtil = new TypicalTestTasks();

    @Test
    public void sort() throws DuplicateTagException, IllegalValueException {
        Tag tag1 = new Tag("abc");
        Tag tag2 = new Tag("test");
        tester.add(tag1);
        tester.add(tag2);

        tester.sort();
        
        Set<Tag> tags = tester.toSet();

        assertTrue(tags.contains(tag1));
        assertTrue(tags.contains(tag2));
    }
    
    @Test
    public void constructorTest() throws DuplicateTagException, IllegalValueException {
        Tag tag1 = new Tag("abc");
        Tag tag2 = new Tag("test");
        tester = new UniqueTagList(tag1, tag2);
        Set<Tag> tags1 = tester.toSet();

        assertTrue(tags1.contains(tag1));
        assertTrue(tags1.contains(tag2));
        
        UniqueTagList newTester = new UniqueTagList(tester);
        Set<Tag> tags2 = newTester.toSet();

        assertTrue(tags2.contains(tag1));
        assertTrue(tags2.contains(tag2));
        UniqueTagList newNewTester = new UniqueTagList(tags2);
        Set<Tag> tags3 = newNewTester.toSet();

        assertTrue(tags3.contains(tag1));
        assertTrue(tags3.contains(tag2));
    }
}

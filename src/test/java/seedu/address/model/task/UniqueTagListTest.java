//@@author A0143409J
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

public class UniqueTagListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UniqueTagList tester = new UniqueTagList();

    @Test
    public void sortTag_twoTags_allTagsContained() throws DuplicateTagException, IllegalValueException {
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
    public void checkConstructor_validConstructors_successfulConstruction()
            throws DuplicateTagException, IllegalValueException {
        Tag tag1 = new Tag("abc");
        Tag tag2 = new Tag("test");
        tester = new UniqueTagList(tag1, tag2);
        Set<Tag> tags1 = tester.toSet();

        assertTrue(tags1.contains(tag1));
        assertTrue(tags1.contains(tag2));

        UniqueTagList tagListTester = new UniqueTagList(tester);
        Set<Tag> tags2 = tagListTester.toSet();

        assertTrue(tags2.contains(tag1));
        assertTrue(tags2.contains(tag2));
        UniqueTagList newSetTester = new UniqueTagList(tags2);
        Set<Tag> tags3 = newSetTester.toSet();

        assertTrue(tags3.contains(tag1));
        assertTrue(tags3.contains(tag2));

        ArrayList<Tag> tagsList = new ArrayList<Tag>();
        tagsList.add(tag1);
        tagsList.add(tag2);
        tester = new UniqueTagList(tagsList);
        Set<Tag> tags4 = tester.toSet();

        assertTrue(tags4.contains(tag1));
        assertTrue(tags4.contains(tag2));
    }

    @Test
    public void equivalenceTagList_twoEquivalentTagLists_equalTagLists() throws IllegalValueException {
        Tag tag1 = new Tag("abc");
        Tag tag2 = new Tag("test");
        tester = new UniqueTagList(tag1, tag2);
        Set<Tag> tags1 = tester.toSet();

        assertTrue(tags1.contains(tag1));
        assertTrue(tags1.contains(tag2));

        UniqueTagList newTester = new UniqueTagList(tester);

        assertTrue(newTester.equals(tester));
    }

    @Test
    public void setTag_importAnotherTagList_sucessfullySet() throws IllegalValueException {
        Tag tag1 = new Tag("abc");
        Tag tag2 = new Tag("test");
        tester = new UniqueTagList(tag1, tag2);
        Set<Tag> tags = tester.toSet();

        assertTrue(tags.contains(tag1));
        assertTrue(tags.contains(tag2));

        UniqueTagList newTester = new UniqueTagList();
        newTester.setTags(tester);

        tags = newTester.toSet();

        assertTrue(tags.contains(tag1));
        assertTrue(tags.contains(tag2));
    }

    @Test
    public void mergeTagLists_twoDifferentLists_successfullyMerged() throws IllegalValueException {
        Tag tag1 = new Tag("abc");
        Tag tag2 = new Tag("test");
        Tag tag3 = new Tag("lala");
        Tag tag4 = new Tag("xx");
        tester = new UniqueTagList(tag1, tag2);
        UniqueTagList newTester = new UniqueTagList(tag3, tag4);
        tester.mergeFrom(newTester);
        Set<Tag> tags1 = tester.toSet();

        assertTrue(tags1.contains(tag1));
        assertTrue(tags1.contains(tag2));
        assertTrue(tags1.contains(tag3));
        assertTrue(tags1.contains(tag4));

        tester = new UniqueTagList("tag1", "tag2", "tag3", "tag4", "tag5");
        Set<Tag> tagsSet = tester.toSet();
        assertEquals(tagsSet.size(), 5);
    }

    @Test
    public void duplicateTag_addDuplicateTag_duplicateTagException() throws IllegalValueException {
        Tag tag1 = new Tag("abc");
        thrown.expect(DuplicateTagException.class);
        tester = new UniqueTagList(tag1, tag1);
    }
}

//@@ author A0143409J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListFavoriteCommand;
import seedu.address.logic.commands.ListFinishedCommand;

public class ListCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nullTest() throws Exception {

        Field field = ListCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListCommand listCommand =  (ListCommand) ListCommandParser.parse(null);
        assertEquals(field.get(listCommand), null);
    }

    @Test
    public void singleFieldTest() throws Exception {

        Field field = ListCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListCommand listCommand = (ListCommand) ListCommandParser.parse("personal");

        Set<String> set = new HashSet<String>();
        set.add("personal");

        assertEquals(field.get(listCommand), set);
    }

    @Test
    public void multipleFieldTest() throws Exception {

        Field field = ListCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListCommand listCommand = (ListCommand) ListCommandParser.parse("personal school");

        Set<String> set = new HashSet<String>();
        set.add("personal");
        set.add("school");

        assertEquals(field.get(listCommand), set);
    }

    @Test
    public void singleFieldFinishedTest() throws Exception {

        Field field = ListFinishedCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListFinishedCommand listFinishedCommand =
                (ListFinishedCommand) ListCommandParser.parse("finished personal");

        Set<String> set = new HashSet<String>();
        set.add("personal");

        assertEquals(field.get(listFinishedCommand), set);
    }

    @Test
    public void multipleFieldFinishedTest() throws Exception {

        Field field = ListFinishedCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListFinishedCommand listFinishedCommand =
                (ListFinishedCommand) ListCommandParser.parse("finished personal school");

        Set<String> set = new HashSet<String>();
        set.add("personal");
        set.add("school");

        assertEquals(field.get(listFinishedCommand), set);
    }

    @Test
    public void singleFieldAllTest() throws Exception {

        Field field = ListAllCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListAllCommand listAllCommand =
                (ListAllCommand) ListCommandParser.parse("all personal");

        Set<String> set = new HashSet<String>();
        set.add("personal");

        assertEquals(field.get(listAllCommand), set);
    }

    @Test
    public void multipleFieldAllTest() throws Exception {

        Field field = ListAllCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListAllCommand listAllCommand =
                (ListAllCommand) ListCommandParser.parse("all personal school");

        Set<String> set = new HashSet<String>();
        set.add("personal");
        set.add("school");

        assertEquals(field.get(listAllCommand), set);
    }

    @Test
    public void singleFieldFavoriteTest() throws Exception {

        Field field = ListFavoriteCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListFavoriteCommand listFavoriteCommand =
                (ListFavoriteCommand) ListCommandParser.parse("favorite personal");

        Set<String> set = new HashSet<String>();
        set.add("personal");

        assertEquals(field.get(listFavoriteCommand), set);
    }

    @Test
    public void multipleFieldFavoriteTest() throws Exception {

        Field field = ListFavoriteCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListFavoriteCommand listFavoriteCommand =
                (ListFavoriteCommand) ListCommandParser.parse("favorite personal school");

        Set<String> set = new HashSet<String>();
        set.add("personal");
        set.add("school");

        assertEquals(field.get(listFavoriteCommand), set);
    }
}

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ListCommand;

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

        ListCommand listCommand =  (ListCommand) ListCommandParser.parse("personal");

        Set<String> set = new HashSet<String>();
        set.add("personal");

        assertEquals(field.get(listCommand), set);
    }

    @Test
    public void multipleFieldTest() throws Exception {

        Field field = ListCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        ListCommand listCommand =  (ListCommand) ListCommandParser.parse("personal school");

        Set<String> set = new HashSet<String>();
        set.add("personal");
        set.add("school");

        assertEquals(field.get(listCommand), set);
    }
}

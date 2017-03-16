package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.HelpCommand;

public class HelpCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nullTest() throws Exception{

        Field field = HelpCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        HelpCommandParser tester = new HelpCommandParser();
        HelpCommand helpCommand =  (HelpCommand) tester.parse(null);
        assertEquals(field.get(helpCommand), null);
    }

    @Test
    public void singleFieldTest() throws Exception {

        Field field = HelpCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        HelpCommandParser tester = new HelpCommandParser();
        HelpCommand HelpCommand =  (HelpCommand) tester.parse("personal");

        Set<String> set = new HashSet<String>();
        set.add("personal");

        assertEquals(field.get(HelpCommand), set);
    }

    /*@Test
    public void nultipleieldTest() throws Exception {

        Field field = HelpCommand.class.getDeclaredField("keywords");
        field.setAccessible(true);

        HelpCommandParser tester = new HelpCommandParser();
        HelpCommand HelpCommand =  (HelpCommand) tester.parse("personal school");

        Set<String> set = new HashSet<String>();
        set.add("personal");
        set.add("school");

        assertEquals(field.get(HelpCommand), set);
    }*/
}

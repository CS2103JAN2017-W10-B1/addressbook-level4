package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.HelpCommand;

public class HelpCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nullTest() throws Exception{

        Field field = HelpCommand.class.getDeclaredField("USAGE_MESSAGE");
        field.setAccessible(true);

        HelpCommand helpCommand =  (HelpCommand) HelpCommandParser.parse(null);
        assertEquals(field.get(helpCommand), null);
    }

    @Test
    public void singleCommandTest() throws Exception {

        Field field = HelpCommand.class.getDeclaredField("USAGE_MESSAGE");
        field.setAccessible(true);

        HelpCommand HelpCommand =  (HelpCommand) HelpCommandParser.parse(AddCommand.COMMAND_WORD);

        assertEquals(field.get(HelpCommand), AddCommand.MESSAGE_USAGE);
    }

    /*@Test
    public void mfltipleFieldTest() throws Exception {

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

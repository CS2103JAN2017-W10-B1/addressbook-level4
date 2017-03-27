package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class CommandFormatterTest {

    @Test
    public void singleListTest() throws Exception {
        String formatHeader = "List all tasks";
        String list1 = "list1";
        Set<String> keywords = new HashSet<>();
        keywords.add(list1);

        String result = CommandFormatter.listFormatter(formatHeader, keywords);
        assertEquals(result, "List all tasks in list list1");
    }
}

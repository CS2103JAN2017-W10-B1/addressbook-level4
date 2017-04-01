//@@ author A0143409J
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

    @Test
    public void undoFriendlyMesageTest() throws Exception {
        String formatHeader = "Hey";
        String command = "rubbish";

        String result = CommandFormatter.undoFormatter(formatHeader, command);
        assertEquals(result, "Hey\nYou can undo the rubbish by typing 'undo'");
    }

    @Test
    public void undoMesageTest() throws Exception {
        ClearCommand clearCommand = new ClearCommand();

        String result = CommandFormatter.undoMessageFormatter(UndoCommand.MESSAGE_SUCCESS, clearCommand);
        assertEquals(result, "Undo clear command successfully.");
    }

    @Test
    public void redoMesageTest() throws Exception {
        ClearCommand clearCommand = new ClearCommand();

        String result = CommandFormatter.undoMessageFormatter(RedoCommand.MESSAGE_SUCCESS, clearCommand);
        assertEquals(result, "Redo clear command successfully.");
    }

    @Test
    public void undoAddMesageTest() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(1);

        String result = CommandFormatter.undoMessageFormatter(UndoCommand.MESSAGE_SUCCESS, deleteCommand);
        assertEquals(result, "Undo add command successfully.");
    }

    @Test
    public void redoDeleteMesageTest() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(1);

        String result = CommandFormatter.undoMessageFormatter(RedoCommand.MESSAGE_SUCCESS, deleteCommand);
        assertEquals(result, "Redo add command successfully.");
    }
}

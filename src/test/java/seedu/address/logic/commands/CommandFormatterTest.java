//@@ author A0143409J
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class CommandFormatterTest {

    @Test
    public void listFormatter_listAllTask_listSuccess() throws Exception {
        String formatHeader = "List all tasks";
        String list1 = "list1";
        Set<String> keywords = new HashSet<>();
        keywords.add(list1);

        String result = CommandFormatter.listFormatter(formatHeader, keywords);
        assertEquals(result, "List all tasks in list list1");
    }

    @Test
    public void undoFormatter_friendlyMesage_correctMessage() throws Exception {
        String formatHeader = "Hey";
        String command = "rubbish";

        String result = CommandFormatter.undoFormatter(formatHeader, command);
        assertEquals(result, "Hey\nYou can undo the rubbish by typing 'undo'");
    }

    @Test
    public void undoMessageFormatter_clear_correctMessage() throws Exception {
        ClearCommand clearCommand = new ClearCommand();

        String result = CommandFormatter.undoMessageFormatter(UndoCommand.MESSAGE_SUCCESS, clearCommand);
        assertEquals(result, "Undo clear command successfully.\nYou can type 'redo' to revert this undo.\n");
    }

    @Test
    public void redoMessageFormatter_clear_correctMessage() throws Exception {
        ClearCommand clearCommand = new ClearCommand();

        String result = CommandFormatter.undoMessageFormatter(RedoCommand.MESSAGE_SUCCESS, clearCommand);
        assertEquals(result, "Redo clear command successfully.\nYou can type 'undo' to revert this redo again!");
    }

    @Test
    public void undoMessageFormatter_delete_correctMessage() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(1, false);

        String result = CommandFormatter.undoMessageFormatter(UndoCommand.MESSAGE_SUCCESS, deleteCommand);
        assertEquals(result, "Undo add command successfully.\nYou can type 'redo' to revert this undo.\n");
    }

    @Test
    public void redoMessageFormatter_delete_correctMessage() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(1, false);

        String result = CommandFormatter.undoMessageFormatter(RedoCommand.MESSAGE_SUCCESS, deleteCommand);
        assertEquals(result, "Redo add command successfully.\nYou can type 'undo' to revert this redo again!");
    }

    @Test
    public void viewFeedBackFormatter_friendlyMessage_correctMessages() {
        String messageDisplay = CommandFormatter.viewCommandFeedBackMessageFormatter(
                "0", "", "today!", "tomorrow!");
        assertEquals(messageDisplay, "today!");
        messageDisplay = CommandFormatter.viewCommandFeedBackMessageFormatter(
                "1", "", "today!", "tomorrow!");
        assertEquals(messageDisplay, "tomorrow!");
        messageDisplay = CommandFormatter.viewCommandFeedBackMessageFormatter(
                "2", "", "today!", "tomorrow!");
        assertEquals(messageDisplay, "");
    }

    @Test
    public void viewCommandParserFormatter_validString_chopUpSuccess() throws Exception {

        String[] parameters = CommandFormatter.viewCommandParserFormatter("next/10");

        assertEquals(parameters[0], "next");
        assertEquals(parameters[1], "10");
    }
}

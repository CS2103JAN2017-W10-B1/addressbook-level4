//@@author A0138474X
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNFAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    private EditCommandParser() {
    }
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public static Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_TAG,
                        PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_PRIORITY, PREFIX_FAVOURITE, PREFIX_UNFAVOURITE, PREFIX_START, PREFIX_STARTTIME);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setDue(ParserUtil.parseDate(argsTokenizer.getValue(PREFIX_DATE)));
            editTaskDescriptor.setStart(ParserUtil.parseDate(argsTokenizer.getValue(PREFIX_START)));
            editTaskDescriptor.setName(ParserUtil.parseName(argsTokenizer.getValue(PREFIX_NAME)));
            editTaskDescriptor.setDueTime(ParserUtil.parseTime(argsTokenizer.getValue(PREFIX_TIME)));
            editTaskDescriptor.setStartTime(ParserUtil.parseTime(argsTokenizer.getValue(PREFIX_STARTTIME)));
            editTaskDescriptor.setTag(ParserUtil.parseTag(argsTokenizer.getValue(PREFIX_TAG)));
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            editTaskDescriptor.setVenue(ParserUtil.parseVenue(argsTokenizer.getValue(PREFIX_VENUE)));
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
            editTaskDescriptor.setIsFavourite(ParserUtil.isFavourite(argsTokenizer.getValue(PREFIX_FAVOURITE)));
            editTaskDescriptor.setIsUnfavourite(ParserUtil.isUnfavourite(argsTokenizer.getValue(PREFIX_UNFAVOURITE)));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

}

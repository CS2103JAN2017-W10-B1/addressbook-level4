package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_TAG, PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_PRIORITY);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(argsTokenizer.getValue(PREFIX_NAME)));
            editTaskDescriptor.setDate(ParserUtil.parseDate(argsTokenizer.getValue(PREFIX_DATE)));
            editTaskDescriptor.setTime(ParserUtil.parseTime(argsTokenizer.getValue(PREFIX_TIME)));
            editTaskDescriptor.setTag(ParserUtil.parseTag(argsTokenizer.getValue(PREFIX_TAG)));
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            editTaskDescriptor.setVenue(ParserUtil.parseVenue(argsTokenizer.getValue(PREFIX_VENUE)));
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

}

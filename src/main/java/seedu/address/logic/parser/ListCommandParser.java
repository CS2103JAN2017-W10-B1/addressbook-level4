package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT_LIST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    private ListCommandParser() {
    }
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public static Command parse(String args) {
        if (args == null) {
            return new ListCommand();
        }

        // if not match, show all the unfinished task
        final Matcher matcher = KEYWORDS_ARGS_FORMAT_LIST.matcher(args.trim());
        if (!matcher.matches()) {
            return new ListCommand();
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new ListCommand(keywordSet);
    }

}

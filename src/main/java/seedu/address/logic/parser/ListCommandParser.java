//@@author A0143409J
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT_LIST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListFavoriteCommand;
import seedu.address.logic.commands.ListFinishedCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    private ListCommandParser() {
    }

    private static final String LIST_SEPARATOR = "\\s+";
    public static final String LIST_ALL = "all";
    public static final String LIST_FINISHED = "finished";
    public static final String LIST_FAVORITE = "favorite";

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
        final String[] keywords = matcher.group("keywords").split(LIST_SEPARATOR);
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));

        if (keywordSet.contains(LIST_ALL)) {
            keywordSet.remove(LIST_ALL);
            return new ListAllCommand(keywordSet);
        } else if (keywordSet.contains(LIST_FINISHED)) {
            keywordSet.remove(LIST_FINISHED);
            return new ListFinishedCommand(keywordSet);
        } else if (keywordSet.contains(LIST_FAVORITE)) {
            keywordSet.remove(LIST_FAVORITE);
            return new ListFavoriteCommand(keywordSet);
        } else {
            return new ListCommand(keywordSet);
        }
    }

}

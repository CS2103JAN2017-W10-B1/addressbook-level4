//@@author A0147996E
package seedu.address.logic.commands;

import java.util.Set;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD +
            ": Finds all task whose names contain any of "
            + "the specified keywords (case-sensitive)\nand displays them as a list with index numbers."
            + "\nYou can also specify whether to find among all/finished/unfinished tasks."
            + "Parameters: [all/finished/unfinished] KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " finished cs2103 st3131";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        assert keywords != null;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (keywords.contains("finished")) {
            keywords.remove("finished");
            model.updateFilteredTaskListFinished(keywords);
        } else if (keywords.contains("unfinished")) {
            keywords.remove("unfinished");
            model.updateFilteredTaskList(keywords);
        } else if (keywords.contains("all")) {
            keywords.remove("all");
            model.updateFilteredTaskList(keywords);
        } else {
            model.updateFilteredTaskListAll(keywords);
        }
        return new CommandResult(getMessageForTaskFoundShownSummary(
                model.getFilteredTaskList().size()));
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}

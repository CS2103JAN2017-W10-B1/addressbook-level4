//@@Author ShermineJong A0138474X
package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.Venue;


/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) n/TASKNAME [due/DUEDATE] [t/TIME] [#LISTNAME] "
            + "[d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [*f]"
            + "Example: " + COMMAND_WORD + " 1 due/17/3/2017 #CS2103T";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;
    private Task task;
    private Task oldTask;

    /**
     * @param filteredPersonListIndex the index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    public EditCommand(Task task, Task oldTask) {
        this.task = task;
        this.oldTask = oldTask;
        this.filteredTaskListIndex = 0;
        this.editTaskDescriptor = null;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        this.oldTask = (Task) taskToEdit;
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
            this.task = editedTask;
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAllTasks();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        TaskDate updatedDate = editTaskDescriptor.getDate().orElseGet(taskToEdit::getDate);
        TaskTime updatedTime = editTaskDescriptor.getTime().orElseGet(taskToEdit::getTime);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        Tag updatedTag = editTaskDescriptor.getTag().orElseGet(taskToEdit::getTag);
        Venue updatedVenue = editTaskDescriptor.getVenue().orElseGet(taskToEdit::getVenue);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        boolean isFavourite = editTaskDescriptor.getFavourite();

        return new Task(updatedName, updatedDate, updatedTime, updatedDescription,
                updatedTag, updatedVenue, updatedPriority, isFavourite);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<TaskDate> date = Optional.empty();
        private Optional<TaskTime> time = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<Tag> tag = Optional.empty();
        private Optional<Venue> venue = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private boolean isFavourite;

        private boolean isFavouriteEdited;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.date = toCopy.getDate();
            this.time = toCopy.getTime();
            this.description = toCopy.getDescription();
            this.tag = toCopy.getTag();
            this.venue = toCopy.getVenue();
            this.priority = toCopy.getPriority();
            this.isFavourite = toCopy.getFavourite();
        }

         /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(
                    this.name, this.date, this.time,
                    this.description, this.tag, this.venue, this.priority) || isFavouriteEdited;
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setDate(Optional<TaskDate> date) {
            assert date != null;
            this.date = date;
        }

        public Optional<TaskDate> getDate() {
            return date;
        }

        public void setTime(Optional<TaskTime> time) {
            assert time != null;
            this.time = time;
        }

        public Optional<TaskTime> getTime() {
            return time;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setTag(Optional<Tag> tag) {
            assert tag != null;
            this.tag = tag;
        }

        public Optional<Tag> getTag() {
            return tag;
        }

        public void setVenue(Optional<Venue> venue) {
            assert venue != null;
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return venue;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setIsFavourite(boolean isFavourite) {
            this.isFavourite = isFavourite;
            this.isFavouriteEdited = true;
        }

        private boolean getFavourite() {
            return isFavourite;
        }
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    public Task getTask(){
        return this.task;
    }

    @Override
    public CommandResult executeUndo() throws CommandException {
        try {
            model.deleteTask(task);
            model.addTask(oldTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException e) {
            assert false : "The target person cannot be missing";
        }
        model.updateFilteredListToShowAllTasks();
        return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }

    @Override
    public Command getUndoCommand() {
        return new EditCommand(this.task, this.oldTask);
    }
}

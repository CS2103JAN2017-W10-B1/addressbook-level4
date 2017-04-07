///@@author A0138474X
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyEvent;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTask.FinishProperty;
import seedu.address.model.task.RecurringEvent;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.Venue;


/**
 * Edits the details of an existing task in the Dueue.
 */
public class EditCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String COMMAND_EDIT = "edit command";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index given.\n"
            + "Parameters: INDEX [n/TASK_NAME]\n [due/DUE_DATE] [dueT/DUE_TIME]"
            + "[start/START_DATE] [startT/STAR_TTIME] [#LIST_NAME] "
            + "[d/DESCRIPTION] [@VENUE] [p/PRIORITY_LEVEL] [*f/*u]\n"
            + "Example: " + COMMAND_WORD + " 1 due/17/3/2017 #CS2103T";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    protected final int filteredTaskListIndex;
    protected final EditTaskDescriptor editTaskDescriptor;
    protected ReadOnlyTask task;
    protected Task oldTask;
    protected boolean isSuccess;
    private boolean isUndo = false;

    /**
     * @param filteredPersonListIndex the index of the task in the filtered task list to edit
     * @param editPersonDescriptor details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
        this.isSuccess = false;
    }

    // constructor for undoing edit command
    public EditCommand(ReadOnlyTask task, Task oldTask) {
        this.task = task;
        this.oldTask = oldTask;
        this.filteredTaskListIndex = 0;
        this.editTaskDescriptor = null;
        isUndo = true;
    }

    @Override
    public CommandResult execute() throws CommandException {
        processTask();
        return execute(String.format(MESSAGE_EDIT_TASK_SUCCESS, task.getName()));
    }

    /*
     * update the Task using the index if not undoing a task
     * if undoing a task update by delete and add
     */
    public CommandResult execute(String message) throws CommandException {

        if (!isUndo) {
            try {
                Task editedTask = createEditedTask(task, editTaskDescriptor);
                model.updateTask(filteredTaskListIndex, editedTask);
                isSuccess = true;
                task = editedTask;
                int taskIndex = model.getFilteredTaskList().indexOf(editedTask);
                EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(taskIndex));
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                isSuccess = false;
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            } catch (IllegalValueException e) {
                throw new CommandException(e.getMessage());
            }
        } else {
            try {
                model.deleteTask(this.task);
                model.addTask(this.oldTask);
                ReadOnlyTask temp;
                temp = this.task;
                this.task = this.oldTask;
                this.isSuccess = true;
                this.oldTask = (Task) temp;
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            } catch (TaskNotFoundException e) {
                assert false : "The target task cannot be missing";
            }
        }
        model.updateFilteredListToShowAllUnfinishedTasks();
        String displayMessage;
        if (isUndo) {
            displayMessage = CommandFormatter.undoMessageFormatter(message, getUndoCommandWord());
        } else {
            displayMessage = CommandFormatter.undoFormatter(message, COMMAND_EDIT);
        }
        return new CommandResult(displayMessage);
    }

    private void processTask() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        task = lastShownList.get(filteredTaskListIndex);
        oldTask = createTask(task);
    }

    protected Task createTask(ReadOnlyTask task) {
        Task newTask = null;
        if (task.isEvent() && task.isRecurring()) {
            try {
                newTask = new RecurringEvent(task);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        } else if (task.isEvent()) {
            try {
                newTask = new Event(task);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        } else if (task.isRecurring()) {
            newTask = new RecurringTask(task);
        } else {
            newTask = new Task(task);
        }
        return newTask;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code taskToEdit}
     * edited with {@code editPersonDescriptor}.
     * @throws IllegalValueException
     */
    protected static Task createEditedTask(ReadOnlyTask taskToEdit,
            EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        assert taskToEdit != null;

        // get the common detail field
        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        TaskDate updatedDueDate = editTaskDescriptor.getDue().orElseGet(taskToEdit::getDate);
        updatedDueDate = new TaskDate(updatedDueDate.getValue());
        TaskTime updatedDueTime = editTaskDescriptor.getDueTime().orElseGet(taskToEdit::getTime);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        Tag updatedTag = editTaskDescriptor.getTag().orElseGet(taskToEdit::getTag);
        Venue updatedVenue = editTaskDescriptor.getVenue().orElseGet(taskToEdit::getVenue);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        FinishProperty isFinished = taskToEdit.getFinished();
        boolean isFavourite;
        // check the fravourite
        if (editTaskDescriptor.getIsFavouriteEdited()) {
            isFavourite = editTaskDescriptor.getFavourite();
        } else {
            isFavourite = taskToEdit.isFavorite();
        }
        // check the RecurringMode
        RecurringMode mode = null;
        if (taskToEdit.isRecurring() || editTaskDescriptor.getRecurringMode() != null) {
            if (editTaskDescriptor.getRecurringMode() != null) {
                mode = editTaskDescriptor.getRecurringMode();
            } else {
                if (taskToEdit.isEvent()) {
                    mode = ((RecurringEvent) taskToEdit).getMode();
                } else {
                    mode = ((RecurringTask) taskToEdit).getMode();
                }
            }
        }
        /*
         *If the Task is an Event or the user specify the startDate or startTime
         *Then get the Event property
         *Else it is a task
         */
        if (editTaskDescriptor.updatedEvent(editTaskDescriptor.getStart()) || taskToEdit.isEvent()) {

            // if the task is an event and the user wants to keep it as an event or the user edit it to become an event
            if (taskToEdit.isEvent() && !(editTaskDescriptor.getStart().isPresent() &&
                    editTaskDescriptor.getStart().get().getValue().isEmpty())) {

                TaskDate updatedStartDate = editTaskDescriptor.getStart()
                        .orElseGet(((ReadOnlyEvent) taskToEdit)::getStartDate);
                TaskTime updatedStartTime = editTaskDescriptor.getStartTime()
                        .orElseGet(((ReadOnlyEvent) taskToEdit)::getStartTime);

                // if the Event is recurring or the user edit it to become recurring
                if (taskToEdit.isRecurring() || editTaskDescriptor.getRecurringMode() != null) {
                    return new RecurringEvent(updatedName, updatedStartDate, updatedStartTime, updatedDueDate,
                            updatedDueTime, updatedDescription, updatedTag, updatedVenue, updatedPriority,
                            isFavourite, isFinished, mode);
                } else {
                    return new Event(updatedName, updatedStartDate, updatedStartTime, updatedDueDate, updatedDueTime,
                            updatedDescription, updatedTag, updatedVenue, updatedPriority, isFavourite, isFinished);
                }

            } else {
                // the user wants to convert a event into a task

                // if the Task is recurring or the user edit it to become recurring
                if (taskToEdit.isRecurring() || editTaskDescriptor.getRecurringMode() != null) {
                    return new RecurringTask(updatedName, updatedDueDate, updatedDueTime, updatedDescription,
                            updatedTag, updatedVenue, updatedPriority, isFavourite, isFinished, mode);
                } else {
                    return new Task(updatedName, updatedDueDate, updatedDueTime, updatedDescription,
                            updatedTag, updatedVenue, updatedPriority, isFavourite, isFinished);
                }
            }
        } else {
            // if the Task is recurring or the user edit it to become recurring
            if ((taskToEdit.isRecurring() || editTaskDescriptor.getRecurringMode() != null) && !taskToEdit.isEvent()) {
                return new RecurringTask(updatedName, updatedDueDate, updatedDueTime,
                        updatedDescription, updatedTag, updatedVenue, updatedPriority, isFavourite, mode);
            } else {
                return new Task(updatedName, updatedDueDate, updatedDueTime, updatedDescription,
                        updatedTag, updatedVenue, updatedPriority, isFavourite, isFinished);
            }
        }
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        return execute(message);
    }

    // get the edit command to undo this edit command
    @Override
    public Command getUndoCommand() {
        if (isSuccess) {
            return new EditCommand(this.task, this.oldTask);
        } else {
            return null;
        }
    }

    @Override
    public String getUndoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }

    @Override
    public String getRedoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }
}

///@@author A0138474X
package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
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
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.Venue;


/**
 * Edits the details of an existing task in the address book.
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

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;
    private ReadOnlyTask task;
    private Task oldTask;
    private boolean isSuccess;

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

    public EditCommand(ReadOnlyTask task, Task oldTask) {
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
        this.oldTask = createTask(taskToEdit);

        try {
            Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
            model.updateTask(filteredTaskListIndex, editedTask);
            this.isSuccess = true;
            this.task = editedTask;
            int taskIndex = model.getFilteredTaskList().indexOf(editedTask);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(taskIndex));
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            this.isSuccess = false;
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
        model.updateFilteredListToShowAllUnfinishedTasks();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit.getName()));
    }

    private Task createTask(ReadOnlyTask task) {
        Task newTask = null;
        if (task.isEvent()) {
            try {
                newTask = new Event(task.getName(), ((Event) task).getStartDate(),
                        ((Event) task).getStartTime(), task.getDate(), task.getTime(), task.getDescription(),
                        task.getTag(), task.getVenue(), task.getPriority(), task.isFavorite(),
                        FinishProperty.UNFINISHED);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        } else if (task.isRecurring()) {
            newTask = new RecurringTask(task.getName(), task.getDate(), task.getTime(), task.getDescription(),
                        task.getTag(), task.getVenue(), task.getPriority(), task.isFavorite(),
                        ((RecurringTask) task).getMode());
        } else {
            newTask = new Task(task.getName(), task.getDate(), task.getTime(), task.getDescription(),
                    task.getTag(), task.getVenue(), task.getPriority(), task.isFavorite(),
                    FinishProperty.UNFINISHED);
        }
        return newTask;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code taskToEdit}
     * edited with {@code editPersonDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
            EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        TaskDate updatedDueDate = editTaskDescriptor.getDue().orElseGet(taskToEdit::getDate);
        TaskTime updatedDueTime = editTaskDescriptor.getDueTime().orElseGet(taskToEdit::getTime);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        Tag updatedTag = editTaskDescriptor.getTag().orElseGet(taskToEdit::getTag);
        Venue updatedVenue = editTaskDescriptor.getVenue().orElseGet(taskToEdit::getVenue);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        FinishProperty isFinished = taskToEdit.getFinished();
        boolean isFavourite;
        if (editTaskDescriptor.getIsFavouriteEdited()) {
            isFavourite = editTaskDescriptor.getFavourite();
        } else {
            isFavourite = taskToEdit.isFavorite();
        }
        if (taskToEdit.isRecurring() || editTaskDescriptor.getRecurringMode() != null) {
            RecurringMode mode;
            if (editTaskDescriptor.getRecurringMode() != null) {
                mode = editTaskDescriptor.getRecurringMode();
            } else {
                mode = ((RecurringTask) taskToEdit).getMode();
            }
            return new RecurringTask(updatedName, updatedDueDate, updatedDueTime,
                    updatedDescription, updatedTag, updatedVenue, updatedPriority, isFavourite, mode);
        } else if (editTaskDescriptor.updatedEvent(editTaskDescriptor.getStart()) || taskToEdit.isEvent()) {
            if (taskToEdit.isEvent() && !(editTaskDescriptor.getStart().isPresent() &&
                    editTaskDescriptor.getStart().get().getValue().isEmpty())) {
                TaskDate updatedStartDate = editTaskDescriptor.getStart()
                        .orElseGet(((ReadOnlyEvent) taskToEdit)::getStartDate);
                TaskTime updatedStartTime = editTaskDescriptor.getStartTime()
                        .orElseGet(((ReadOnlyEvent) taskToEdit)::getStartTime);
                return new Event(updatedName, updatedStartDate, updatedStartTime, updatedDueDate, updatedDueTime,
                        updatedDescription, updatedTag, updatedVenue, updatedPriority, isFavourite, isFinished);
            } else if (!taskToEdit.isEvent() && (editTaskDescriptor.getStart().isPresent() &&
                    !editTaskDescriptor.getStart().get().getValue().isEmpty())) {
                TaskDate updatedStartDate = editTaskDescriptor.getStart().orElse(new TaskDate(""));
                TaskTime updatedStartTime = editTaskDescriptor.getStartTime().orElse(new TaskTime(""));
                return new Event (updatedName, updatedStartDate, updatedStartTime, updatedDueDate, updatedDueTime,
                        updatedDescription, updatedTag, updatedVenue, updatedPriority, isFavourite, isFinished);
            } else {
                return new Task(updatedName, updatedDueDate, updatedDueTime, updatedDescription,
                        updatedTag, updatedVenue, updatedPriority, isFavourite, isFinished);
            }
        } else {
            return new Task(updatedName, updatedDueDate, updatedDueTime, updatedDescription,
                    updatedTag, updatedVenue, updatedPriority, isFavourite, isFinished);
        }
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<TaskDate> due = Optional.empty();
        private Optional<TaskTime> dueTime = Optional.empty();
        private Optional<TaskDate> start = Optional.empty();
        private Optional<TaskTime> startTime = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<Tag> tag = Optional.empty();
        private Optional<Venue> venue = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private RecurringMode recurringMode;
        private boolean isFavourite;

        private boolean isFavouriteEdited;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.due = toCopy.getDue();
            this.dueTime = toCopy.getDueTime();
            this.start = toCopy.getStart();
            this.startTime = toCopy.getStartTime();
            this.description = toCopy.getDescription();
            this.tag = toCopy.getTag();
            this.venue = toCopy.getVenue();
            this.priority = toCopy.getPriority();
            this.isFavourite = toCopy.getFavourite();
            this.isFavouriteEdited = toCopy.getIsFavouriteEdited();
            this.recurringMode = toCopy.getRecurringMode();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(
                    this.name, this.due, this.dueTime, this.start, this.startTime,
                    this.description, this.tag, this.venue, this.priority) ||
                    this.isFavouriteEdited || (this.recurringMode != null);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setDue(Optional<TaskDate> due) {
            assert due != null;
            this.due = due;
        }

        public void setStart(Optional<TaskDate> start) {
            assert start != null;
            this.start = start;
        }

        public Optional<TaskDate> getDue() {
            return due;
        }

        public Optional<TaskDate> getStart() {
            return start;
        }

        public void setDueTime(Optional<TaskTime> dueTime) {
            assert dueTime != null;
            this.dueTime = dueTime;
        }

        public void setStartTime(Optional<TaskTime> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<TaskTime> getDueTime() {
            return dueTime;
        }

        public Optional<TaskTime> getStartTime() {
            return startTime;
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
            if (isFavourite) {
                this.isFavouriteEdited = true;
                this.isFavourite = true;
            }
        }

        private boolean getFavourite() {
            return this.isFavourite;
        }

        private boolean getIsFavouriteEdited() {
            return this.isFavouriteEdited;
        }

        public void setIsUnfavourite (boolean isUnFavourite) {
            if (isUnFavourite) {
                this.isFavouriteEdited = true;
                this.isFavourite = false;
            }
        }

        public boolean updatedEvent(Optional<TaskDate> start) {
            return start.isPresent();
        }

        public void setRecurringMode(Optional<String> ocurrence) {
            if (ocurrence.isPresent()) {
                String ocurring = ocurrence.orElse("");
                if (ocurring.contains("daily")) {
                    this.recurringMode = RecurringMode.DAY;
                } else if (ocurring.contains("weekly")) {
                    this.recurringMode = RecurringMode.WEEK;
                } else if (ocurring.contains("monthly")) {
                    this.recurringMode = RecurringMode.MONTH;
                }
            } else {
                this.recurringMode = null;
            }
        }

        public RecurringMode getRecurringMode() {
            return this.recurringMode;
        }
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        assert model != null;
        try {
            model.deleteTask(this.task);
            model.addTask(this.oldTask);
            ReadOnlyTask temp;
            temp = this.task;
            this.task = this.oldTask;
            this.oldTask = (Task) temp;
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
        model.updateFilteredListToShowAllUnfinishedTasks();
        this.isSuccess = true;
        return new CommandResult(CommandFormatter.undoMessageFormatter(message, getUndoCommandWord()));
    }

    @Override
    public Command getUndoCommand() {
        if (isSuccess) {
            return new EditCommand(this.task, this.oldTask);
        } else {
            return new IncorrectCommand(null);
        }
    }

    @Override
    public String getUndoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }

}

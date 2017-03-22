# Dueue - User Guide

By : `Team CS2103JAN2017-W10-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `dueue.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Dueue.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**`  add laundry due/10/01 t/16:00 #personal d/wash clothes p/trivial @B1 *f` :
     adds a task to Dueue named `laundry` which is due on `10/01` at `16:00` under list `personal` with description `wash clothes` at venue `B1` with priority `trivial` and star it as `favourite`.
   * **`delete`**` 3` : deletes the task with index 3 as shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#2-features) section below for details of each command.<br>

## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

View specific help messages<br>
Format: `help COMMAND_KEYWORD`

> * The relevent help message of the command will be displayed in result display box.
> * When the Dueue app is launched every time, a brief help message will show in the result display box.
> * The specific help message is also shown if you enter an incorrect command e.g. `delete INVALID_TASK_INDEX`

Examples:

*`help add`<br>
  Display help message of add command for the user.

### 2.2. Adding a typical task: `add`

Add a task to Dueue<br>
Format: `add n/TASKNAME [due/DATE] [t/TIME] [#LIST] [d/DESCRIPTION] [@VENUE] [p/PRIORITY] [*f]`

> * Tasks cannot be categorized under multiple LISTs.
> * Tasks must be added with TASKNAME.
> * The required field for a task is TASKNAME, which must be entered as the first field, while other fields are optional and can be entered in random sequence.
> * TIME of each task can only be one time point, e.g. 16:00.
> * DATE must be in the format of dd/mm/yyyy or dd/mm.
> * PRIORITY will determine the sequence of the tasks displayed.
> * LIST will be automatically created if it is non-existent.
> * By default, task will be added to default list `Inbox`, unless [#LIST] is specified.
> * If no fields are entered, help message for add command will appear.
> * User can choose to replace the priority levels `IMPORTANT`, `NORMAL` and `TRIVIAL` by 3, 2 and 1.

Examples:

* `add n/laundry due/every sunday #personal d/wash clothes @B1 *f`<br>
  Add a task with taskname `laundry` due `every sunday` under the list `personal` with description `wash clothes` at the venue `B1` and mark it as favourite.
* `add n/2103lecture due/every friday t/4pm #study d/go for lecture p/3 @iCube`<br>
  Add a task with taskname `2103lecture` due `every friday` at time `4pm` under the list `study` with description `go for lecture` at the venue `iCube` with priority level `IMPORTANT`.

### 2.3. Adding an event: `add`
//TO DO

### 2.4. Finishing task(s) : `finish`

Mark a task as finished in Dueue.<br>
Format: `finish TASK_INDEX...`

> * Finishes the task(s) at the specified `TASKINDEX`. <br>
> * The index refers to the index number shown in current list view.<br>
> * To finish multiple tasks, indices must be seperated by whitespaces.
> * Each index **must be a positive integer** 1, 2, 3, ...
> * A finished task will automatically dissappeared from curret list view.
> * Finished tasks can be viewed under the "list [LIST_INDEX/LIST_NAME] finished" command.
> * Finished tasks can be viewed together with unfinished tasks under the "list [LIST_INDEX/LIST_NAME] all" command.

Examples:

* `finish 1`<br>
  Mark the task with index 1 under current list view as finished.

* `finish 2 3 5`<br>
  Mark the tasks with index 2,3 and 5 under current list view as finished.

### 2.5. Listing all tasks : `list`

Shows a list of tasks in Dueue filtered by specifications given<br>
Format: `list [LIST_INDEX/LIST_NAME] [all/favorite/finished]`<br>

Shows all unfinished tasks in Dueue<br>
Format: `list`

Shows all finished tasks in Dueue<br>
Format: `list finished`

Shows all tasks in Dueue (finished and unfinished)<br>
Format: `list all`

Shows all tasks in LIST (finished and unfinished)<br>
Format: `list LIST_INDEX/LIST_NAME all`

Shows all unfinished tasks in LIST<br>
Format: `list LIST_INDEX/LIST_NAME`

Shows all finished tasks in LIST<br>
Format: `list LIST_INDEX/LIST_NAME finished`

> * Specification parameters must be entered following the defined sequence.

Examples:

* `list`<br>
  Lists all unfinished tasks
* `list all`<br>
  Lists all unfinished and finished tasks
* `list work`<br>
  List all unfinished tasks in list `work`
* `list work all`<br>
  List all unfinished and finished tasks in list `work`
* `list inbox finished`<br>
  List all finished tasks in list `inbox`
* `list study favourite`<br>
  List all favourite tasks in list `study`

### 2.6. Editing task(s) : `edit`

Edits existing task(s) in Dueue<br>
Format: `edit TASKINDEX ... [n/NAME] [due/DUEDATE] [t/TIME] [#LIST] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [*f/*u]`

> * Edits the task at the specified `INDEX`.
> * The index refers to the index number shown in the current list view.<br>
> * The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * For the field "favorite", use "*f" to mark as favorte and use "*u" to unfavorite a task.
> * Existing values will be edited to the input values.
> * You can remove details by typing the field prefix only, e.g.`edit TASK_INDEX due/`.
> * To edit multiple entries, indices must be seperated by whitespaces.
> * Specifications of fields for task can be entered in any order.

Examples:

* `edit 1 Laundry due/10/01 @`<br>
  Edits the name, due date and the venue of the 1st task to be `Laundry`, `10/01` and remove the venue respectively.

* `edit 2 due/ *u`<br>
  Remove the due date of the task with `INDEX` 2 and make it not a favourite.

### 2.7. View certain tasks: `view`

View tasks due on a specified date.<br>
Format: `view dueon/DATE`

View tasks due by a specified date.<br>
Format: `view dueby/DATE`

> * The date has to be specified in a certain format.<br>

Examples:

* `view dueon/10/04`<br>
  Returns a list of tasks due on 10/04
* `view dueby/10/04`<br>
  Returns a list of tasks due by 10/04

### 2.8. Deleting task(s) or list(s) : `delete`

Deletes the specified task(s) from Dueue.<br>
Format: `delete TASKINDEX...`

> * Deletes the task at the specified `TASKINDEX` or `LISTINDEX`. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * To delete multiple entries, indices must be seperated by whitespaces.
> * Each index **must be a positive integer** 1, 2, 3, ...
> * When a repeated task is deleted, user will need to confirm whether it should be deleted for once or deleted forever (stop repeating).

Examples:

* `delete 2`<br>
  Deletes the 2nd task/list in current list view.
* `delete 1 2 3`<br>
  Deletes the 1st, 2nd and 3rd tasks/lists in current list view.

### 2.10. Undo latest command: `undo`

Undo the immediately preceding command.<br>
Format: `undo`

> * Undo the latest mutating command, including `add`, `delete`, `edit` and `finish`.
> * Definition of **mutating** : making changes to the fields of tasks
> * Cannot undo more than once in succession.

Examples:

* `undo`<br>
  Latest command is reversed.

### 2.11. Reverse previous `undo` command: `redo`

Reverse the immediately preceding undo command.<br>
Format: `redo`

> * Reverse `undo` command if user made a mistake.
> * Can only redo right after an `undo` command.
> * Cannot redo more than once.

Examples:

* `redo`<br>
  Previous `undo` command is reversed.

### 2.12. Clearing all tasks in Dueue : `clear`

Clears all tasks from Dueue.<br>
Format: `clear`

### 2.13. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.14. Saving the data

Dueue data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Dueue folder.

## 4. Command Summary

* **Add**  `add n/TASKNAME [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [FAVOURITE]` <br>
  e.g. `add n/laundry due/every sunday #personal d/wash clothes @B1 p/IMPORTANT *f`

* **Clear** : `clear`
  e.g. `clear`

* **Delete** : `delete TASKINDEX...` <br>
   e.g. `delete 3`

* **Edit** : `edit TASKINDEX ... [n/NAME] [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [*f/*u]` <br>
  e.g.`update 1 Laundry due/every sunday @`

* **Finish** : `finish TASKINDEX...` <br>
   e.g. `finish 5`

* **Help** : `help COMMAND_KEYWORD` <br>
  e.g. `help add`

* **List** : `list [LIST_INDEX/LIST_NAME] [all/favorite/finished]` <br>
  e.g. `list work favorite`

* **Redo** : `redo` <br>
  e.g. `redo`

* **Undo** : `undo` <br>
  e.g. `undo`

* **View** : `view DUETYPE/DUEDATE`<br>
  e.g. `view dueon/today`

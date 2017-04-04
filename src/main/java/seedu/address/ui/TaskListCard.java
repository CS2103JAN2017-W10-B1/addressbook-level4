//@@author A0147996E
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyEvent;
import seedu.address.model.task.ReadOnlyRecurringTask;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTask.RecurringProperty;

/**
 * Taskcard defines format for individual task view.
 */
public class TaskListCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox taskCardPane;
    @FXML
    private Label tag;
    @FXML
    private Label index;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label pastDue;
    @FXML
    private Label description;
    @FXML
    private Label venue;
    @FXML
    private Label priority;
    @FXML
    private Label isFavorite;
    @FXML
    private Label isFinished;
    @FXML
    private Label isRecurring;

    public TaskListCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        index.setText(displayedIndex + ". ");
        name.setText(task.getName().getDisplayText());
        description.setText(task.getDescription().getDisplayText());
        time.setText(task.getTime().getDisplayText());
        date.setText(task.getDate().getDisplayText());
        venue.setText(task.getVenue().getDisplayText());
        priority.setText(task.getPriority().getDisplayText());
        tag.setText(task.getTag().getDisplayText());
        isFavorite.setText(task.getFavoriteText());
        isFinished.setText(task.getFinishedText());
        setPastDue(task);
        setEventFields(task);
        setRecurringTaskFields(task);
    }

    private void setPastDue(ReadOnlyTask task) {
        pastDue.setText("");
        if (!task.isFinished()) {
            pastDue.setText(task.getDate().getPastDueDisplayedText());
        }
    }

    private void setEventFields(ReadOnlyTask task) {
        startTime.setText("");
        startDate.setText("");
        if (task instanceof ReadOnlyEvent) {
            startDate.setText(((ReadOnlyEvent) task).getStartDate().getStartDisplayText());
            startTime.setText(((ReadOnlyEvent) task).getStartTime().getStartDisplayText());
        }
    }

    private void setRecurringTaskFields(ReadOnlyTask task) {
        isRecurring.setText("");
        if (task.getRecurringProperty() == RecurringProperty.RECURRING) {
            assert task instanceof ReadOnlyRecurringTask;
            String mode = ((ReadOnlyRecurringTask) task).getRecurringPeriod();
            isRecurring.setText(mode);
        }
    }
}

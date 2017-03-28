//@@author A0147996E
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Event;
import seedu.address.model.task.ReadOnlyTask;

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
        if (task.getDate().isPastDue()) {
            pastDue.setText("Past due!");
        } else {
            pastDue.setText("");
        }
        startTime.setText("");
        startDate.setText("");
        if (task instanceof Event) {
            startDate.setText(((Event) task).getStartDate().getDisplayText());
            startTime.setText(((Event) task).getStartTime().getDisplayText());
        }
    }
}


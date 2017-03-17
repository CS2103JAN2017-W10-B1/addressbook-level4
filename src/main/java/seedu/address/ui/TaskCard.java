//@@author Matilda_yxx A0147996E
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Taskcard defines format for individual task view.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
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
    private Label description;
    @FXML
    private Label venue;
    @FXML
    private Label priority;
    @FXML
    private Label isFavorite;
    @FXML
    private Label isFinished;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
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
    }
}


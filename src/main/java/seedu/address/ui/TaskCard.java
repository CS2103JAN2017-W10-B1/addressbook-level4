package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

/**
 * @author yanxiaoxuan
 *
 * Taskcard defines format for individual task view.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private Label time;
    @FXML
    private Label venue;
    @FXML
    private Label priority;
    @FXML
    private Label isFavorite;

    public TaskCard(ReadOnlyTask task) {
        super(FXML);
        name.setText(task.getName().fullName);
        description.setText(task.getDescription().value);
        time.setText(task.getTime().value);
        venue.setText(task.getVenue().value);
        priority.setText(task.getPriority().value);
        isFavorite.setText(String.valueOf(task.isFavorite()));
    }
}


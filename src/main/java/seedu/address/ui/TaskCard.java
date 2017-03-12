package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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
    private HBox cardPane;
//    @FXML
//    private Label tag;
   @FXML
   private Label index;
    @FXML
    private Label name;
//  @FXML
//  private Label date;
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

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        index.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        description.setText(task.getDescription().value);
        time.setText("Time: " + task.getTime().value);
//     date.setText("Date: " + task.getDate().value);
        venue.setText("Venue: " + task.getVenue().value);
        priority.setText("Priority: " + task.getPriority().value);
        isFavorite.setText("Favorite: " + String.valueOf(task.isFavorite()));
//     tag.setText(task.getTag().tagName);
    }
}


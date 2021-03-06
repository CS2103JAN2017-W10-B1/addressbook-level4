//@@author A0147996E
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * TagListCard defines format for individual tag view.
 */
public class TagListCard extends UiPart<Region> {

    private static final String FXML = "TagListCard.fxml";

    @FXML
    private HBox tagCardPane;
    @FXML
    private Label index;
    @FXML
    private Label name;

    public TagListCard(Tag taskList, int displayedIndex) {
        super(FXML);
        index.setText(displayedIndex + ". ");
        name.setText(taskList.getName());
    }
}


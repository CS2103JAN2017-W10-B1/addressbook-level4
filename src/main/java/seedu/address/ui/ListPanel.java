
//@@author Matilda_yxx A0147996E
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ListPanelSelectionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of tasks.
 */
public class ListPanel extends UiPart<Region> {

    private final Logger logger = LogsCenter.getLogger(ListPanel.class);
    private static final String FXML = "ListPanel.fxml";

    @FXML
    private ListView<Tag> listListView;

    public ListPanel(AnchorPane listPlaceholder, ObservableList<Tag> listList) {
        super(FXML);
        setConnections(listList);
        addToPlaceholder(listPlaceholder);
    }

    private void setConnections(ObservableList<Tag> Tags) {
        listListView.setItems(Tags);
        listListView.setCellFactory(listView -> new ListListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        listListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new ListPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            listListView.scrollTo(index);
            listListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class ListListViewCell extends ListCell<Tag> {

        @Override
        protected void updateItem(Tag Tag, boolean empty) {
            super.updateItem(Tag, empty);

            if (empty || Tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ListCard(Tag, getIndex() + 1).getRoot());
            }
        }
    }
}


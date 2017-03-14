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
import seedu.address.model.tasklist.TaskList;

/**
 * Panel containing the list of tasks.
 */
public class ListPanel extends UiPart<Region> {
   
	private final Logger logger = LogsCenter.getLogger(ListPanel.class);
    private static final String FXML = "ListPanel.fxml";

    @FXML
    private ListView<TaskList> listListView;

    public ListPanel(AnchorPane listPlaceholder, ObservableList<TaskList> listList) {
        super(FXML);
        setConnections(listList);
        addToPlaceholder(listPlaceholder);
    }

    private void setConnections(ObservableList<TaskList> taskList) {
        listListView.setItems(taskList);
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

    class ListListViewCell extends ListCell<TaskList> {

        @Override
        protected void updateItem(TaskList taskList, boolean empty) {
            super.updateItem(taskList, empty);

            if (empty || taskList == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ListCard(taskList, getIndex() + 1).getRoot());
            }
        }
    }

}


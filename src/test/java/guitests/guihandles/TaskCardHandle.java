package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATE_FIELD_ID = "#address";
    private static final String TIME_FIELD_ID = "#phone";
    private static final String DESCRIPTION_FIELD_ID = "#email";
    private static final String TAG_FIELD_ID = "#tag";
    private static final String VENUE_FIELD_ID = "#venue";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String FAVORITE_FIELD_ID = "#favorite";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }

    public String getTime() {
        return getTextFromLabel(TIME_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getTag() {
        return getTextFromLabel(TAG_FIELD_ID);
    }

    public String getVenue() {
        return getTextFromLabel(VENUE_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String isFavorite() {
        return getTextFromLabel(FAVORITE_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return getFullName().equals(task.getName().fullName)
                && getDate().equals(task.getDate().value)
                && getTime().equals(task.getTime().value)
                && getDescription().equals(task.getDescription().value)
                && getTag().equals(task.getTag())
                && getVenue().equals(task.getVenue().value)
                && getPriority().equals(task.getPriority().value)
                && isFavorite().equals(task.isFavorite());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getDate().equals(handle.getDate())
                    && getTime().equals(handle.getTime())
                    && getDescription().equals(handle.getDescription())
                    && getTag().equals(handle.getTag())
                    && getVenue().equals(handle.getVenue())
                    && getPriority().equals(handle.getPriority())
                    && isFavorite().equals(handle.isFavorite());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName();
    }
}

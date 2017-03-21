//@@author Matilda_yxx A0147996E
package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the tag list.
 */
public class TagListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TAG_LIST_VIEW_ID = "#tagListView";

    public TagListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Tag> getSelectedTags() {
        ListView<Tag> tagList = getListView();
        return tagList.getSelectionModel().getSelectedItems();
    }

    public ListView<Tag> getListView() {
        return getNode(TAG_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the tag details correctly and in correct order.
     * @param tags A list of tag in the correct order.
     * @throws IllegalValueException
     * @throws IllegalArgumentException
     */
    public boolean isListMatching(Tag... tags) throws IllegalArgumentException, IllegalValueException {
        return this.isListMatching(0, tags);
    }

    /**
     * Returns true if the list is showing the tag details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tags A list of tag in the correct order.
     * @throws IllegalValueException
     */
    public boolean isListMatching(int startPosition, Tag... tags) 
            throws IllegalArgumentException, IllegalValueException {
        if (tags.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tags");
        }
        assertTrue(this.containsInOrder(startPosition, tags));
        for (int i = 0; i < tags.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTag(getTagCardHandle(startPosition + i), tags[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tags} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, Tag... tags) {
        List<Tag> tagsInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tags.length > tagsInList.size()) {
            return false;
        }

        // Return false if any of the tags doesn't match
        for (int i = 0; i < tags.length; i++) {
            if (!tagsInList.get(startPosition + i).getName().equals(tags[i].getName())) {
                return false;
            }
        }
        return true;
    }

    public TagCardHandle navigateToTag(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<Tag> tag = getListView().getItems().stream()
                                                    .filter(p -> p.getName().equals(name))
                                                    .findAny();
        if (!tag.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToTag(tag.get());
    }

    /**
     * Navigates the listview to display and select the tag.
     */
    public TagCardHandle navigateToTag(Tag tag) {
        int index = getTagIndex(tag);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTagCardHandle(tag);
    }


    /**
     * Returns the position of the tag given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTagIndex(Tag targetTag) {
        List<Tag> tagsInList = getListView().getItems();
        for (int i = 0; i < tagsInList.size(); i++) {
            if (tagsInList.get(i).getName().equals(targetTag.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a tag from the list by index
     */
    public Tag getTag(int index) {
        return getListView().getItems().get(index);
    }

    public TagCardHandle getTagCardHandle(int index) throws IllegalValueException {
        return getTagCardHandle(new Tag(getListView().getItems().get(index)));
    }

    public TagCardHandle getTagCardHandle(Tag tag) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> tagCardNode = nodes.stream()
                .filter(n -> new TagCardHandle(guiRobot, primaryStage, n).isSameTag(tag))
                .findFirst();
        if (tagCardNode.isPresent()) {
            return new TagCardHandle(guiRobot, primaryStage, tagCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTags() {
        return getListView().getItems().size();
    }
}

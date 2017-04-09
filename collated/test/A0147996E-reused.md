# A0147996E-reused
###### /java/guitests/CommandBoxTest.java
``` java
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.ui.CommandBox;

public class CommandBoxTest extends TaskManagerGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "scroll 3";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    @Before
    public void setUp() {
        defaultStyleOfCommandBox = new ArrayList<>(commandBox.getStyleClass());
        assertFalse("CommandBox default style classes should not contain error style class.",
                    defaultStyleOfCommandBox.contains(CommandBox.ERROR_STYLE_CLASS));

        // build style class for error
        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_commandSucceeds_textClearedAndStyleClassRemainsTheSame() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandFails_textStaysAndErrorStyleClassAdded() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandSucceedsAfterFailedCommand_textClearedAndErrorStyleClassRemoved() {
        // add error style to simulate a failed command
        commandBox.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);

        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

}
```
###### /java/guitests/ErrorDialogGuiTest.java
``` java
package guitests;

import static junit.framework.TestCase.assertTrue;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.AlertDialogHandle;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;

public class ErrorDialogGuiTest extends TaskManagerGuiTest {

    @Test
    public void showErrorDialogs() throws InterruptedException {
        //Test DataSavingExceptionEvent dialog
        raise(new DataSavingExceptionEvent(new IOException("Stub")));
        AlertDialogHandle alertDialog = mainGui.getAlertDialog("File Op Error");
        assertTrue(alertDialog.isMatching("Could not save data", "Could not save data to file" + ":\n"
                                                                         + "java.io.IOException: Stub"));
    }
}
```
###### /java/guitests/guihandles/AlertDialogHandle.java
``` java
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import seedu.address.ui.UiManager;

/**
 * A handle for the AlertDialog of the UI
 */
public class AlertDialogHandle extends GuiHandle {


    public AlertDialogHandle(GuiRobot guiRobot, Stage primaryStage, String dialogTitle) {
        super(guiRobot, primaryStage, dialogTitle);
    }

    public boolean isMatching(String headerMessage, String contentMessage) {
        assert intermediateStage.isPresent() : "Alert dialog is not present";
        DialogPane dialogPane = getNode("#" + UiManager.ALERT_DIALOG_PANE_FIELD_ID);
        boolean isMatching = dialogPane.getHeaderText().equals(headerMessage)
                && dialogPane.getContentText().equals(contentMessage);
        return isMatching;
    }
}
```
###### /java/guitests/guihandles/CommandBoxHandle.java
``` java
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * A handle to the Command Box in the GUI.
 */
public class CommandBoxHandle extends GuiHandle {

    private static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }

    /**
     * Clicks on the TextField.
     */
    public void clickOnTextField() {
        guiRobot.clickOn(COMMAND_INPUT_FIELD_ID);
    }

    public void enterCommand(String command) {
        setTextField(COMMAND_INPUT_FIELD_ID, command);
    }

    public String getCommandInput() {
        return getTextFieldText(COMMAND_INPUT_FIELD_ID);
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     */
    public void runCommand(String command) {
        enterCommand(command);
        pressEnter();
        guiRobot.sleep(200); //Give time for the command to take effect
    }

    public ObservableList<String> getStyleClass() {
        return getNode(COMMAND_INPUT_FIELD_ID).getStyleClass();
    }
}
```
###### /java/guitests/guihandles/GuiHandle.java
``` java
package guitests.guihandles;

import java.util.Optional;
import java.util.logging.Logger;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.TestApp;
import seedu.address.commons.core.LogsCenter;

/**
 * Base class for all GUI Handles used in testing.
 */
public class GuiHandle {
    protected final GuiRobot guiRobot;
    protected final Stage primaryStage;
    /**
     * An optional stage that exists in the App other than the primaryStage, could be a alert dialog, popup window, etc.
     */
    protected Optional<Stage> intermediateStage = Optional.empty();
    protected final String stageTitle;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public GuiHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        this.guiRobot = guiRobot;
        this.primaryStage = primaryStage;
        this.stageTitle = stageTitle;
        focusOnSelf();
    }

    public void focusOnWindow(String stageTitle) {
        logger.info("Focusing " + stageTitle);
        Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            logger.warning("Can't find stage " + stageTitle + ", Therefore, aborting focusing");
            return;
        }
        intermediateStage = Optional.ofNullable((Stage) window.get());
        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> window.get().requestFocus());
        logger.info("Finishing focus " + stageTitle);
    }

    protected <T extends Node> T getNode(String query) {
        return guiRobot.lookup(query).query();
    }

    protected String getTextFieldText(String filedName) {
        TextField textField = getNode(filedName);
        return textField.getText();
    }

    protected void setTextField(String textFieldId, String newText) {
        guiRobot.clickOn(textFieldId);
        TextField textField = getNode(textFieldId);
        textField.setText(newText);
        guiRobot.sleep(500); // so that the texts stays visible on the GUI for a short period
    }

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER).sleep(500);
    }

    protected String getTextFromLabel(String fieldId, Node parentNode) {
        return ((Label) guiRobot.from(parentNode).lookup(fieldId).tryQuery().get()).getText();
    }

    public void focusOnSelf() {
        if (stageTitle != null) {
            focusOnWindow(stageTitle);
        }
    }

    public void focusOnMainApp() {
        this.focusOnWindow(TestApp.APP_TITLE);
    }

    public void closeWindow() {
        Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            return;
        }

        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> ((Stage) window.get()).close());
        focusOnMainApp();
    }
}
```
###### /java/guitests/guihandles/MainGuiHandle.java
``` java
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public TagListPanelHandle getTagListPanel() {
        return new TagListPanelHandle(guiRobot, primaryStage);
    }

    public TaskListPanelHandle getTaskListPanel() {
        return new TaskListPanelHandle(guiRobot, primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public AlertDialogHandle getAlertDialog(String title) {
        guiRobot.sleep(1000);
        return new AlertDialogHandle(guiRobot, primaryStage, title);
    }
}
```
###### /java/guitests/guihandles/ResultDisplayHandle.java
``` java
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * A handler for the ResultDisplay of the UI
 */
public class ResultDisplayHandle extends GuiHandle {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    public ResultDisplayHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Clicks on the TextArea.
     */
    public void clickOnTextArea() {
        guiRobot.clickOn(RESULT_DISPLAY_ID);
    }

    public String getText() {
        return getResultDisplay().getText();
    }

    private TextArea getResultDisplay() {
        return getNode(RESULT_DISPLAY_ID);
    }
}
```
###### /java/guitests/GuiRobot.java
``` java
package guitests;

import org.testfx.api.FxRobot;

import javafx.scene.input.KeyCodeCombination;
import seedu.address.testutil.TestUtil;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    public GuiRobot push(KeyCodeCombination keyCodeCombination) {
        return (GuiRobot) super.push(TestUtil.scrub(keyCodeCombination));
    }
}
```
###### /java/guitests/ScrollToCommandTest.java
``` java
package guitests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import org.junit.Test;

import seedu.address.testutil.TestTask;

public class ScrollToCommandTest extends TaskManagerGuiTest {
    private TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void scroll_inNonEmptyList_scrollSuccess() {
        assertScrollSuccess(1);

        int taskCount = currentList.length;
        assertScrollSuccess(taskCount);
        int middleIndex = taskCount / 2;
        assertScrollSuccess(middleIndex);
        assertScrollInvalid(taskCount + 1);
        assertScrollSuccess(middleIndex);
    }

    @Test
    public void scroll_inEmptyList_scrollFailure() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertScrollInvalid(1);
    }

    private void assertScrollInvalid(int index) {
        commandBox.runCommand("scroll " + index);
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Checks whether the list is scrolled to the specified index.
     *
     * @param index the index as shown in task list panel.
     */
    private void assertScrollSuccess(int index) {
        commandBox.runCommand("scroll " + index);
        assertResultMessage("Scrolled to index " + index);
        assertEquals(currentList[index - 1].getAsText(), taskListPanel.getSelectedTasks().get(0).getAsText());
    }
}
```
###### /java/guitests/TaskManagerGuiTest.java
``` java
package guitests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TagListPanelHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.TaskManager;
import seedu.address.testutil.TypicalRecurringTasks;
import seedu.address.testutil.TypicalTestEvents;
import seedu.address.testutil.TypicalTestTasks;

/**
 * A GUI Test class for TaskManager.
 */
public abstract class TaskManagerGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    private TestApp testApp;

    protected TypicalTestTasks td = new TypicalTestTasks();
    protected TypicalTestEvents te = new TypicalTestEvents();
    protected TypicalRecurringTasks tr = new TypicalRecurringTasks();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected TagListPanelHandle tagListPanel;
    protected TaskListPanelHandle taskListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    private Stage stage;

    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            taskListPanel = mainGui.getTaskListPanel();
            tagListPanel = mainGui.getTagListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected TaskManager getInitialData() {
        TaskManager tm = TaskManager.getStub();
        TypicalTestTasks.loadTaskManagerWithSampleData(tm);
        return tm;
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfTasks = taskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }

    public void raise(BaseEvent e) {
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }
}
```

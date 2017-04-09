# A0147996E-reused
###### /java/seedu/address/logic/parser/FindCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    private static FindCommandParser theOne;

    private FindCommandParser() {}

    public static FindCommandParser getInstance() {
        if (theOne == null) {
            theOne = new FindCommandParser();
        }
        return theOne;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}
```
###### /java/seedu/address/logic/parser/ScrollToCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ScrollToCommand;

/**
 * Parses input arguments and creates a new ScrollToCommand object
 */
public class ScrollToCommandParser {

    private static ScrollToCommandParser theOne;

    private ScrollToCommandParser() {
    }

    public static ScrollToCommandParser getInstance() {
        if (theOne == null) {
            theOne = new ScrollToCommandParser();
        }
        return theOne;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the ScrollToCommand
     * and returns an ScrollToCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScrollToCommand.MESSAGE_USAGE));
        }
        return new ScrollToCommand(index.get());
    }

}
```
###### /java/seedu/address/ui/ResultDisplay.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";
    private static final String INIT_HELP_MESSAGE = "Welcome to Dueue. How do you do today? \n"
            + "The basic command words are as "
            + "following: add, clear, delete, edit, find, finish, load, list, scroll, view, undo and redo.\n"
            + "To view the help message of any command, enter 'help + Command_Keyword'.";

    private final StringProperty displayed = new SimpleStringProperty(INIT_HELP_MESSAGE);

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay(AnchorPane placeHolder) {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        FxViewUtil.applyAnchorBoundaryParameters(resultDisplay, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayed.setValue(event.message);
    }
}
```
###### /java/seedu/address/ui/Ui.java
``` java
package seedu.address.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

}
```
###### /java/seedu/address/ui/UiManager.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.JumpToTagListRequestEvent;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.commons.events.ui.TagPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            MainWindow.initializeMainWindow(primaryStage, config, prefs, logic);
            MainWindow.mainWindow.show();
            MainWindow.mainWindow.fillInnerParts();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(MainWindow.mainWindow.getCurrentGuiSetting());
        MainWindow.mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    public void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(MainWindow.mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/MoonriseTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code ===============================================================
    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleJumpToTagListRequestEvent(JumpToTagListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        MainWindow.mainWindow.getTagListPanel().scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleJumpToTaskListRequestEvent(JumpToTaskListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        MainWindow.mainWindow.getTaskListPanel().scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleTagPanelSelectionChangedEvent(TagPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
```
###### /java/seedu/address/ui/UiPart.java
``` java
package seedu.address.ui;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;

/**
 * Represents a distinct part of the UI. e.g. Windows, dialogs, panels, status bars, etc.
 * It contains a scene graph with a root node of type {@code T}.
 */
public abstract class UiPart<T> {

    /** Resource folder where FXML files are stored. */
    public static final String FXML_FILE_FOLDER = "/view/";

    private FXMLLoader fxmlLoader;

    /**
     * Constructs a UiPart with the specified FXML file URL.
     * The FXML file must not specify the {@code fx:controller} attribute.
     */
    public UiPart(URL fxmlFileUrl) {
        assert fxmlFileUrl != null;
        fxmlLoader = new FXMLLoader(fxmlFileUrl);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Constructs a UiPart using the specified FXML file within {@link #FXML_FILE_FOLDER}.
     * @see #UiPart(URL)
     */
    public UiPart(String fxmlFileName) {
        this(fxmlFileName != null ? MainApp.class.getResource(FXML_FILE_FOLDER + fxmlFileName) : null);
    }

    /**
     * Returns the root object of the scene graph of this UiPart.
     */
    public T getRoot() {
        return fxmlLoader.getRoot();
    }

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Creates a modal dialog.
     * @param title Title of the dialog.
     * @param parentStage The owner stage of the dialog.
     * @param scene The scene that will contain the dialog.
     * @return the created dialog, not yet made visible.
     */
    protected Stage createDialogStage(String title, Stage parentStage, Scene scene) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setScene(scene);
        return dialogStage;
    }
}
```
###### /resources/view/StatusBarFooter.fxml
``` fxml

<?import javafx.scene.layout.*?>
<?import org.controlsfx.control.StatusBar?>
<GridPane styleClass="grid-pane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" stylesheets="@MoonriseTheme.css">
<columnConstraints>
  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
</columnConstraints>
 <children>
      <StatusBar styleClass="anchor-pane" fx:id="syncStatus" minWidth="0.0"/>
      <StatusBar styleClass="anchor-pane" fx:id="currentDate" minWidth="0.0" GridPane.columnIndex="1"/>
 </children>
</GridPane>
```

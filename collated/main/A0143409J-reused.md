# A0143409J-reused
###### /java/seedu/address/commons/core/ComponentManager.java
``` java
package seedu.address.commons.core;

import seedu.address.commons.events.BaseEvent;

/**
 * Base class for *Manager classes
 *
 * Registers the class' event handlers in eventsCenter
 */
public abstract class ComponentManager {
    protected EventsCenter eventsCenter;

    /**
     * Uses default {@link EventsCenter}
     */
    public ComponentManager() {
        this(EventsCenter.getInstance());
    }

    public ComponentManager(EventsCenter eventsCenter) {
        this.eventsCenter = eventsCenter;
        eventsCenter.registerHandler(this);
    }

    protected void raise(BaseEvent event) {
        eventsCenter.post(event);
    }
}
```
###### /java/seedu/address/commons/core/Config.java
``` java
package seedu.address.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

```
###### /java/seedu/address/commons/core/Config.java
``` java

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public String getTaskManagerFilePath() {
        return dueueFilePath;
    }

    public void setTaskManagerFilePath(String dueueFilePath) {
        this.dueueFilePath = dueueFilePath;
    }

    public String getTaskManagerName() {
        return dueueName;
    }

    public void setTaskManagerName(String dueueName) {
        this.dueueName = dueueName;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { //this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(dueueFilePath, o.dueueFilePath)
                && Objects.equals(dueueName, o.dueueName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, dueueFilePath, dueueName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + dueueFilePath);
        sb.append("\nTaskManager name : " + dueueName);
        return sb.toString();
    }

}
```
###### /java/seedu/address/commons/core/EventsCenter.java
``` java
package seedu.address.commons.core;

import java.util.logging.Logger;

import com.google.common.eventbus.EventBus;

import seedu.address.commons.events.BaseEvent;

/**
 * Manages the event dispatching of the app.
 */
public class EventsCenter {
    private static final Logger logger = LogsCenter.getLogger(EventsCenter.class);
    private final EventBus eventBus;
    private static EventsCenter instance;

    public static EventsCenter getInstance() {
        if (instance == null) {
            instance = new EventsCenter();
        }
        return instance;
    }

    public static void clearSubscribers() {
        instance = null;
    }

    private EventsCenter() {
        eventBus = new EventBus();
    }

    public EventsCenter registerHandler(Object handler) {
        eventBus.register(handler);
        return this;
    }

    /**
     * Posts an event to the event bus.
     */
    public <E extends BaseEvent> EventsCenter post(E event) {
        logger.info("------[Event Posted] " + event.getClass().getCanonicalName() + ": " + event.toString());
        eventBus.post(event);
        return this;
    }

}
```
###### /java/seedu/address/commons/core/GuiSettings.java
``` java
package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private Double windowWidth;
    private Double windowHeight;
    private Point windowCoordinates;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
    }

    public Double getWindowWidth() {
        return windowWidth;
    }

    public Double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return Objects.equals(windowWidth, o.windowWidth)
                && Objects.equals(windowHeight, o.windowHeight)
                && Objects.equals(windowCoordinates.x, o.windowCoordinates.x)
                && Objects.equals(windowCoordinates.y, o.windowCoordinates.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates);
        return sb.toString();
    }
}
```
###### /java/seedu/address/commons/core/LogsCenter.java
``` java
package seedu.address.commons.core;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import seedu.address.commons.events.BaseEvent;

/**
 * Configures and manages loggers and handlers, including their logging level
 * Named {@link Logger}s can be obtained from this class<br>
 * These loggers have been configured to output messages to the console and a {@code .log} file by default,
 *   at the {@code INFO} level. A new {@code .log} file with a new numbering will be created after the log
 *   file reaches 5MB big, up to a maximum of 5 files.<br>
 */
public class LogsCenter {
    private static final int MAX_FILE_COUNT = 5;
    private static final int MAX_FILE_SIZE_IN_BYTES = (int) (Math.pow(2, 20) * 5); // 5MB
    private static final String LOG_FILE = "dueue.log";
    private static Level currentLogLevel = Level.INFO;
    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);
    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

    /**
     * Initializes with a custom log level (specified in the {@code config} object)
     * Loggers obtained *AFTER* this initialization will have their logging level changed<br>
     * Logging levels for existing loggers will only be updated if the logger with the same name
     * is requested again from the LogsCenter.
     */
    public static void init(Config config) {
        currentLogLevel = config.getLogLevel();
        logger.info("currentLogLevel: " + currentLogLevel);
    }

    /**
     * Creates a logger with the given name the given name.
     */
    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        removeHandlers(logger);
        addConsoleHandler(logger);
        addFileHandler(logger);

        return Logger.getLogger(name);
    }

    /**
     * Creates a Logger for the given class name.
     */
    public static <T> Logger getLogger(Class<T> clazz) {
        if (clazz == null) return Logger.getLogger("");
        return getLogger(clazz.getSimpleName());
    }

    private static void addConsoleHandler(Logger logger) {
        if (consoleHandler == null) consoleHandler = createConsoleHandler();
        logger.addHandler(consoleHandler);
    }

    private static void removeHandlers(Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
    }

    private static void addFileHandler(Logger logger) {
        try {
            if (fileHandler == null) fileHandler = createFileHandler();
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.warning("Error adding file handler for logger.");
        }
    }

    private static FileHandler createFileHandler() throws IOException {
        FileHandler fileHandler = new FileHandler(LOG_FILE, MAX_FILE_SIZE_IN_BYTES, MAX_FILE_COUNT, true);
        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(currentLogLevel);
        return fileHandler;
    }

    private static ConsoleHandler createConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(currentLogLevel);
        return consoleHandler;
    }

    /**
     * Decorates the given string to create a log message suitable for logging event handling methods.
     */
    public static String getEventHandlingLogMessage(BaseEvent e, String message) {
        return "---[Event handled][" + e + "]" + message;
    }

    /**
     * @see #getEventHandlingLogMessage(BaseEvent, String)
     */
    public static String getEventHandlingLogMessage(BaseEvent e) {
        return getEventHandlingLogMessage(e, "");
    }
}
```
###### /java/seedu/address/commons/core/Messages.java
``` java
package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_TASKS_FOUND_OVERVIEW = "%1$d tasks found!";

}
```
###### /java/seedu/address/commons/core/UnmodifiableObservableList.java
``` java
package seedu.address.commons.core;

import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * Unmodifiable view of an observable list
 */
public class UnmodifiableObservableList<E> implements ObservableList<E> {

    public static final String MUTATION_OP_EXCEPTION_MESSAGE = "Attempted to modify an unmodifiable view";

    private final ObservableList<? extends E> backingList;

    public UnmodifiableObservableList(ObservableList<? extends E> backingList) {
        assert backingList != null;
        this.backingList = backingList;
    }

    @Override
    public final void addListener(ListChangeListener<? super E> listener) {
        backingList.addListener(listener);
    }

    @Override
    public final void addListener(InvalidationListener listener) {
        backingList.addListener(listener);
    }

    @Override
    public final void removeListener(ListChangeListener<? super E> listener) {
        backingList.removeListener(listener);
    }

    @Override
    public final void removeListener(InvalidationListener listener) {
        backingList.removeListener(listener);
    }

    @Override
    public final boolean addAll(Object... elements) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean setAll(Object... elements) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean setAll(Collection<? extends E> col) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean removeAll(Object... elements) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean retainAll(Object... elements) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final void remove(int from, int to) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean remove(Object o) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final E remove(int index) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final FilteredList<E> filtered(Predicate<E> predicate) {
        return new FilteredList<>(this, predicate);
    }

    @Override
    public final SortedList<E> sorted(Comparator<E> comparator) {
        return new SortedList<>(this, comparator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final SortedList<E> sorted() {
        return sorted(Comparator.nullsFirst((o1, o2) -> {
            if (o1 instanceof Comparable) {
                return ((Comparable<E>) o1).compareTo(o2);
            }
            return Collator.getInstance().compare(o1.toString(), o2.toString());
        }));
    }

    @Override
    public final int size() {
        return backingList.size();
    }

    @Override
    public final boolean isEmpty() {
        return backingList.isEmpty();
    }

    @Override
    public final boolean contains(Object o) {
        return backingList.contains(o);
    }

    @Override
    public final Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<? extends E> i = backingList.iterator();

            public final boolean hasNext() {
                return i.hasNext();
            }
            public final E next() {
                return i.next();
            }
            public final void remove() {
                throw new UnsupportedOperationException();
            }
            @Override
            public final void forEachRemaining(Consumer<? super E> action) {
                // Use backing collection version
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public final Object[] toArray() {
        return backingList.toArray();
    }

    @Override
    public final <T> T[] toArray(T[] a) {
        return backingList.toArray(a);
    }

    @Override
    public final boolean add(E o) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final void add(int index, Object element) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean containsAll(Collection<?> c) {
        return backingList.containsAll(c);
    }

    @Override
    public final void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final void sort(Comparator<? super E> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }


    @Override
    public final boolean equals(Object o) {
        return o == this || backingList.equals(o);
    }

    @Override
    public final int hashCode() {
        return backingList.hashCode();
    }


    @Override
    public final E get(int index) {
        return backingList.get(index);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Object set(int index, Object element) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final int indexOf(Object o) {
        return backingList.indexOf(o);
    }

    @Override
    public final int lastIndexOf(Object o) {
        return backingList.lastIndexOf(o);
    }

    @Override
    public final ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public final ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            private final ListIterator<? extends E> i = backingList.listIterator(index);

            public final boolean hasNext() {
                return i.hasNext();
            }
            public final E next() {
                return i.next();
            }
            public final boolean hasPrevious() {
                return i.hasPrevious();
            }
            public final E previous() {
                return i.previous();
            }
            public final int nextIndex() {
                return i.nextIndex();
            }
            public final int previousIndex() {
                return i.previousIndex();
            }

            public final void remove() {
                throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
            }
            public final void set(E e) {
                throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
            }
            public final void add(E e) {
                throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
            }

            @Override
            public final void forEachRemaining(Consumer<? super E> action) {
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public final List<E> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(backingList.subList(fromIndex, toIndex));
    }

    @Override
    public final boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Stream<E> stream() {
        return (Stream<E>) backingList.stream();
    }

    @Override
    public final void forEach(Consumer<? super E> action) {
        backingList.forEach(action);
    }

}
```
###### /java/seedu/address/commons/core/Version.java
``` java
package seedu.address.commons.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents a version with major, minor and patch number
 */
public class Version implements Comparable<Version> {

    public static final String VERSION_REGEX = "V(\\d+)\\.(\\d+)\\.(\\d+)(ea)?";

    private static final String EXCEPTION_STRING_NOT_VERSION = "String is not a valid Version. %s";

    private static final Pattern VERSION_PATTERN = Pattern.compile(VERSION_REGEX);

    private final int major;
    private final int minor;
    private final int patch;
    private final boolean isEarlyAccess;

    public Version(int major, int minor, int patch, boolean isEarlyAccess) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.isEarlyAccess = isEarlyAccess;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public boolean isEarlyAccess() {
        return isEarlyAccess;
    }

    /**
     * Parses a version number string in the format V1.2.3.
     * @param versionString version number string
     * @return a Version object
     */
    @JsonCreator
    public static Version fromString(String versionString) throws IllegalArgumentException {
        Matcher versionMatcher = VERSION_PATTERN.matcher(versionString);

        if (!versionMatcher.find()) {
            throw new IllegalArgumentException(String.format(EXCEPTION_STRING_NOT_VERSION, versionString));
        }

        return new Version(Integer.parseInt(versionMatcher.group(1)),
                Integer.parseInt(versionMatcher.group(2)),
                Integer.parseInt(versionMatcher.group(3)),
                versionMatcher.group(4) == null ? false : true);
    }

    @JsonValue
    public String toString() {
        return String.format("V%d.%d.%d%s", major, minor, patch, isEarlyAccess ? "ea" : "");
    }

    @Override
    public int compareTo(Version other) {
        return this.major != other.major ? this.major - other.major :
               this.minor != other.minor ? this.minor - other.minor :
               this.patch != other.patch ? this.patch - other.patch :
               this.isEarlyAccess == other.isEarlyAccess() ? 0 :
               this.isEarlyAccess ? -1 : 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Version)) {
            return false;
        }
        final Version other = (Version) obj;

        return this.compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        String hash = String.format("%03d%03d%03d", major, minor, patch);
        if (!isEarlyAccess) {
            hash = "1" + hash;
        }
        return Integer.parseInt(hash);
    }
}
```
###### /java/seedu/address/commons/events/BaseEvent.java
``` java
package seedu.address.commons.events;

public abstract class BaseEvent {

    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     *
     * For example, the event manager post method will call any posted event's toString and print it in the console.
     */
    public abstract String toString();

}
```
###### /java/seedu/address/commons/events/model/DueueChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskManager;

/** Indicates the AddressBook in the model has changed*/
public class DueueChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public DueueChangedEvent(ReadOnlyTaskManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
```
###### /java/seedu/address/commons/events/storage/DataSavingExceptionEvent.java
``` java
package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class DataSavingExceptionEvent extends BaseEvent {

    public Exception exception;

    public DataSavingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.toString();
    }

}
```
###### /java/seedu/address/commons/events/ui/ExitAppRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for App termination
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/JumpToListRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/JumpToTagListRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tags
 */
public class JumpToTagListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTagListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/NewResultAvailableEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;

    public NewResultAvailableEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/address/commons/events/ui/ShowHelpRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```

//@@author A0143409J-reused
package seedu.address.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Dueue App";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String dueueFilePath = "data/dueue.xml";
    private String dueueName = "MyTaskManager";
    private static Config instance;

//@@author A0147996E
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private Config() {}
//@@author

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

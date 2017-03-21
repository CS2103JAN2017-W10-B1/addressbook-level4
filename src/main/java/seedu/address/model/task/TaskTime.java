package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's due time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class TaskTime implements TaskField, Comparable<TaskTime> {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Task time should be the form hh:mm";

    /*
     * The first character of the time must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String HOUR_VALIDATION_REGEX = "(\\d)|(0\\d)|(1\\d)|(2[0-3])";
    public static final String MINUTE_VALIDATION_REGEX = "[0-5][0-9]";
    public static final String TIME_VALIDATION_REGEX = ".*:.*";
    public static final String HOUR_MINUTE_SEPARATOR = ":";

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("hh:mm");

    public static final int INF = 1000000000;

    private final String value;
    private final Date time;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public TaskTime(String time) throws IllegalValueException {
        assert time != null;
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        try {
            this.time = time.equals("") ? null : FORMATTER.parse(time);
        } catch (ParseException e) {
            assert false : "impossible";
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidTime(String test) {
        if (test.equals("")) {
            return true;
        }
        if (!test.matches(TIME_VALIDATION_REGEX)) {
            return false;
        }
        String[] hourAndMinute = test.split(HOUR_MINUTE_SEPARATOR);
        String hour = hourAndMinute[0];
        String minute = hourAndMinute[1];
        return isValidHour(hour) && isValidMinute(minute);
    }

    private static boolean isValidHour(String test) {
        return test.matches(HOUR_VALIDATION_REGEX);
    }

    private static boolean isValidMinute(String test) {
        return test.matches(MINUTE_VALIDATION_REGEX);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTime // instanceof handles nulls
                && this.value.equals(((TaskTime) other).value)); // state check
    }

    @Override
    public int compareTo(TaskTime other) {
        if (this.time == null) {
            if (other.time == null) {
                return 0;
            } else {
                return INF;
            }
        } else {
            if (other.time == null) {
                return -INF;
            } else {
                long diff = this.time.getTime() - other.time.getTime();
                return (int) TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            }
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

//@@author A0143409J
    @Override
    public String getDisplayText() {
        if ((value == null) || (value == "")) {
            return "";
        } else {
            return "Time: " + value;
        }
    }
}

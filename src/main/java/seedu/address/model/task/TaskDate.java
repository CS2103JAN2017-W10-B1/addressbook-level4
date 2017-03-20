//@@author A0147984L
package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDate implements TaskField {

    public static final String MESSAGE_DATE_CONSTRAINTS = "task due date should be the form dd/mm or dd/mm/yyyy";

    public static final String DATE_VALIDATION_REGEX = ".*/.*";
    public static final String DAY_MONTH_SEPARATOR = "/";
    public static final String MONTH_VALIDATION_REGEX_1 = "([13578])|(0[13578])|(1[02])";
    public static final String MONTH_VALIDATION_REGEX_2 = "([469])|(0[469])|(11)";
    public static final String MONTH_VALIDATION_REGEX_3 = "(2)|(02)";
    public static final String MONTH_VALIDATION_REGEX = MONTH_VALIDATION_REGEX_1 + "|"
            + MONTH_VALIDATION_REGEX_2 + "|" + MONTH_VALIDATION_REGEX_3;
    public static final String DAY_VALIDATION_REGEX_1 = "([1-9])|(0[1-9])|(1\\d)|(2\\d)|(3[0-1])";
    public static final String DAY_VALIDATION_REGEX_2 = "([1-9])|(0[1-9])|(1\\d)|(2\\d)|(30)";
    public static final String DAY_VALIDATION_REGEX_3 = "([1-9])|(0[1-9])|(1\\d)|(2[0-8])";
    public static final String YEAR_VALIDATION_REGEX = "20(1[789])|([2-9][0-9])";

    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");

    private final Calendar today;
    
    private final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public TaskDate(String date) throws IllegalValueException {
        assert date != null;
        today = Calendar.getInstance();
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test.equals("")) {
            return true;
        }
        if (!test.matches(DATE_VALIDATION_REGEX)) {
            return false;
        }
        String[] dayMonthYear = test.split(DAY_MONTH_SEPARATOR);
        if (dayMonthYear.length > 3) {
            return false;
        }
        String day = dayMonthYear[0];
        String month = dayMonthYear[1];
        String year = dayMonthYear.length == 3? dayMonthYear[2]: null;
        return isValidMonth(month) && isValidDay(day, month);
    }

    private static boolean isValidDay(String test, String month) {
        if (month.matches(MONTH_VALIDATION_REGEX_1)) {
            return test.matches(DAY_VALIDATION_REGEX_1);
        } else if (month.matches(MONTH_VALIDATION_REGEX_2)) {
            return test.matches(DAY_VALIDATION_REGEX_2);
        } else if (month.matches(MONTH_VALIDATION_REGEX_3)) {
            return test.matches(DAY_VALIDATION_REGEX_3);
        }
        assert false;
        return false;
    }

    private static boolean isValidMonth(String test) {
        return test.matches(MONTH_VALIDATION_REGEX);
    }

    private static boolean isValidYear(String test) {
        return test.matches(YEAR_VALIDATION_REGEX);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.value.equals(((TaskDate) other).value)); // state check
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
            return "Date: " + value;
        }
    }
}

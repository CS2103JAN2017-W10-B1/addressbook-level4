//@@author A0147984L
package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDate implements TaskField, Comparable<TaskDate> {

    public static final String MESSAGE_DATE_CONSTRAINTS_1 = "task due date should be the form dd/mm or dd/mm/yyyy";

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
    public static final String DAY_VALIDATION_REGEX_4 = "([1-9])|(0[1-9])|(1\\d)|(2[0-9])";
    public static final String YEAR_VALIDATION_REGEX = "(201[789])|(20[2-9]\\d)";

    public static final String DAY_VALIDATION_MONDAY = "(?i)(monday)|(mon)";
    public static final String DAY_VALIDATION_TUESDAY = "(?i)(tuesday)|(tue)";
    public static final String DAY_VALIDATION_WEDNESDAY = "(?i)(wednesday)|(wed)";
    public static final String DAY_VALIDATION_THURSDAY = "(?i)(thursday)|(thu)|(thur)|(thurs)";
    public static final String DAY_VALIDATION_FRIDAY = "(?i)(friday)|(fri)";
    public static final String DAY_VALIDATION_SATURDAY = "(?i)(saturday)|(sat)";
    public static final String DAY_VALIDATION_SUNDAY = "(?i)(sunday)|(sun)";

    public static final String DAY_VALIDATION_TODAY = "(?i)(today)";
    public static final String DAY_VALIDATION_TOMORROW = "(?i)(tomorrow)|(tmr)";

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");


    public static final int INF = 1000000000;
    public static final String INF_DATE = "1/1/2100";

    private final Calendar today;

    private final String value;
    public final Date date;
    public final boolean isPastDue;

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
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS_1);
        }
        if (isDayInWeek(trimmedDate)) {
            int day = dayInWeek(trimmedDate);
            int incre = 0;
            Calendar current = Calendar.getInstance();
            while ((current.get(Calendar.DAY_OF_WEEK) != day)) {
                current.add(Calendar.DATE, 1);
                incre += 1;
                if (incre >= 7) {
                    assert false;
                }
            }
            this.date = current.getTime();
            this.isPastDue = false;
            this.value = current.get(Calendar.DAY_OF_MONTH) + "/"
                    + (current.get(Calendar.MONTH) + 1) + "/"
                    + current.get(Calendar.YEAR);
        } else if (isTodayOrTomorrow(trimmedDate)) {
            int incre = todayOrTomorrow(trimmedDate);
            Calendar current = Calendar.getInstance();
            while (incre --> 0) {
                current.add(Calendar.DATE, 1);
            }
            this.date = current.getTime();
            this.isPastDue = false;
            this.value = current.get(Calendar.DAY_OF_MONTH) + "/"
                    + (current.get(Calendar.MONTH) + 1) + "/"
                    + current.get(Calendar.YEAR);
        } else {
            try {
                this.date = trimmedDate.equals("") ?
                        FORMATTER.parse(INF_DATE) :
                        FORMATTER.parse(parseDate(trimmedDate));
            } catch (ParseException e) {
                assert false : "impossble";
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS_1);
            }
            this.isPastDue = TimeUnit.DAYS.convert(
                    this.date.getTime() - today.getTime().getTime(), TimeUnit.MILLISECONDS) < 0;
            this.value = trimmedDate.equals("") ? trimmedDate : parseDate(trimmedDate);
        }
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test.equals("")) {
            return true;
        }
        if (isDayInWeek(test)) {
            return true;
        }
        if (isTodayOrTomorrow(test)) {
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
        String year = dayMonthYear.length == 3 ? dayMonthYear[2] : null;
        return isValidMonth(month) && isValidDay(day, month, year) && isValidYear(year);
    }

    private static boolean isTodayOrTomorrow(String test) {
        return todayOrTomorrow(test) != -1;
    }

    private static int todayOrTomorrow(String test) {
        if (test.matches(DAY_VALIDATION_TODAY)) {
            return 0;
        } else if (test.matches(DAY_VALIDATION_TOMORROW)) {
            return 1;
        } else {
            return -1;
        }
    }

    private static boolean isDayInWeek(String test) {
        return (dayInWeek(test) <= 7) && (dayInWeek(test) >= 1);
    }

    private static int dayInWeek(String test) {
        if (test.matches(DAY_VALIDATION_SUNDAY)) {
            return 1;
        } else if (test.matches(DAY_VALIDATION_MONDAY)) {
            return 2;
        } else if (test.matches(DAY_VALIDATION_TUESDAY)) {
            return 3;
        } else if (test.matches(DAY_VALIDATION_WEDNESDAY)) {
            return 4;
        } else if (test.matches(DAY_VALIDATION_THURSDAY)) {
            return 5;
        } else if (test.matches(DAY_VALIDATION_FRIDAY)) {
            return 6;
        } else if (test.matches(DAY_VALIDATION_SATURDAY)) {
            return 7;
        } else {
            return -1;
        }
    }

    private static boolean isValidDay(String test, String month, String year) {
        if (month.matches(MONTH_VALIDATION_REGEX_1)) {
            return test.matches(DAY_VALIDATION_REGEX_1);
        } else if (month.matches(MONTH_VALIDATION_REGEX_2)) {
            return test.matches(DAY_VALIDATION_REGEX_2);
        } else if (month.matches(MONTH_VALIDATION_REGEX_3) && !isLeapYear(year)) {
            return test.matches(DAY_VALIDATION_REGEX_3);
        } else if (month.matches(MONTH_VALIDATION_REGEX_3) && isLeapYear(year)) {
            return test.matches(DAY_VALIDATION_REGEX_4);
        }
        assert false;
        return false;
    }

    private static boolean isValidMonth(String test) {
        return test.matches(MONTH_VALIDATION_REGEX);
    }

    private static boolean isValidYear(String test) {
        return test == null || test.matches(YEAR_VALIDATION_REGEX);
    }

    private static boolean isLeapYear(String test) {
        if (test == null) {
            return true;
        }
        int year = Integer.parseInt(test);
        return (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
    }

    private String parseDate(String validDate) throws IllegalValueException {
        String[] dayMonthYear = validDate.split(DAY_MONTH_SEPARATOR);
        if (dayMonthYear.length == 3) {
            return validDate;
        }
        String day = dayMonthYear[0];
        String month = dayMonthYear[1];
        String year = Integer.toString(today.get(Calendar.YEAR));
        try {
            String returnDate = day + DAY_MONTH_SEPARATOR + month + DAY_MONTH_SEPARATOR + year;
            Date date = FORMATTER.parse(returnDate);
            if (date.compareTo(today.getTime()) < 0) {
                year = Integer.toString(today.get(Calendar.YEAR) + 1);
                returnDate = day + DAY_MONTH_SEPARATOR + month + DAY_MONTH_SEPARATOR + year;
            }
            date = FORMATTER.parse(returnDate);
            if (!isValidDay(day, month, year)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS_1);
            } else {
                return returnDate;
            }
        } catch (ParseException e) {
            assert false : "impossible";
        }
        assert false : "impossible";
        return validDate;
    }

    public String getValue() {
        return value;
    }

    /** return if the task has past due*/
    public boolean isPastDue() {
        return isPastDue;
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

    @Override
    public int compareTo(TaskDate other) {
        if (this.value.equals("")) {
            if (other.value.equals("")) {
                return 0;
            } else {
                return INF;
            }
        } else {
            if (other.value.equals("")) {
                return -INF;
            } else {
                long diff = this.date.getTime() - other.date.getTime();
                return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            }
        }
    }

//@@author A0143409J
    @Override
    public String getDisplayText() {
        if (value.equals(" ") || value.equals("")) {
            return "";
        } else {
            return "Date: " + value;
        }
    }
}

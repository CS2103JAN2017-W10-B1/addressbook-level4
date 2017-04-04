//@@author A0147984L
package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;

/**
 * Represents a Task's due date in task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDate implements TaskField, Comparable<TaskDate> {

    public static final String MESSAGE_DATE_CONSTRAINTS_1 =
            "task due date should be the form dd/mm, dd/mm/yyyy or Monday, tomorrow, etc.";

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

    private Calendar today;

    private String value;
    public Date date;
    public boolean isPastDue;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public TaskDate(String date) throws IllegalValueException {
        assert date != null;
        initializeToday();
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS_1);
        }
        if (isDayInWeek(trimmedDate)) {
            initializeGivenDayInWeek(trimmedDate);
        } else if (isTodayOrTomorrow(trimmedDate)) {
            initializeGivenTodayOrTomorrow(trimmedDate);
        } else {
            initializeGivenDate(trimmedDate);
        }
    }

    private void initializeToday() {
        today = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test.isEmpty()) {
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

    // methods used when given string is a date

    private void initializeGivenDate(String trimmedDate) throws IllegalValueException {
        trimmedDate = addYearField(trimmedDate);
        try {
            this.date = trimmedDate.isEmpty() ?
                    FORMATTER.parse(INF_DATE) :
                    FORMATTER.parse(trimmedDate);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS_1);
        }
        this.isPastDue = this.date.before(today.getTime());
        this.value = trimmedDate.isEmpty() ? trimmedDate : getDateString(date);
    }

    private String addYearField(String validDate) throws IllegalValueException {
        if (validDate.isEmpty()) {
            return validDate;
        }
        String[] dayMonthYear = validDate.split(DAY_MONTH_SEPARATOR);
        if (dayMonthYear.length == 3) {
            return validDate;
        }
        String day = dayMonthYear[0];
        String month = dayMonthYear[1];
        String year = Integer.toString(today.get(Calendar.YEAR));
        try {
            return parseDate(day, month, year);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS_1);
        }
    }

    private String parseDate(String day, String month, String year) throws IllegalValueException, ParseException {
        if (!isValidDay(day, month, year)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS_1);
        } // need to check because of February 29
        String returnDate = getDateString(year, month, day);
        Date date = FORMATTER.parse(getDateString(year, month, day));
        if (date.before(today.getTime())) {
            year = Integer.toString(today.get(Calendar.YEAR) + 1);
            returnDate = getDateString(year, month, day);
            if (!isValidDay(day, month, year)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS_1);
            }
        }
        return returnDate;
    }

    /**
     * Test the combination of given day, month, year is valid
     */
    private static boolean isValidDay(String day, String month, String year) {
        if (month.matches(MONTH_VALIDATION_REGEX_1)) {
            return day.matches(DAY_VALIDATION_REGEX_1);
        } else if (month.matches(MONTH_VALIDATION_REGEX_2)) {
            return day.matches(DAY_VALIDATION_REGEX_2);
        } else if (month.matches(MONTH_VALIDATION_REGEX_3) && !isLeapYear(year)) {
            return day.matches(DAY_VALIDATION_REGEX_3);
        } else if (month.matches(MONTH_VALIDATION_REGEX_3) && isLeapYear(year)) {
            return day.matches(DAY_VALIDATION_REGEX_4);
        }
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

    // methods used when given string is today or tomorrow

    private void initializeGivenTodayOrTomorrow(String trimmedDate) {
        Calendar current = Calendar.getInstance();
        current.setTime(today.getTime());
        if (todayOrTomorrow(trimmedDate) == 1) {
            current.add(Calendar.DATE, 1);
        }
        this.isPastDue = false;
        this.date = current.getTime();
        this.value = getDateString(current);
    }

    private static boolean isTodayOrTomorrow(String test) {
        return todayOrTomorrow(test) != -1;
    }

    /**
     * Return the corresponding digit represented by the giving string
     * Return 0 for today, 1 for tomorrow, and -1 for invalid input
     */
    public static int todayOrTomorrow(String test) {
        assert test != null;
        if (test.matches(DAY_VALIDATION_TODAY)) {
            return 0;
        } else if (test.matches(DAY_VALIDATION_TOMORROW)) {
            return 1;
        } else {
            return -1;
        }
    }

    // methods used when given string is day in a week

    private void initializeGivenDayInWeek(String trimmedDate) {
        int day = dayInWeek(trimmedDate);
        int incre = 0;
        Calendar current = Calendar.getInstance();
        current.setTime(today.getTime());
        while ((current.get(Calendar.DAY_OF_WEEK) != day)) {
            current.add(Calendar.DATE, 1);
            incre += 1;
            if (incre >= 7) {
                assert false : "date should not increase more than 7 times";
            }
        }
        this.isPastDue = false;
        this.date = current.getTime();
        this.value = getDateString(current);
    }

    private static boolean isDayInWeek(String test) {
        return (dayInWeek(test) <= 7) && (dayInWeek(test) >= 1);
    }

    /**
     * Return the corresponding digit represented by the order of day in a week of the giving string
     * The first day in a week is Sunday
     * Return -1 for invalid input
     */
    public static int dayInWeek(String test) {
        assert test != null;
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

    // Methods to format date string

    /**
     * Format the date by given calendar instance
     */
    public static String getDateString(Calendar current) {
        return current.get(Calendar.DAY_OF_MONTH) + DAY_MONTH_SEPARATOR
                + (current.get(Calendar.MONTH) + 1) + DAY_MONTH_SEPARATOR
                + current.get(Calendar.YEAR);
    }

    /**
     * Format the date by given date
     */
    public static String getDateString(Date date) {
        Calendar current = Calendar.getInstance();
        current.setTime(date);
        return getDateString(current);
    }

    /**
     * Format the date by given year, month, and day
     */
    public static String getDateString(String year, String month, String day) {
        return day + DAY_MONTH_SEPARATOR
                + month + DAY_MONTH_SEPARATOR
                + year;
    }

// Methods with recurring task

    /**
     * Add a recurring period for date
     */
    public void addPeriod(RecurringMode mode, int times) {
        assert mode != null;
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(this.date);
        if (mode.equals(RecurringMode.DAY)) {
            todayCalendar.add(Calendar.DATE, 1 * times);
        } else if (mode.equals(RecurringMode.WEEK)) {
            todayCalendar.add(Calendar.DATE, 7 * times);
        } else if (mode.equals(RecurringMode.MONTH)) {
            todayCalendar.add(Calendar.MONTH, 1 * times);
        } else {
            assert false;
        }
        this.date = todayCalendar.getTime();
        this.value = getDateString(todayCalendar);
    }

    public void addPeriod(RecurringMode mode) {
        addPeriod(mode, 1);
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

    /**
     * Returns the time difference in hours
     * Empty date is always greater compare to non-empty date
     */
    @Override
    public int compareTo(TaskDate other) {
        if (this.value.isEmpty()) {
            if (other.value.isEmpty()) {
                return 0;
            } else {
                return INF;
            }
        } else {
            if (other.value.isEmpty()) {
                return -INF;
            } else {
                long diff = this.date.getTime() - other.date.getTime();
                return (int) TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            }
        }
    }

//@@author A0143409J
    @Override
    public String getDisplayText() {
        if (" ".equals(value) || "".equals(value)) {
            return "";
        } else {
            return "Date: " + value;
        }
    }

    public int compareToDay(TaskDate other) {
        if (this.value.isEmpty()) {
            if (other.value.isEmpty()) {
                return 0;
            } else {
                return INF;
            }
        } else {
            if (this.value.isEmpty()) {
                return -INF;
            } else {
                long diff = this.date.getTime() - other.date.getTime();
                return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            }
        }
    }

//@@author A0147996E
    public String getStartDisplayText() {
        if (" ".equals(value) || "".equals(value)) {
            return "";
        } else {
            return "StartDate: " + value;
        }
    }

    public String getPastDueDisplayedText() {
        if (isPastDue()) {
            return "Past due!";
        } else {
            return "";
        }
    }
}

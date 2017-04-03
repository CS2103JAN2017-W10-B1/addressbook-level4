//@@author A0147984L
package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in task manager.
 * Guarantees: is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority implements TaskField, Comparable<Priority> {

    public static final String PRIORITY_1 = "1";
    public static final String PRIORITY_2 = "2";
    public static final String PRIORITY_3 = "3";
    public static final String PRIORITY_TRIVIAL = "trivial";
    public static final String PRIORITY_NORMAL = "normal";
    public static final String PRIORITY_IMPORTANT = "important";
    public static final String PRIORITY_EMPTY = "";

    public static final String DEFAULT_PRIORITY = PRIORITY_2;

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "task priority can only be 1, 2, 3,"
            + "trivial, normal, or important";

    public static final String PRIORITY_VALIDATION_REGEX = PRIORITY_1 + "|" + PRIORITY_2 + "|" + PRIORITY_3;

    private final String value;

    /**
     * Validity given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        if (priority == null) {
            this.value = DEFAULT_PRIORITY;
        } else {
            String trimmedPriority = priority.trim();
            String convertedPriority = convert(trimmedPriority);
            if (!isValidPriority(convertedPriority)) {
                throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
            }
            this.value = convertedPriority;
        }
    }

    /**
     * Returns if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    /**
     * Convert English expressions into digits expressions
     */
    public static String convert(String value) {
        value = value.isEmpty() ? DEFAULT_PRIORITY : value;
        value = value.replaceFirst("(?i)" + PRIORITY_IMPORTANT, PRIORITY_3);
        value = value.replaceFirst("(?i)" + PRIORITY_NORMAL, PRIORITY_2);
        value = value.replaceFirst("(?i)" + PRIORITY_TRIVIAL, PRIORITY_1);
        return value;
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
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Priority other) {
        return Integer.parseInt(this.value)
                -
                Integer.parseInt(other.value);
    }

//@@author A0147996E
    @Override
    public String getDisplayText() {
        if (" ".equals(value) || "".equals(value)) {
            return "";
        } else if ("1".equals(value)) {
            return "Priority: " + "\u25CF";
        } else if ("2".equals(value)) {
            return "Priority: " + "\u25CF \u25CF";
        } else if ("3".equals(value)) {
            return "Priority: " + "\u25CF \u25CF \u25CF";
        } else  {
            throw new IllegalStateException("Priority is illegal\n");
        }
    }
}

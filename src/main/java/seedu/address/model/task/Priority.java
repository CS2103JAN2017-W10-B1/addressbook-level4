package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String PRIORITY_1 = "1";
    public static final String PRIORITY_2 = "2";
    public static final String PRIORITY_3 = "3";
    public static final String PRIORITY_TRIVIAL = "trivial";
    public static final String PRIORITY_NORMAL = "normal";
    public static final String PRIORITY_IMPORTANT = "important";

    public static final String DEFAULT_PRIORITY = PRIORITY_2;

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "task priority can only be 1, 2, 3, trivial, normal, or important";
    //TODO: public static final String DATE_VALIDATION_REGEX =

    public final String value;

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
            if (!isValidPriority(trimmedPriority)) {
                throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
            }
            this.value = convert(trimmedPriority);
        }
    }

    /**
     * Returns if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return true;
        //TODO: add REGEX
        //return test.matches(EMAIL_VALIDATION_REGEX);
    }

    /**
     * Convert English expressions into digits expressions
     */
    public String convert(String value) {
        if (value.equals(PRIORITY_TRIVIAL)) {
            return PRIORITY_1;
        } else if (value.equals(PRIORITY_NORMAL)) {
            return PRIORITY_2;
        } else if (value.equals(PRIORITY_IMPORTANT)) {
            return PRIORITY_3;
        } else {
            return value;
        }
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

}

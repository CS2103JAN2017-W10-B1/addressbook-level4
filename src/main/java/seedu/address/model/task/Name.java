package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the task manager.
 * Guarantees: is valid as declared in {@link #isValidName(String)}
 */
public class Name implements TaskField, Comparable<Name> {

    public static final String MESSAGE_NAME_CONSTRAINTS_1 =
            "Task names should only contain alphanumeric characters and spaces, "
            + "and it should not be blank";
    public static final String MESSAGE_NAME_CONSTRAINTS_2 =
            " is a reserved name.";

    /*
     * The first character of the task name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS_1);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public String getValue() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

    @Override
    public int compareTo(Name other) {
        return this.fullName.compareTo(other.fullName);
    }

//@@author A0143409J
    @Override
    public String getDisplayText() {
        if ((" ".equals(fullName)) || ("".equals(fullName))) {
            return "";
        } else {
            return fullName;
        }
    }
}

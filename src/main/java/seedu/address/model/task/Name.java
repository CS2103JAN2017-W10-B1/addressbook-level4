package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name implements TaskField {

    public static final String MESSAGE_NAME_CONSTRAINTS_1 =
            "Task names should only contain alphanumeric characters and spaces, "
            + "and it should not be blank, or be 'list' since it is reserved for command";
    public static final String MESSAGE_NAME_CONSTRAINTS_2 =
            " is a reserved name.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String RESERVED_NAME = "(?i)"
            + "(list)";

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
        } else if (isReservedName(trimmedName)) {
            throw new IllegalValueException(trimmedName + MESSAGE_NAME_CONSTRAINTS_2);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public static boolean isReservedName(String test) {
        return test.matches(RESERVED_NAME);
    }

    @Override
    public String toString() {
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

//@@author A0143409J
    @Override
    public String getDisplayText() {
        if ((fullName == null) || (fullName == "")) {
            return "";
        } else {
            return fullName;
        }
    }
}

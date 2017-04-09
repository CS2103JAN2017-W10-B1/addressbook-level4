//@@author A0147984L
package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's venue in task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue implements TaskField, Comparable<Venue> {

    public static final String MESSAGE_VENUE_CONSTRAINTS =
            "task venue can only contains alphanumerics and #, -, '.";

    // cannot begin with space; can only contains
    public static final String VENUE_VALIDATION_REGEX = "^((\\w)|([-#]))((\\w)|([-#'])|(\\s))*";

    private final String value;

    /**
     * Validity given venue.
     *
     * @param venue
     * @throws IllegalValueException if given venue string is invalid.
     */
    public Venue(String venue) throws IllegalValueException {
        assert venue != null;
        String trimmedVenue = venue.trim();
        if (!isValidVenue(trimmedVenue)) {
            throw new IllegalValueException(MESSAGE_VENUE_CONSTRAINTS);
        }
        this.value = trimmedVenue;
    }

    /**
     * Returns if a given string is a valid venue.
     */
    public static boolean isValidVenue(String test) {
        if (test.isEmpty()) {
            return true;
        }
        return test.matches(VENUE_VALIDATION_REGEX);
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
                || (other instanceof Venue // instanceof handles nulls
                && this.value.equals(((Venue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Venue other) {
        return this.value.compareTo(other.value);
    }

//@@author A0143409J
    @Override
    public String getDisplayText() {
        if (" ".equals(value) || "".equals(value)) {
            return "";
        } else {
            return "Venue: " + value;
        }
    }
}

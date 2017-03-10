package seedu.address.model.list;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a List in Dueue.
 * Guarantees: name is valid as declared in {@link #isValidListName(String)}
 */
public class List{
    
    public static final String MESSAGE_LIST_CONSTRAINTS = "List name should not begin with digits.";
    //TODO: public static final String LIST_VALIDATION_REGEX = ;
    
    private String name;
    private UniqueTagList tags;
    
    /**
     * Validates given list name.
     *
     * @throws IllegalValueException if the given list name string is invalid.
     */
    public List(String name) throws IllegalValueException{
        assert name != null;
        String trimmedName = name.trim();
        if (!IsValidListName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_LIST_CONSTRAINTS);
        }
        this.name = trimmedName;
        this.tags = new UniqueTagList();
    }

    /**
     * Returns true if a given string is a valid list name.
     */
    private boolean IsValidListName(String trimmedName) {
        // TODO Auto-generated method stub
        return true;
    }

    
    public void add(Tag tag) throws DuplicateTagException{
        tags.add(tag);
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof List // instanceof handles nulls
                && this.name.equals(((List) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '{' + name + '}';
    }

}

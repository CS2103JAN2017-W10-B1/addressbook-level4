package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.testutil.TypicalTestTasks;

public class ModelManagerTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private ModelManager modelManager = new ModelManager();
    private final TypicalTestTasks testUtil = new TypicalTestTasks();
    
    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), modelManager.getFilteredTaskList());
        assertEquals(Collections.emptyList(), modelManager.getFilteredListList());
    }
    
    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        modelManager.resetData(null);
    }
    
    @Test
    public void resetData() {
        modelManager.resetData(testUtil.getTypicalTaskManager());
        assertEquals(modelManager.getTaskManager(), testUtil.getTypicalTaskManager());
        assertEquals(modelManager.getFilteredTaskList().size(), 2);
        assertEquals(modelManager.getFilteredListList().size(), 2);
    }

    @Test
    public void testFilterName() {
        modelManager.resetData(testUtil.getTypicalTaskManager());
        Set<String> keywords = new HashSet<String>();
        keywords.add("Gym");
        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 1);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "gym");
        
        modelManager = new ModelManager();
        modelManager.resetData(testUtil.getTypicalTaskManager());
        keywords.add("cs2103");
        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 2);
    }
    
    @Test
    public void testFilterTag() {
        modelManager.resetData(testUtil.getTypicalTaskManager());
        assertEquals(modelManager.getFilteredTaskList().size(), 2);
        Set<String> keywords = new HashSet<String>();
        keywords.add("personal");
        modelManager.updateFilteredTaskListGivenListName(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 1);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "gym");
        
        modelManager = new ModelManager();
        modelManager.resetData(testUtil.getTypicalTaskManager());
        keywords.clear();
        keywords.add("School");
        modelManager.updateFilteredTaskListGivenListName(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 1);
        assertEquals(modelManager.getFilteredTaskList().get(0).getName().fullName, "cs2103");
        
        modelManager = new ModelManager();
        modelManager.resetData(testUtil.getTypicalTaskManager());
        keywords.clear();
        keywords.add("inbox");
        modelManager.updateFilteredTaskList(keywords);
        assertEquals(modelManager.getFilteredTaskList().size(), 0);
    }
}

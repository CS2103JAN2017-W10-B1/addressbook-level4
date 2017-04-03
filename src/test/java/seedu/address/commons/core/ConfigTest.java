package seedu.address.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConfigTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toStringDefaultObjectStringReturned() {
        String defaultConfigAsString = "App title : Dueue App\n" +
                "Current log level : INFO\n" +
                "Preference file Location : preferences.json\n" +
                "Local data file location : data/dueue.xml\n" +
                "TaskManager name : MyTaskManager";

        assertEquals(defaultConfigAsString, Config.getStub());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = Config.getStub();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
    }

}

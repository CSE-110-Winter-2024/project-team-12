package edu.ucsd.cse110.successorator;
import android.content.SharedPreferences;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class SuccessoratorApplicationTest {
    private SuccessoratorApplication app;

    @Before
    public void setUp() {
        app = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testOnCreate_initializesTaskRepository() {
        assertNotNull("TaskRepository should be initialized", app.getTaskRepository());
    }
}
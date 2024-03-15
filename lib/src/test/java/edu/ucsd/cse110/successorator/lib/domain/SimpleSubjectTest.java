package edu.ucsd.cse110.successorator.lib.domain;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class SimpleSubjectTest extends TestCase {

    MockSimpleSubject subj = new MockSimpleSubject();

    @Before
    public void setUp(){
        subj.observers.add("observer1");
        subj.observers.add("observer2");
        subj.observers.add("observer3");
    }
    @Test
    public void testObserve() {
        assertEquals(subj.observers.size(),3);
        subj.observe("observer4");
        assertEquals(subj.observers.size(),4);
        assertEquals(subj.observers.get(3),"observer4_observed_task");
    }

    @Test
    public void testRemoveObserver() {
        assertEquals(subj.observers.size(),3);
        subj.removeObserver("observer2");
        assertEquals(subj.observers.size(),2);
        assertEquals(subj.observers.get(0), "observer1");
        assertEquals(subj.observers.get(1),"observer3");
    }

    @Test
    public void testNotifyObservers() {
        assertEquals(subj.observers.size(),3);
        assertEquals(subj.observers.get(0), "observer1");
        assertEquals(subj.observers.get(1),"observer2");
        assertEquals(subj.observers.get(2), "observer3");
        subj.observers=subj.mockNotifyObservers();
        assertEquals(subj.observers.size(),3);
        assertEquals(subj.observers.get(0), "observer1_observed_task");
        assertEquals(subj.observers.get(1),"observer2_observed_task");
        assertEquals(subj.observers.get(2), "observer3_observed_task");
    }

}

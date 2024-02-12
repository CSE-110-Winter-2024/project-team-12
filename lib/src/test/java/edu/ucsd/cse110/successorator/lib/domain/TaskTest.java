package edu.ucsd.cse110.successorator.lib.domain;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TaskTest extends TestCase {

    private String TestString = "Test the Task class methods";
    private Task aTask = new Task(TestString);
    private Task aDoneTask = new Task(TestString);

    public void testGetTaskText() {
        assertEquals(TestString, aTask.getTaskText());
        assertEquals(TestString, Task.getTaskText(aTask));
    }


    public void testGetDoneStatus() {
        assertFalse(aTask.getDoneStatus());
        assertFalse(Task.getDoneStatus(aTask));
        aDoneTask.markAsDone();
        assertTrue(aDoneTask.getDoneStatus());
        assertTrue(Task.getDoneStatus(aDoneTask));
    }


    public void testMarkAsDone() {
        aDoneTask.markAsDone();
        assertTrue(aDoneTask.getDoneStatus());
        aDoneTask.markAsDone();
        assertTrue(Task.getDoneStatus(aDoneTask));
    }


    public void testMarkAsToDo() {
        aDoneTask.markAsToDo();
        assertFalse(aDoneTask.getDoneStatus());
        Task.markAsToDo(aDoneTask);
        assertFalse(aDoneTask.getDoneStatus());
    }
}
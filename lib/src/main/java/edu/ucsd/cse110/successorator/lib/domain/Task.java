package edu.ucsd.cse110.successorator.lib.domain;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

//import edu.ucsd.cse110.successorator.lib.util.errors.NotImplementedException;


/*
 * This class is an object that represents a single task
 *
 * Private variables:
 * String taskText - contains the user input for what the task is
 * boolean isDone - whether or not the task has been marked as done
 *
 * Constructor:
 * 1. initializes the String taskText to constructor parameter
 * 2. intializes isDone to false by default;
 */
public class Task implements Serializable {
    private final @Nullable Integer id;
    private final String text;
    private final boolean isDone;
    private int sortOrder;

    private static ArrayList<Integer> DoneToday = new ArrayList<>();;


    private static int maxOrder = 0;
    private static int minOrder = 0;


    public Task(@Nullable Integer id, String text, boolean isDone, Integer sortOrder) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
        this.sortOrder = sortOrder;
        if (maxOrder < sortOrder) {
            maxOrder = sortOrder;
        }
        if (minOrder >sortOrder){
            minOrder = sortOrder;
        }

    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public Task withId(@Nullable Integer id) {
        return new Task(id, text, isDone, sortOrder);
    }

    public String getText() {
        return text;
    }


    public Task withText(String text) {
        return new Task(id, text, isDone, sortOrder);
    }

    public boolean isDone() {
        return isDone;
    }

    public Task withDone(boolean isDone) {
        if(isDone){
            maxOrder = maxOrder +1;
            this.sortOrder = maxOrder;
            DoneToday.add(this.id);
        }else{
            minOrder = minOrder -1;
            this.sortOrder = minOrder;
            DoneToday.remove(this.id);
        }
        return new Task(id, text, isDone, sortOrder);
    }
    public static ArrayList<Integer> getDoneToday(){
        return DoneToday;
    }

    public static void clearDoneToday(){
        DoneToday = new ArrayList<Integer>();
    }
    public Integer getSortOrder() {
        return sortOrder;
    }


    public Task withSortOrder(int sortOrder) {
        return new Task(id, text, isDone, sortOrder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return isDone == task.isDone && Objects.equals(id, task.id) && Objects.equals(text, task.text) && Objects.equals(sortOrder, task.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, isDone, sortOrder);
    }
}

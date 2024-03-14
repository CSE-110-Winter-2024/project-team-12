package edu.ucsd.cse110.successorator.lib.domain;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;

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
    private String text;
    private final boolean isDone;
    private int sortOrder;

    private int isRecurring;

    private LocalDate date;
    private Tag tag;
    private static ArrayList<Integer> DoneToday = new ArrayList<>();;

    Calendar calendar = Calendar.getInstance();

    private static int maxOrder = 0;
    private static int minOrder = 0;


    public Task(@Nullable Integer id, String text, boolean isDone, Integer sortOrder, LocalDate date, Tag tag, int isRecurring) {
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
        this.date = date;
        this.tag = tag;
        this.isRecurring =isRecurring;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public Task withId(@Nullable Integer id) {
        return new Task(id, text, isDone, sortOrder, date, tag, isRecurring);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Task withText(String text) {
        return new Task(id, text, isDone, sortOrder, date, tag, isRecurring);
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
        return new Task(id, text, isDone, sortOrder, date, tag, isRecurring);
    }

    public Task withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public static ArrayList<Integer> getDoneToday(){
        // for when the actual date changes
        return DoneToday;
    }
    public static void setDoneToday(ArrayList<Integer> todayList){
        DoneToday = todayList;
    }

    public static void clearDoneToday(){
        DoneToday = new ArrayList<Integer>();
    }
    public Integer getSortOrder() {
        return sortOrder;
    }
    public LocalDate getDate(){
        return date;
    }

    public void setTag(Tag tag){
        this.tag = tag;
    }

    public Tag getTag(){
        return tag;
    }

    public Task withSortOrder(int sortOrder) {
        return new Task(id, text, isDone, sortOrder, date, tag, isRecurring);
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
        return Objects.hash(id, text, isDone, sortOrder, date);
    }


    public int isRecurring() {
        return isRecurring;
    }

    public void setRecurring(int recurring) {
        isRecurring = recurring;
    }
}

package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Tag;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;


public class MockSimpleSubject extends SimpleSubject {

    private Task value = new Task(1,"task",false,1, LocalDate.now(), Tag.ERRANDS,0);

    public List<String> observers = new java.util.ArrayList<>();


    public Task getValue() {
        return value;
    }


    public void setValue(Task value) {
        this.value = value;
        notifyObservers();
    }


    public void observe(String observer) {
        observer=onChanged(observer, value);
        observers.add(observer);
    }


    public void removeObserver(String observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of the subject's new value. Used internally.
     */
    public List<String> mockNotifyObservers() {
        for (int i=0; i<observers.size();i++) {
            observers.set(i,onChanged(observers.get(i), value));
        }
        return observers;
    }

    public String onChanged(String observer, Task value) {
        observer=observer+"_observed_"+value.getText();
        return observer;
    }
}


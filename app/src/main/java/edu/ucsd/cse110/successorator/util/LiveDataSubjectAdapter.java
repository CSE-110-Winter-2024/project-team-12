package edu.ucsd.cse110.successorator.util;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class LiveDataSubjectAdapter<T> implements Subject<T> {

    private final LiveData<T> adaptee;

    public LiveDataSubjectAdapter(LiveData <T> adaptee) {
        this.adaptee=adaptee;
    }

    public T getValue() {
        return adaptee.getValue();
    }

    public void observe(Observer<T> observer) {
        adaptee.observeForever(observer::onChanged);
    }

    public void removeObserver(Observer<T> observer) {
        adaptee.removeObserver(observer::onChanged);
    }
}
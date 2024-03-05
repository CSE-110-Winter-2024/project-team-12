package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

public class TomorrowTask extends Task {

    public TomorrowTask(@Nullable Integer id, String text, boolean isDone, Integer sortOrder) {
        super(id, text, isDone, sortOrder);
        dueDate=TaskDateType.TOMORROW;
    }

    @Override
    public Task withId(@Nullable Integer id) {
        return null;
    }

    @Override
    public Task withText(String text) {
        return null;
    }

    @Override
    public Task withDone(boolean isDone) {
        return null;
    }

    @Override
    public Task withSortOrder(int sortOrder) {
        return null;
    }
}

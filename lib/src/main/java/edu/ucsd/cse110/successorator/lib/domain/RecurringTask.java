package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

public class RecurringTask extends Task {
    public RecurringTask(@Nullable Integer id, String text, boolean isDone, Integer sortOrder) {
        super(id, text, isDone, sortOrder, TaskDateType.RECURRING.ordinal());
    }
}

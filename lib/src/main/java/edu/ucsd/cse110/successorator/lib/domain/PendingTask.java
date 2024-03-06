package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

public class PendingTask extends Task {
    public PendingTask(@Nullable Integer id, String text, boolean isDone, int sortOrder) {
        super(id, text, isDone, sortOrder, TaskDateType.PENDING.ordinal());
    }
}

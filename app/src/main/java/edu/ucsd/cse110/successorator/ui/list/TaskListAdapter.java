package edu.ucsd.cse110.successorator.ui.list;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;



import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.databinding.ListItemBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;


public class TaskListAdapter extends ArrayAdapter<Task> {
    Consumer<Integer> onDeleteClick;
    public TaskListAdapter(Context context,
                           List<Task> tasks,
                           Consumer<Integer> onDeleteClick) {
        // This sets a bunch of stuff internally, which we can access
        // with getContext() and getItem() for example.
        //
        // Also note that ArrayAdapter NEEDS a mutable List (ArrayList),
        // or it will crash!
        super(context, 0, new ArrayList<>(tasks));
        this.onDeleteClick=onDeleteClick;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the flashcard for this position.
        var task = getItem(position);
        assert task != null;

        // Check if a view is being reused...
        ListItemBinding binding;
        if (convertView != null) {
            // if so, bind to it
            binding = ListItemBinding.bind(convertView);
        } else {
            // otherwise inflate a new view from our layout XML.
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemBinding.inflate(layoutInflater, parent, false);
        }

        // Populate the view with the flashcard's data.
        // binding..setText(flashcard.front());
        binding.checkBox.setOnClickListener(v-> {
            var id=task.getId();
            assert id !=null;
            onDeleteClick.accept(id);
        });

        binding.checkBox.setText(task.getTaskText());
        if(task.getDoneStatus()){
            binding.checkBox.setPaintFlags(binding.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            binding.checkBox.setPaintFlags(binding.checkBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return binding.getRoot();
    }

    // The below methods aren't strictly necessary, usually.
    // But get in the habit of defining them because they never hurt
    // (as long as you have IDs for each item) and sometimes you need them.

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        var task = getItem(position);
        assert task != null;

        var id = task.getId();
        assert id != null;

        return id;
    }
}

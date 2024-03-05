package edu.ucsd.cse110.successorator.ui.list;
// imports
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
import edu.ucsd.cse110.successorator.databinding.TaskListItemBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;


public class TaskListAdapter extends ArrayAdapter<Task> {
    private final Consumer<Task> onTaskClicked;

    public TaskListAdapter(Context context,
                           List<Task> tasks,
                           Consumer<Task> onTaskClicked) {
        // This sets a bunch of stuff internally, which we can access
        // with getContext() and getItem() for example.
        //
        // Also note that ArrayAdapter NEEDS a mutable List (ArrayList),
        // or it will crash!
        super(context, 0, new ArrayList<>(tasks));
        this.onTaskClicked = onTaskClicked;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the flashcard for this position.
        var task = getItem(position);
        assert task != null;

        // Check if a view is being reused...
        TaskListItemBinding binding;
        if (convertView != null) {
            // if so, bind to it
            binding = TaskListItemBinding.bind(convertView);
        } else {
            // otherwise inflate a new view from our layout XML.
            var layoutInflater = LayoutInflater.from(getContext());
            binding = TaskListItemBinding.inflate(layoutInflater, parent, false);
        }

        // M -> V
        // Make the view match the model from the repo.
        binding.taskText.setText(task.getText());
        if (task.isDone()) {
            binding.taskText.setPaintFlags(binding.taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            binding.taskText.setPaintFlags(binding.taskText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

        }

        // V -> M
        // Make clicks update the model in the repo.

        binding.taskText.setOnClickListener(v -> {
            onTaskClicked.accept(task);
        });

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

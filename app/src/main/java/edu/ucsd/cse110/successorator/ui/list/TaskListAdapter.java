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

public class TaskListAdapter extends ArrayAdapter<Task>{
    public TaskListAdapter(Context context, List<Task> tasks){
        super(context, 0, new ArrayList<>(tasks));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        var task = getItem(position);
        assert task != null;

        ListItemBinding binding;
        if(convertView != null){
            binding = ListItemBinding.bind(convertView);
        } else {
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemBinding.inflate(layoutInflater, parent, false);
        }
        binding.checkBox.setText(task.getTaskText());
        if(task.getDoneStatus()){
            binding.checkBox.setPaintFlags(binding.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            binding.checkBox.setPaintFlags(binding.checkBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return binding.getRoot();
    }
}

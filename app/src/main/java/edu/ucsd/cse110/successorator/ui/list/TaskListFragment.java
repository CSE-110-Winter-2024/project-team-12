package edu.ucsd.cse110.successorator.ui.list;

import static edu.ucsd.cse110.successorator.lib.domain.Tag.HOME;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.TaskListBinding;
import edu.ucsd.cse110.successorator.lib.domain.Tag;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.util.TagUtils;

public class TaskListFragment extends Fragment {
    public static final String ARG_FILTER_DATE = "FILTER_DATE";
    private MainViewModel activityModel;
    private TaskListBinding view;
    private TaskListAdapter adapter;
    private static boolean hasDateFilter = false;
    public static @Nullable LocalDate filterDate = null;
    public static @Nullable Tag filterType = null;

    private Integer selectedTaskId = null;

    public TaskListFragment() {
        // Required empty public constructor

    }

    public static TaskListFragment newInstance(@Nullable LocalDate filterDate, boolean inMainView) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILTER_DATE, filterDate);
        fragment.setArguments(args);
        hasDateFilter = true;
        if(inMainView){
            hasDateFilter = false;
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get args
        var bundle = getArguments();
        if (bundle != null) {
            filterDate = (LocalDate) bundle.getSerializable(ARG_FILTER_DATE);
        } else {
            filterDate = null;
            filterType = null;
        }

        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        // Initialize the Adapter (with an empty list for now)
        Consumer<Task> onTaskClicked = task -> {
            var updatedTask = task.withDone(!task.isDone());
            activityModel.save(updatedTask);
        };

        Function<Tag, Drawable> tagDrawableFactory = tag -> {
            return TagUtils.getTagDrawable(getResources(), tag);
        };

        this.adapter = new TaskListAdapter(requireContext(), List.of(), onTaskClicked, tagDrawableFactory);

        ResourcesCompat.getDrawable(getResources(), R.drawable.e_button, null);

        activityModel.getOrderedTasks().observe(tasks -> {
            if (tasks == null) return;
            var filteredTasks = tasks.stream().collect(Collectors.toList());

            filteredTasks = filterDate == null ?
                    tasks.stream()
                        .filter(t -> t.getDate() == null)
                        .collect(Collectors.toList())
                    :
                    tasks.stream()
                            .filter(t -> t.getDate() != null && t.getDate().equals(filterDate))
                            .collect(Collectors.toList());

            if(filterDate == null && !hasDateFilter){
                filteredTasks = tasks.stream().collect(Collectors.toList());
            }

            if (filterType != null ) {
                filteredTasks = filteredTasks.stream()
                        .filter(t -> t.getTag().toChar() == filterType.toChar())
                        .collect(Collectors.toList());
            }
            filteredTasks = filteredTasks.stream()
                    .sorted(Comparator.comparing(t -> t.getTag().toString()))
                    .collect(Collectors.toList());

            adapter.clear();
            adapter.addAll(new ArrayList<>(filteredTasks)); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });
    }

    //Override onCreateContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.task_list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Task selectedTask = adapter.getItem(info.position);
            selectedTaskId = selectedTask.getId();
        }

        getActivity().getMenuInflater().inflate(R.menu.task_context_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = TaskListBinding.inflate(inflater, container, false);

        // Set the adapter on the ListView
        view.taskList.setAdapter(adapter);

        // Register the RecyclerView for context menu
        registerForContextMenu(view.taskList);
        return view.getRoot();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = getId();
        LocalDate newDate;

        // Use the item.getItemId() to compare with your menu item IDs
        if (item.getItemId() == R.id.move_to_today) {
            newDate = LocalDate.now();
            activityModel.rescheduleTask(id, newDate);
        } else if (item.getItemId() == R.id.move_to_tomorrow) {
            newDate = LocalDate.now().plusDays(1);
            activityModel.rescheduleTask(id, newDate);
        } else if (item.getItemId() == R.id.mark_as_completed) {
            activityModel.markTaskAsDone(id);
        } else if (item.getItemId() == R.id.delete) {
            activityModel.remove(id);
        } else {
            return super.onContextItemSelected(item);
        }

        return true;
    }

}


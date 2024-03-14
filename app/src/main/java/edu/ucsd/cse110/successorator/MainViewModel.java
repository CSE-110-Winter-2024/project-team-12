package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.LiveData;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {

    // Domain state (true "Model" state)
    private TaskRepository taskRepository;

    // UI state
    private MutableSubject<List<Task>> orderedTasks;

    public boolean empty = true;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getTaskRepository());
                    });

    public MainViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        // Create the observable subjects.
        this.orderedTasks = new SimpleSubject<>();

        // Initialize...

        // When the list of tasks changes (or is first loaded), reset the ordering.
        taskRepository.findAll().observe(tasks -> {
            if (tasks == null) {
                empty = true;
                return; // not ready yet, ignore
            }
            empty = false;
            var newOrderedTasks = tasks.stream()
                    .sorted(Comparator.comparing(Task::getSortOrder))
                    .collect(Collectors.toList());

            orderedTasks.setValue(newOrderedTasks);
        });
    }

    public Subject<List<Task>> getOrderedTasks() {
        return orderedTasks;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public void append(Task task) {
        taskRepository.append(task);
    }

    public void prepend(Task task) {
        taskRepository.prepend(task);
    }

    public void remove(int id) {
        taskRepository.remove(id);
    }

    public void rescheduleTask(int taskId, LocalDate newDate) {
        taskRepository.rescheduleTask(taskId, newDate);
    }

    public void markTaskAsDone(int taskId) {
        // Asynchronously fetch the task, then update its 'isDone' status and save it
        taskRepository.find(taskId).observe(task -> {
            if (task != null) {
                Task updatedTask = task.withDone(true); // Assuming 'withDone' creates a copy with updated isDone status
                save(updatedTask); // Reuse your existing save method which should handle updating the task in the database
            }
        });
    }

}

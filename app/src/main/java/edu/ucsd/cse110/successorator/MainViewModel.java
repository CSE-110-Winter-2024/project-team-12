package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel{

    // Domain state (true "Model" state)
    private final TaskRepository taskRepository;

    // UI state
    private final MutableSubject<List<Task>> orderedTasks;
    private final MutableSubject<Task> topCard;
    private final MutableSubject<String> displayedText;

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
        this.topCard = new SimpleSubject<>();
        this.displayedText = new SimpleSubject<>();

        // Initialize...

        // When the list of tasks changes (or is first loaded), reset the ordering.
        taskRepository.findAll().observe(tasks -> {
            if (tasks == null) return; // not ready yet, ignore

            var newOrderedTasks = tasks.stream()
                    .sorted(Comparator.comparing(Task::getSortOrder))
                    .collect(Collectors.toList());
            orderedTasks.setValue(tasks);
        });

        // When the ordering changes, update the top task.
        orderedTasks.observe(tasks -> {
            if (tasks == null || tasks.size() == 0) return;
            var task = tasks.get(0);
            this.topCard.setValue(task);
        });

        // When the top task changes, update the displayed text and display the front side.
        topCard.observe(task -> {
            if (task == null) return;
            displayedText.setValue(task.getTaskText());
        });

    }


    public void setTaskAsDone(int taskID, boolean done){
        List<Task> tasks = orderedTasks.getValue();
        if (tasks != null) {
            for (Task task : tasks) {
                if (task.getId() != null && task.getId() == taskID) {
                    if (done) {
                        task.markAsDone();
                    } else {
                        task.markAsToDo();
                    }
                    orderedTasks.setValue(tasks); // Trigger UI update
                    break;
                }
            }
        }
    }

    public Subject<String> getDisplayedText() {
        return displayedText;
    }

    public Subject<List<Task>> getOrderedTasks() {
        return orderedTasks;
    }

    public void append(Task task)
    {
        taskRepository.append(task);
    }

    public void remove(int id) {
        taskRepository.remove(id);
    }
}

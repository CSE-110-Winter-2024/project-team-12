package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class RoomTaskRepository implements TaskRepository{

    private final TaskDao taskDao;
    public RoomTaskRepository(TaskDao taskDao) {
        this.taskDao=taskDao;
    }
    @Override
    public Subject<Task> find(int id) {
        var entityLiveData=taskDao.findAsLiveData(id);
        var taskLiveData= Transformations.map(entityLiveData,taskEntity::toTask);
        return new LiveDataSubjectAdapter<>(taskLiveData);
    }
    public Subject<List<Task>> findAll() {
        var entitiesLiveData=taskDao.findAllAsLiveData();
        var tasksLiveData=Transformations.map(entitiesLiveData,entities->{
            return entities.stream()
                    .map(TaskEntity::toTask)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(tasksLiveData);
    }

    public void save(Task task) {
        taskDao.insert(TaskEntity.fromTask(task));
    }

    public void save(List<Task> tasks) {
        var entities=tasks.stream()
                .map(TaskEntity::fromTask)
                .collect(Collectors.toList());
        taskDao.insert(entities);
    }

    public void append(Task task) {
        taskDao.append(TaskEntity.fromTask(task));
    }

    public void prepend(Task task) {
        taskDao.prepend(TaskEntity.fromTask(task));
    }

    public void remove(int id) {
        taskDao.delete(id);
    }

}


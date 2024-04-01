package angel.Task.Services;

import angel.Task.Entity.Task;
import angel.Task.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService{
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public Task findTaskById(Integer idTask) {
        Task task= taskRepository.findById(idTask).orElse(null);
        return task;
    }

    @Override
    public List<Task> listTask() {
        return taskRepository.findAll();
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);

    }

    @Override
    public void removeTask(Task task) {
        taskRepository.delete(task);

    }
}

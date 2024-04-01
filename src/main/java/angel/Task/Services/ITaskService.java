package angel.Task.Services;

import angel.Task.Entity.Task;

import java.util.List;

public interface ITaskService {

    public Task findTaskById(Integer idTask);

    public List<Task> listTask();
    public void saveTask(Task task);

    public void removeTask(Task task);
}

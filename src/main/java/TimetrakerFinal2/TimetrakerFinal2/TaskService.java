package TimetrakerFinal2.TimetrakerFinal2;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class TaskService {
    private final MongoOperations mongoOperations;

    public TaskService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public List<Task> getActiveTasks() {
        Query query = Query.query(Criteria.where("deleted").is(false));
        return mongoOperations.find(query, Task.class);
    }

    public List<Task> getDeletedTasks() {
        Query query = Query.query(Criteria.where("deleted").is(true));
        return mongoOperations.find(query, Task.class);
    }

    public Task getTaskById(String id) {
        return mongoOperations.findById(id, Task.class);
    }

    public Task addTask(Task task) {
        return mongoOperations.insert(task);
    }

    public Task editTask(String id, Task task) {

        Task existingTask = getTaskById(id);
        if (existingTask != null) {
            existingTask.setTaskName(task.getTaskName());
            existingTask.setTime(task.getTime());
            existingTask.setTaskDate(task.getTaskDate());
            existingTask.setCompleted(task.isCompleted());
            return saveTask(existingTask);
        }
        throw new RuntimeException("Task not found");
    }

    @Autowired
    private TaskRepository taskRepository;

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task totalTimeForTask(String id, long time) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("time", time);
        mongoOperations.updateFirst(query, update, Task.class);
        return mongoOperations.findById(id, Task.class);
    }

    public void deleteTask(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        mongoOperations.remove(query, Task.class);
    }

    public void softDeleteTask(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("deleted", true);
        mongoOperations.updateFirst(query, update, Task.class);
    }

    public void softDeleteMonday() {
        if (LocalDate.now().getDayOfWeek() == java.time.DayOfWeek.MONDAY) {
            Query query = Query.query(Criteria.where("deleted").is(false));
            Update update = Update.update("deleted", true);
            mongoOperations.updateMulti(query, update, Task.class);
        }
    }

    public Task completeTask(String id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        return editTask(id, task);
    }

    public Task updateTaskName(String id, String newName) {
        Task task = getTaskById(id);
        task.setTaskName(newName);
        taskRepository.save(task);
        return task;
    }

    @GetMapping
    public String getIndex() {
        return "{'message': 'Min Bakend'}";
    }

}

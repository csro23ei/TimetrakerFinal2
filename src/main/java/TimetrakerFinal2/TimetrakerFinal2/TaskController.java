package TimetrakerFinal2.TimetrakerFinal2;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/active")
    public List<Task> getActiveTasks() {
        return taskService.getActiveTasks();
    }

    @GetMapping("/tasks/deleted")
    public List<Task> getDeletedTasks() {
        return taskService.getDeletedTasks();
    }

    @GetMapping("/task/{id}/time")
    public long getTimeForTask(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return task.getTime();
    }

    @PostMapping("/task")
    public Task addTask(@RequestBody Task task) {
        task.setTime(0);
        task.setTaskDate(LocalDate.now());
        return taskService.addTask(task);
    }

    @PatchMapping("/task/{id}")
    public Task editTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.editTask(id, task);
    }

    @PatchMapping("/task/{id}/time")
    public Task totalTimeForTask(@PathVariable String id, @RequestBody Map<String, Long> request) {
        long time = request.get("time");
        return taskService.totalTimeForTask(id, time);
    }

    @DeleteMapping("/task/{id}")
    public String deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return "{'message': 'Task with id " + id + " has been deleted.'}";
    }

    @DeleteMapping("/task/{id}/soft")
    public String softDeleteTask(@PathVariable String id) {
        taskService.softDeleteTask(id);
        return "{'message': 'Task with id " + id + " has been soft deleted.'}";
    }

    @PatchMapping("/task/{id}/complete")
    public Task completeTask(@PathVariable String id) {
        return taskService.completeTask(id);
    }

    @PatchMapping("/task/{id}/name")
    public Task updateTaskName(@PathVariable String id, @RequestBody Map<String, String> request) {
        String newName = request.get("taskName");
        return taskService.updateTaskName(id, newName);
    }

}

package TimetrakerFinal2.TimetrakerFinal2;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Scheduler {
    private final TaskService taskService;

    public Scheduler(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void softDeleteMonday() {
        taskService.softDeleteMonday();
    }
}

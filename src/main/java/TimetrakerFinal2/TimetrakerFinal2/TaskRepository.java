package TimetrakerFinal2.TimetrakerFinal2;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByCompleted(boolean completed);
}

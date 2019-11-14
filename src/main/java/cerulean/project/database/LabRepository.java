package cerulean.project.database;

import cerulean.project.models.Lab;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LabRepository extends MongoRepository<Lab, String> {
    // findById(String) is implicit -- no need to declare
}

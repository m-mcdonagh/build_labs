package cerulean.project.database;

import cerulean.project.models.Part;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartRepository extends MongoRepository<Part, String> {
    // findById(String) is implicit -- no need to declare
}

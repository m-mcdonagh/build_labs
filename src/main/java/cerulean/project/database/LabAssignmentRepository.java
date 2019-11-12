package cerulean.project.database;

import cerulean.project.models.LabAssignment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LabAssignmentRepository extends MongoRepository<LabAssignment, String> {
    // findById(String) is implicit -- no need to declare

    // The below function might not even be required since the database is denormalized
    @Query("{'user_id':?0}")
    List<LabAssignment> findAllAssignedToUser(String userId);

    // Probably not needed as well
    @Query("{'assigner_id':?0}")
    List<LabAssignment> findAllAssignedByAssigner(String assignerId);
}

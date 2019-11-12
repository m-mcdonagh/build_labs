package cerulean.project.services;

import cerulean.project.database.LabRepository;
import cerulean.project.models.Lab;
import org.springframework.beans.factory.annotation.Autowired;

public class LabService {

    @Autowired
    LabRepository labRepository;

}

package cerulean.project.services;

import cerulean.project.database.AccountRepository;
import cerulean.project.database.PartRepository;
import cerulean.project.models.Account;
import cerulean.project.models.LabAssignment;
import cerulean.project.models.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PartControllerService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PartRepository partRepository;

    public void addNewPart(Account creator, Part newPart) {
        if (partRepository.existsById(newPart.get_id())) {
            // If part is already in the DB don't do anything
            throw new RuntimeException("Part with that ID already exists");
        } else {
            creator.getCreatedParts_ids().add(newPart.get_id());
            accountRepository.save(creator);
            partRepository.save(newPart);
        }
    }

    public List<Part> getPartsCreatedByUser(Account user) {
        List<Part> result = new ArrayList<>();
        partRepository.findAllById(user.getCreatedParts_ids()).forEach(result::add);
        return result;
    }

    public Part getPart(String partId) {
        return partRepository.findById(partId).orElse(null);
    }
}

package cerulean.project.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Account {

    private String _id;
    private String username;
    private String password;
    private String email;
    private List<String> assignedLabs_ids;
    private List<String> createdLabs_ids;
    private List<String> createdParts_ids;


    public Account(String _id, String username, String password, String email, List<String> assignedLabs_ids, List<String> createdLabs_ids, List<String> createdParts_ids) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.assignedLabs_ids = assignedLabs_ids;
        this.createdLabs_ids = createdLabs_ids;
        this.createdParts_ids = createdParts_ids;
    }

    public String get_id() {
        return _id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getAssignedLabs_ids() {
        return assignedLabs_ids;
    }

    public List<String> getCreatedLabs_ids() {
        return createdLabs_ids;
    }

    public List<String> getCreatedParts_ids() {
        return createdParts_ids;
    }


}

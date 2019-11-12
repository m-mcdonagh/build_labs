package cerulean.project.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LabAssignment {
    private String labId;
    private String assigner_id;
    private String user_id;
    private Boolean complete;
    public LabAssignment(String labId,String assigner_id,String user_id,Boolean complete){
        this.labId = labId;
        this.assigner_id = assigner_id;
        this.user_id = user_id;
        this.complete = complete;
    }
    public String getLabId() {
        return labId;
    }

    public String getAssigner_id() {
        return assigner_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public Boolean getComplete() {
        return complete;
    }

}

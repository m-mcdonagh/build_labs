package cerulean.project.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LabAssignment {

    @Id
    private String _id;

    private String labId;
    private String assigner_id;
    private String user_id;
    private Boolean complete;
    private Integer currentStep;
    private Integer totalSteps;
    private String lab_name;
    private String user_name;

    public LabAssignment(String _id,String labId, String assigner_id, String user_id, Boolean complete, Integer totalSteps,String lab_name,String user_name) {
        this._id = _id;
        this.labId = labId;
        this.assigner_id = assigner_id;
        this.user_id = user_id;
        this.complete = complete;
        this.currentStep = 0;
        this.totalSteps = totalSteps;
        this.lab_name = lab_name;
        this.user_name = user_name;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String get_id() {
        return _id;
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

    public Integer getCurrentStep() {
        return currentStep;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(Integer totalSteps) {
        this.totalSteps = totalSteps;
    }



    public void setCurrentStep(Integer currentStep) {
        if (currentStep >= totalSteps) {
            complete = true;
            this.currentStep = 0;
        } else {
            this.currentStep = currentStep;
        }
    }
}

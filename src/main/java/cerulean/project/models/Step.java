package cerulean.project.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Step {

    private Integer id; //Index into lab arraylist
    private Integer parentIndex; // What part is this step attached to
    private Integer parentSlot; // List Index in slotPoints in Part
    private List children; //Steps that go after this step
    private Part newPart; // Part that this step uses
    private Integer rotation; // Is the part rotated
    private String instruction; // The instructions for this step
    private String name; //Step name

    public Step(Integer id, Integer parentIndex, Integer parentSlot, List children, Part newPart, Integer rotation, String instruction, String name){
        this.id = id;
        this.parentIndex = parentIndex;
        this.parentSlot = parentSlot;
        this.children = children;
        this.newPart = newPart;
        this.rotation = rotation;
        this.instruction = instruction;
        this.name = name;
    }
    @Override
    public boolean equals(Object obj){

        Step step = (Step) obj;
        return this.id.equals(step.getId())
                && this.parentIndex.equals(step.getParentIndex())
                && this.rotation.equals(step.getRotation())
                && this.newPart.equals(step.getNewPart())
                && this.parentSlot.equals(step.parentSlot);

    }
    public Integer getId() {
        return id;
    }

    public Integer getParentIndex() {
        return parentIndex;
    }

    public Integer getParentSlot() {
        return parentSlot;
    }

    public List getChildren() {
        return children;
    }

    public Part getNewPart() {
        return newPart;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getName(){return this.name;}

    public void setTitle(String name){this.name=name;}

    public boolean stepMatches(Step other) {
        return     this.rotation.equals(other.rotation)
                && this.id.equals(other.id)
                && this.parentSlot.equals(other.parentSlot)
                && this.parentIndex.equals(other.parentIndex)
                && this.newPart.get_id().equals(other.newPart.get_id());
    }
}

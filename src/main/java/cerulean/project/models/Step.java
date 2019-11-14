package cerulean.project.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Step {

    private Integer index; //Index into lab arraylist
    private Integer parentIndex; // What part is this step attached to
    private Integer parentSlot; // List Index in slotPoints in Part
    private List children; //Steps that go after this step
    private Part newPart; // Part that this step uses
    private Integer rotation; // Is the part rotated
    private String instructions; // The instructions for this step

    public Step(Integer index, Integer parentIndex, Integer parentSlot, List children, Part newPart, Integer rotation, String instructions){
        this.index = index;
        this.parentIndex = parentIndex;
        this.parentSlot = parentSlot;
        this.children = children;
        this.newPart = newPart;
        this.rotation = rotation;
        this.instructions = instructions;
    }
    public Integer getIndex() {
        return index;
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

    public String getInstructions() {
        return instructions;
    }
}

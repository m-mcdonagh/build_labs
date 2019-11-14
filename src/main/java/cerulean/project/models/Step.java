package cerulean.project.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Step {

    private Integer index; //Index into lab arraylist
    private Step parent; // What part is this step attached to
    private Integer parentSlot; // List Index in slotPoints in Part
    private List children; //Steps that go after this step
    private Part newPart; // Part that this step uses
    private Integer rotation; // Is the part rotated
    private String instructions; // The instructions for this step

    public Step(Integer index, Step parent, Integer parentSlot, List children, Part newPart, Integer rotation, String instructions){
        this.index = index;
        this.parent = parent;
        this.parentSlot = parentSlot;
        this.children = children;
        this.newPart = newPart;
        this.rotation = rotation;
        this.instructions = instructions;
    }
    @Override
    public boolean equals(Object obj){

        Step step = (Step) obj;
        if(this.index != step.getIndex())
            return false;
        if(this.parent != step.getParent())
            return false;
        if(this.rotation != step.getRotation())
            return false;
        if(this.newPart != step.getNewPart())
            return false;
        if(this.parentSlot != step.getParentSlot())
            return false;
        return true;
    }
    public Integer getIndex() {
        return index;
    }

    public Step getParent() {
        return parent;
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

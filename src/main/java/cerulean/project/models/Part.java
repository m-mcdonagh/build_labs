package cerulean.project.models;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
public class Part{

    @Id
    private String _id;
    private String name;
    private String img; // TODO replace this with actual file handle implementation
    private List<Double> dimensions;
    private List<List<Double>> slotPoints;
    private List<Double> connectorPoint;
    private Boolean ispublished;

    public Part(String name, String img, List<Double> dimensions, List<List<Double>> slotPoints, List<Double> connectorPoint) {
        this._id = UUID.randomUUID().toString();
        this.name = name;
        this.img = img;
        this.dimensions = dimensions;
        this.slotPoints = slotPoints;
        this.connectorPoint = connectorPoint;
        this.ispublished = false;
    }
    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){this.name = name;}

    public String getImg() {
        return img;
    }

    public List<Double> getDimensions() {
        return dimensions;
    }

    public List<List<Double>> getSlotPoints() {
        return slotPoints;
    }

    public void setSlotPoints(List<List<Double>> slotPoints){this.slotPoints = slotPoints; }

    public List<Double> getConnectorPoint() {
        return connectorPoint;
    }

    public void setConnectorPoint(List<Double> connectorPoint){this.connectorPoint = connectorPoint;}

    public Boolean getIspublished(){return ispublished;}

    public void setIspublished(Boolean ispublished){this.ispublished = ispublished;}


    private void set_id(String id){
        this._id = id;
    }

    public Part clonePart(){
        String id = UUID.randomUUID().toString();
        this.set_id(id);
        this.setName(this.getName()+" Copy");
        this.setIspublished(false);

        return this;
    }

//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        Part cloned = (Part)super.clone();
//        return cloned;
//    }
//
//    public Part clonePart(Part part) throws CloneNotSupportedException {
//        Part newPart = (Part)part.clone();
//        String id = UUID.randomUUID().toString();
//        newPart.set_id(id);
//        newPart.setName(newPart.getName() + " COPY");
//        return newPart;
//    }

}

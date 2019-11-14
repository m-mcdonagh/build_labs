package cerulean.project.models;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Part {

    @Id
    private String _id;
    private String name;
    private Binary img; // TODO replace this with actual file handle implementation
    private List<Double> dimensions;
    private List<List<Double>> slotPoints;
    private List<Double> connectorPoint;
    public Part(String _id, String name, Binary img, List<Double> dimensions, List<List<Double>> slotPoints, List<Double> connectorPoint) {
        this._id = _id;
        this.name = name;
        this.img = img;
        this.dimensions = dimensions;
        this.slotPoints = slotPoints;
        this.connectorPoint = connectorPoint;
    }
    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public Binary getImg() {
        return img;
    }

    public List<Double> getDimensions() {
        return dimensions;
    }

    public List<List<Double>> getSlotPoints() {
        return slotPoints;
    }

    public List<Double> getConnectorPoint() {
        return connectorPoint;
    }

}

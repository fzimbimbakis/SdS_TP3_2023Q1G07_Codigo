package FloatImpl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonConfigReaderFloat {

    private final Float radius;
    private final Float mass;
    private final Float maxX;
    private final Float maxY;
    private final Float whiteX;
    private Float whiteY;
    private final Float whiteV;
    private final Float triangleX;
    private final Float triangleY;
    private final Float minEpsilon;
    private final Float maxEpsilon;

    public JsonConfigReaderFloat(String jsonConfigFilePath) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(jsonConfigFilePath)) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            this.radius = Float.parseFloat(jsonObject.get("radius").toString());
            this.mass = Float.parseFloat(jsonObject.get("mass").toString());
            this.whiteV = Float.parseFloat(jsonObject.get("white_v").toString());
            this.whiteX = Float.parseFloat(jsonObject.get("white_x").toString());
            this.whiteY = Float.parseFloat(jsonObject.get("white_y").toString());
            this.maxX = Float.parseFloat(jsonObject.get("max_x").toString());
            this.maxY = Float.parseFloat(jsonObject.get("max_y").toString());
            this.triangleX = Float.parseFloat(jsonObject.get("triangle_x").toString());
            this.triangleY = Float.parseFloat(jsonObject.get("triangle_y").toString());
            this.minEpsilon = Float.parseFloat(jsonObject.get("min_epsilon").toString());
            this.maxEpsilon = Float.parseFloat(jsonObject.get("max_epsilon").toString());

        } catch (IOException | ParseException e) {
            throw new RuntimeException("Error reading parameters from config.json");
        }
    }

    public Float getRadius() {
        return radius;
    }

    public Float getMass() {
        return mass;
    }

    public Float getWhiteX() {
        return whiteX;
    }

    public Float getWhiteY() {
        return whiteY;
    }

    public Float getWhiteV() {
        return whiteV;
    }

    public Float getMaxX(){
        return maxX;
    }

    public Float getMaxY(){
        return maxY;
    }


    public Float getTriangleX() {
        return triangleX;
    }

    public Float getTriangleY() {
        return triangleY;
    }

    public Float getMinEpsilon() {
        return minEpsilon;
    }

    public Float getMaxEpsilon() {
        return maxEpsilon;
    }

    public void setWhiteY(Float whiteY) {
        this.whiteY = whiteY;
    }
}

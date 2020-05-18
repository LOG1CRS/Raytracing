package lights;

import Objects.Object3D;
import Tools.Intersection;
import Tools.Ray;
import Tools.Vector3D;

import java.awt.*;

/**
 * Light Class, this class contains the basic properties to create a light
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public abstract class Light extends Object3D {
    private double intensity;

    //Constructor
    public Light(Vector3D position, Color color, double intensity){
        super(position, color);
        setIntensity(intensity);
    }

    //Getters & Setters
    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    /**
     * Abstract function, all lights must have this function
     * @param intersection
     * @return
     */
    public abstract float getNDotL(Intersection intersection);

    public Intersection getIntersection(Ray ray){
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}

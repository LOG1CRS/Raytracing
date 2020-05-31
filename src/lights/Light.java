package Lights;

import Objects.Object3D;
import Tools.MathTools.Intersection;
import Tools.MathTools.Ray;
import Tools.MathTools.Vector3D;

import java.awt.*;

/**
 * Light Class, this class contains the basic properties to create a light.
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

    public double getIntensity(Vector3D lightPosition, Vector3D intersectionPosition) {
        double distance = Math.sqrt((intersectionPosition.getX() - lightPosition.getX()) + (intersectionPosition.getY() - lightPosition.getY()) + (intersectionPosition.getZ() - lightPosition.getZ()));
        return (intensity / (Math.pow(distance, 2)));
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    /**
     * Abstract function, all lights must have this function.
     * @param intersection
     * @return
     */
    public abstract float getNDotL(Intersection intersection);

    public Intersection getIntersection(Ray ray){
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}

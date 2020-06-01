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

    /**
     * getIntensity defines the intensity of the light,
     * this function allows the light falloff dividing the intensity by distance squared.
     * @param lightPosition
     * @param intersectionPosition
     * @return the amount of light an object will have
     */
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

    /**
     * Gets the intersection between the light and any object
     * @param ray
     * @return new Intersection
     */
    public Intersection getIntersection(Ray ray){
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}

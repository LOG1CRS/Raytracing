package Lights;

import Tools.MathTools.Intersection;
import Tools.MathTools.Vector3D;

import java.awt.*;

/**
 * DirectionalLight Class, this class creates a kind of light that only sends light in a single direction.
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public class DirectionalLight extends Light {
    private Vector3D direction;

    //Constructor
    public DirectionalLight(Vector3D position, Vector3D direction, Color color, double intensity){
        super(position, color, intensity);
        setDirection(Vector3D.normalize(direction));
    }

    //Getters & Setters
    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    /**
     * getNDotL calculates how this light works with objects
     * @param intersection
     * @return float
     */
    @Override
    public float getNDotL(Intersection intersection) {
        return (float)Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1.0)), 0.0);
    }
}

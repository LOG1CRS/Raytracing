package Lights;

import Tools.MathTools.Intersection;
import Tools.MathTools.Vector3D;

import java.awt.*;

/**
 * PointLight Class, this class creates a kind of light that sends light in all directions.
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public class PointLight extends Light {
    public PointLight(Vector3D position, Color color, double intensity) {
        super(position, color, intensity);
    }

    /**
     *
     * @param intersection
     * @return
     */
    @Override
    public float getNDotL(Intersection intersection) {
        return (float)Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.normalize(Vector3D.substract(getPosition(), intersection.getPosition()))), 0.0);
    }
}

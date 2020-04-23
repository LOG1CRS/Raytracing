package Objects;

import java.awt.*;

public class Sphere extends Object3D{

    private float radius;

    /**
     * Sphere constructor
     * @param origin
     * @param radius
     * @param color
     */
    public Sphere(Vector3D origin, float radius, Color color) {
        super(origin, color);
        setRadius(radius);
    }

    //Getters & Setters

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }


}

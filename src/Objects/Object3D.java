package Objects;

import Interfaces.IIntersectable;
import Tools.Intersection;
import Tools.Ray;
import Tools.Vector3D;

import java.awt.*;

public abstract class Object3D implements IIntersectable {
    private Vector3D position;
    private Color color;

    /**
     * Object3D constructor
     * @param position
     * @param color
     */
    public Object3D(Vector3D position, Color color){
        setPosition(position);
        setColor(color);
    }

    //Getters & Setters

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

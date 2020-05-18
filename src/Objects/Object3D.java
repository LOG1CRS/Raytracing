package Objects;
import Interfaces.IIntersectable;
import Tools.Vector3D;

import java.awt.*;

/**
 * Object3D Class, this class contains the basic properties to crate a 3D object
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public abstract class Object3D implements IIntersectable {

    private Vector3D position;
    private Color color;

    //Constructor
    public Object3D(Vector3D position, Color color) {
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

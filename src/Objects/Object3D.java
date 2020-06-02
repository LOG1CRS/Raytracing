package Objects;
import Interfaces.IIntersectable;
import Objects.ObjectMaterial.Material;
import Tools.MathTools.Vector3D;

import java.awt.*;

/**
 * Object3D Class, this class contains the basic properties to crate a 3D object.
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public abstract class Object3D implements IIntersectable {

    private Vector3D position;
    private Color color;
    private Material material;

    //Constructor
    public Object3D(Vector3D position, Color color, Material material) {
        setPosition(position);
        setColor(color);
        setMaterial(material);
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}

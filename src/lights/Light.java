package lights;

import Objects.Object3D;
import Tools.Intersection;
import Tools.Ray;
import Tools.Vector3D;

import java.awt.*;

/**
 *
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public abstract class Light extends Object3D {
    private double intensity;

    public Light(Vector3D position, Color color, double intensity){
        super(position, color);
        setIntensity(intensity);
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public abstract float getNDotL(Intersection intersection);

    public Intersection getIntersection(Ray ray){
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}

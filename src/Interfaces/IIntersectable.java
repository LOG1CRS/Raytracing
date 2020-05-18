package Interfaces;

import Tools.Intersection;
import Tools.Ray;

/**
 * Java interface, using to add Intersection method
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public interface IIntersectable {
    public abstract Intersection getIntersection(Ray ray);
}

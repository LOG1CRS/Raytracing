package Interfaces;

import Tools.MathTools.Intersection;
import Tools.MathTools.Ray;

/**
 * Java interface, using to add Intersection method.
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public interface IIntersectable {
    public abstract Intersection getIntersection(Ray ray);
}

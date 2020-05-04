package Interfaces;

import Tools.Intersection;
import Tools.Ray;

public interface IIntersectable {
    public abstract Intersection getIntersection(Ray ray);
}

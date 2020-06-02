package Objects;

import Objects.ObjectMaterial.Material;
import Tools.MathTools.Barycentric;
import Tools.MathTools.Intersection;
import Tools.MathTools.Ray;
import Tools.MathTools.Vector3D;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Polygon Class, this class creates a kind of 3D object with triangles.
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public class Polygon extends Object3D {

    private List<Triangle> triangles;
    private Material material;

    //Constructor
    public Polygon(Vector3D position, Triangle[] triangles, Color color, Material material){
        super(position, color, material);
        setTriangles(triangles);
    }

    //Getters & Setters
    public List<Triangle> getTriangles() {
        return triangles;
    }

    /**
     * Set the shape of the polygon, obtaining each vertex of each triangle.
     * @param triangles
     */
    public void setTriangles(Triangle[] triangles) {
        Vector3D position = getPosition();
        Set<Vector3D> uniqueVertices = new HashSet<Vector3D>();
        for(Triangle triangle : triangles){
            uniqueVertices.addAll(Arrays.asList(triangle.getVertices()));
        }

        for(Vector3D vertex : uniqueVertices){
            vertex.setX(vertex.getX() + position.getX());
            vertex.setY(vertex.getY() + position.getY());
            vertex.setZ(vertex.getZ() + position.getZ());
        }

        this.triangles = Arrays.asList(triangles);
    }

    /**
     * Check if there is an intersection and calculates the barycentric coordinates at that position, calling the Barycentric class.
     * @param ray
     * @return The intersection or null if the intersection is behind the camera
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D normal = Vector3D.ZERO();
        Vector3D position = Vector3D.ZERO();

        for(Triangle triangle : getTriangles()){
            Intersection intersection = triangle.getIntersection(ray);
            double intersectionDistance = intersection.getDistance();
            if(intersection != null && intersectionDistance > 0 && (intersectionDistance < distance ||distance < 0)){
                distance = intersectionDistance;
                position = Vector3D.sum(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));

                normal = Vector3D.ZERO();
                double[] uVw = Barycentric.CalculateBarycentricCoordinates(position, triangle);
                Vector3D[] normals = triangle.getNormals();
                for(int i = 0; i < uVw.length; i++) {
                    normal = Vector3D.sum(normal, Vector3D.scalarMultiplication(normals[i], uVw[i]));
                }
            }
        }

        if(distance == -1){
            return null;
        }

        return new Intersection(position, distance, normal, this);
    }
}

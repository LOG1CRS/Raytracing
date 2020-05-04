package Objects;

import Interfaces.IIntersectable;
import Tools.Intersection;
import Tools.Ray;
import Tools.Vector3D;

public class Triangle implements IIntersectable {

    public static final double EPSILON = 0.000000001;

    private Vector3D[] vertices;
    private Vector3D[] normals;

    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3){
        setVertices(vertex1, vertex2, vertex3);
        setNormals(null);
    }

    public Vector3D[] getVertices() {
        return vertices;
    }

    public void setVertices(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3){
        Vector3D[] vertices = new Vector3D[]{vertex1, vertex2, vertex3};
        setVertices(vertices);
    }

    private void setVertices(Vector3D[] vertices) {
        this.vertices = vertices;
    }

    public Vector3D[] getNormals() {
        return normals;
    }

    public Vector3D getNormal(){
        return Vector3D.ZERO();
    }

    public void setNormals(Vector3D[] normals) {
        this.normals = normals;
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        Intersection intersection = new Intersection(null, -1, null, null);

        Vector3D[] vertices = getVertices();
        Vector3D v2v0 = Vector3D.subtraction(vertices[2], vertices[0]);
        Vector3D v1v0 = Vector3D.subtraction(vertices[1], vertices[0]);
        Vector3D vectorP = Vector3D.crossProduct(ray.getDirection(), v1v0);
        double determinant = Vector3D.dotProduct(v2v0, vectorP);
        double invertedDeterminant = 1.0 / determinant;
        Vector3D vectorT = Vector3D.subtraction(ray.getOrigin(), vertices[0]);

        //if the sentence is fulfilled, the ray is out of range
        double u = Vector3D.dotProduct(vectorT, vectorP) * invertedDeterminant;
        if(u < 0 || u > 1){
            return intersection;
        }

        Vector3D vectorQ = Vector3D.crossProduct(vectorT, v2v0);
        double v = Vector3D.dotProduct(ray.getDirection(), vectorQ) * invertedDeterminant;

        //if the sentence is fulfilled, the ray is out of range
        if(v < 0 || (u + v) > (1.0 + EPSILON)){
            return intersection;
        }

        double t = Vector3D.dotProduct(vectorQ, v1v0) * invertedDeterminant;
        intersection.setDistance(t);

        return intersection;
    }
}

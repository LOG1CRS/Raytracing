package Objects;

import Interfaces.IIntersectable;
import Tools.MathTools.Intersection;
import Tools.MathTools.Ray;
import Tools.MathTools.Vector3D;

/**
 * Triangle Class, this class creates triangles.
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public class Triangle implements IIntersectable {

    public static final double EPSILON = 0.000000001;

    private Vector3D[] vertices;
    private Vector3D[] normals;

    //Constructors

    /**
     * Constructor A, gets only each vertex position in three variables and puts normal values in null.
     * @param vertex1
     * @param vertex2
     * @param vertex3
     */
    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3) {
        setVertices(vertex1, vertex2, vertex3);
        setNormals(null);
    }

    /**
     * Constructor B, gets each vertex position in three variables and also gets three variables for normal values.
     * @param vertex1
     * @param vertex2
     * @param vertex3
     * @param normal1
     * @param normal2
     * @param normal3
     */
    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3, Vector3D normal1, Vector3D normal2, Vector3D normal3) {
        this(vertex1, vertex2, vertex3);
        setNormals(normal1, normal2, normal3);
    }

    /**
     * Constructor C, gets an array of vertex and an array of normal values.
     * @param vertices
     * @param normal
     */
    public Triangle(Vector3D[] vertices, Vector3D[] normal) {
        if (vertices.length == 3) {
            setVertices(vertices[0], vertices[1], vertices[2]);
        } else {
            setVertices(Vector3D.ZERO(), Vector3D.ZERO(), Vector3D.ZERO());
        }
        setNormals(normal);
    }

    //Getters & Setters
    public Vector3D[] getVertices() {
        return vertices;
    }

    /**
     * Set triangle vertex with three variables.
     * @param vertex1
     * @param vertex2
     * @param vertex3
     */
    public void setVertices(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3) {
        Vector3D[] vertices = new Vector3D[]{vertex1, vertex2, vertex3};
        setVertices(vertices);
    }

    /**
     * Set triangle vertex with array.
     * @param vertices
     */
    private void setVertices(Vector3D[] vertices) {
        this.vertices = vertices;
    }

    /**
     * If the object doesn't have normal values at each vertex,
     * this function adds the normal value of the face at each vertex.
     * @return shape normals
     */
    public Vector3D[] getNormals() {
        if(normals == null){
            Vector3D normal = getNormal();
            setNormals(new Vector3D[]{normal, normal, normal});
        }
        return normals;
    }

    /**
     * Return the normal of each vertex or creates the normal if normal equals null.
     * @return normal of the vertex
     */
    public Vector3D getNormal() {
        Vector3D normal = Vector3D.ZERO();

        Vector3D[] normals = this.normals;
        if (normals == null) {
            Vector3D[] vertices = getVertices();
            Vector3D v = Vector3D.substract(vertices[1], vertices[0]);
            Vector3D w = Vector3D.substract(vertices[2], vertices[0]);

            normal = Vector3D.scalarMultiplication(Vector3D.normalize(Vector3D.crossProduct(v, w)), -1.0);
        } else {
            for (int i = 0; i < normals.length; i++) {
                normal.setX(normal.getX() + normals[i].getX());
                normal.setY(normal.getY() + normals[i].getY());
                normal.setZ(normal.getZ() + normals[i].getZ());
                //normal = Vector3D.normalize(normal);
            }
        }

        return normal;
    }

    /**
     * Set vertex normal values with array.
     * @param normals
     */
    public void setNormals(Vector3D[] normals) {
        this.normals = normals;
    }

    /**
     * Set vertex normal values with three variables.
     * @param normal1
     * @param normal2
     * @param normal3
     */
    public void setNormals(Vector3D normal1, Vector3D normal2, Vector3D normal3) {
        Vector3D[] normals = new Vector3D[]{normal1, normal2, normal3};
        setNormals(normals);
    }

    /**
     * Check if there is an intersection.
     * @param ray
     * @return The intersection
     * @see <a href="https://cadxfem.org/inf/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">Moller-Trumbore intersection algorithm</a>
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        Intersection intersection = new Intersection(null, -1, null, null);

        Vector3D[] vertices = getVertices();
        Vector3D v2v0 = Vector3D.substract(vertices[2], vertices[0]);
        Vector3D v1v0 = Vector3D.substract(vertices[1], vertices[0]);
        Vector3D vectorP = Vector3D.crossProduct(ray.getDirection(), v1v0);
        double determinant = Vector3D.dotProduct(v2v0, vectorP);
        double invertedDeterminant = 1.0 / determinant;
        Vector3D vectorT = Vector3D.substract(ray.getOrigin(), vertices[0]);
        double u = Vector3D.dotProduct(vectorT, vectorP) * invertedDeterminant;

        //if the if sentence is true, the ray is out of range
        if (u < 0 || u > 1) {
            return intersection;
        }

        Vector3D vectorQ = Vector3D.crossProduct(vectorT, v2v0);
        double v = Vector3D.dotProduct(ray.getDirection(), vectorQ) * invertedDeterminant;

        //if the if sentence is true, the ray is out of range
        if (v < 0 || (u + v) > (1.0 + EPSILON)) {
            return intersection;
        }

        double t = Vector3D.dotProduct(vectorQ, v1v0) * invertedDeterminant;
        intersection.setDistance(t);

        return intersection;
    }
}

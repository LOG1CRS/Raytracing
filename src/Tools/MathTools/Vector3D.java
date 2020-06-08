package Tools.MathTools;

/**
 * Vector3D Class, this class contains all calculations to use 3D vectors.
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public class Vector3D {

    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    private double x, y, z;

    //Constructor
    public Vector3D(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    //Getters & Setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Dot product of two vectors.
     * @param vectorA
     * @param vectorB
     * @return double the result of dot product
     */
    public static double dotProduct(Vector3D vectorA, Vector3D vectorB){
        return (vectorA.getX() * vectorB.getX()) + (vectorA.getY() * vectorB.getY()) + (vectorA.getZ() * vectorB.getZ());
    }

    /**
     * Cross product of two vectors.
     * @param vectorA
     * @param vectorB
     * @return Vector3D the result of cross product
     */
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D((vectorA.getY() * vectorB.getZ()) - (vectorA.getZ() * vectorB.getY()),
                (vectorA.getZ() * vectorB.getX()) - (vectorA.getX() * vectorB.getZ()),
                (vectorA.getX() * vectorB.getY()) - (vectorA.getY() * vectorB.getX()));
    }

    /**
     * Magnitude of a vector.
     * We can return the square root of the Dot product of the same vector to get the magnitude of that vector.
     * @param vectorA
     * @return double the result of magnitude
     */
    public static double magnitude(Vector3D vectorA){
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }

    /**
     * Sum of two vectors.
     * @param vectorA
     * @param vectorB
     * @return Vector3D the result of the sum of two vectors
     */
    public static Vector3D sum(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() + vectorB.getX(), vectorA.getY() + vectorB.getY(), vectorA.getZ() + vectorB.getZ());
    }

    /**
     * Sum for a scalar value
     * @param vectorA
     * @param value
     * @return Vector3D the result of the sum between vector and scalar value
     */
    public static Vector3D sum(Vector3D vectorA, float value){
        return new Vector3D(vectorA.getX() + value, vectorA.getY() + value, vectorA.getZ() + value);
    }

    /**
     * Subtraction of two vectors.
     * @param vectorA
     * @param vectorB
     * @return Vector3D the result of the subtraction of two vectors
     */
    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() - vectorB.getX(), vectorA.getY() - vectorB.getY(), vectorA.getZ() - vectorB.getZ());
    }

    /**
     * Normalization of a vector.
     * @param vectorA
     * @return Vector3D the result of the normalize of a vector
     */
    public static Vector3D normalize(Vector3D vectorA){
        double mag = Vector3D.magnitude(vectorA);
        return new Vector3D(vectorA.getX() / mag, vectorA.getY() / mag, vectorA.getZ() / mag);
    }

    /**
     * Multiply a vector by a scalar.
     * @param vectorA
     * @param scalar
     * @return Vector3D the result of multiply vector to a scalar
     */
    public static Vector3D scalarMultiplication(Vector3D vectorA, double scalar){
        return new Vector3D(vectorA.getX() * scalar, vectorA.getY() * scalar, vectorA.getZ() * scalar);
    }

    /**
     * Clones the vector that is calling this function to keep separate references in memory.
     * @return Vector3D the same vector
     */
    public Vector3D clone(){
        return new Vector3D(getX(), getY(), getZ());
    }

    /**
     * Returns a vector zero.
     * @return Vector3D ZERO
     */
    public static Vector3D ZERO(){
        return ZERO.clone();
    }

    /**
     * Defines the way to print a vector in console.
     * @return the system to print a vector
     */
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }
}

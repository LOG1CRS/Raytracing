package Objects.ObjectMaterial;

/**
 *  Material Class, this class contains the 4 bias values that allows create Blinn-phong reflection
 *  (ambient, diffuse, specular, shininess).
 *  @author LOG1CRS
 */
public class Material {
    private double Diffuse;
    private double Shininess;

    //Constructor
    public Material(double diffuse, double shininess) {
        setDiffuse(diffuse);
        setShininess(shininess);
    }

    //Getters & Setters
    public double getDiffuse() {
        return Diffuse;
    }

    public void setDiffuse(double diffuse) {
        Diffuse = diffuse;
    }

    public double getShininess() {
        return Shininess;
    }

    public void setShininess(double shininess) {
        Shininess = shininess;
    }


    public static float[] calculateReflection(){
        float[] reflection = new float[0];
        return reflection;
    }
}

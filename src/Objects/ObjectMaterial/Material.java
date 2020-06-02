package Objects.ObjectMaterial;

/**
 *  Material Class, this class contains the 4 bias values that allows create Blinn-phong reflection
 *  (ambient, diffuse, specular, shininess).
 *  @author LOG1CRS
 */
public class Material {
    private double Ambient;
    private double Diffuse;
    private double Specular;
    private double Shininess;

    //Constructor
    public Material(double ambient, double diffuse, double specular, double shininess) {
        setAmbient(ambient);
        setDiffuse(diffuse);
        setSpecular(specular);
        setShininess(shininess);
    }

    //Getters & Setters
    public double getAmbient() {
        return Ambient;
    }

    public void setAmbient(double ambient) {
        Ambient = ambient;
    }

    public double getDiffuse() {
        return Diffuse;
    }

    public void setDiffuse(double diffuse) {
        Diffuse = diffuse;
    }

    public double getSpecular() {
        return Specular;
    }

    public void setSpecular(double specular) {
        Specular = specular;
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

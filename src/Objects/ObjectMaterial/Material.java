package Objects.ObjectMaterial;

import Lights.Light;
import Tools.MathTools.Intersection;
import Tools.MathTools.Vector3D;

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


    /**
     * getSpecular, calculates the third variable (specular) according the Blinn-phong calculation.
     *
     * @param light
     * @param closesIntersection
     * @param cameraPosition
     * @return Specular value
     */
    public static double getSpecular(Light light, Intersection closesIntersection, Vector3D cameraPosition){
        double shininess = closesIntersection.getObject().getMaterial().getShininess();
        Vector3D lightNormalize = Vector3D.normalize(light.getPosition());
        Vector3D intersectionNormalize = Vector3D.normalize(cameraPosition);
        Vector3D lightPlusView = Vector3D.normalize(Vector3D.sum(lightNormalize, intersectionNormalize));

        double magnitudeLightPlusView = Vector3D.magnitude(lightPlusView);
        Vector3D H = Vector3D.normalize(Vector3D.scalarMultiplication(lightPlusView, magnitudeLightPlusView));
        double specular = Math.pow((Vector3D.dotProduct(H, closesIntersection.getNormal())), shininess);

        return specular;
    }
}

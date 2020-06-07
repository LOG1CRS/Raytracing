package Objects.ObjectMaterial;

import Lights.Light;
import Main.Raytracer;
import Objects.Object3D;
import Tools.MathTools.Intersection;
import Tools.MathTools.Ray;
import Tools.MathTools.Vector3D;

import java.util.ArrayList;

/**
 *  Material Class, this class allows add material to the objects,
 *  those materials are: blinn-phong, reflection, refraction.
 *  @author LOG1CRS
 */
public class Material {
    private double Diffuse;
    private double Shininess;
    private double Refraction;
    private String MaterialType;

    //Constructor
    public Material(double diffuse, double shininess, double Refraction, String MaterialType) {
        setDiffuse(diffuse);
        setShininess(shininess);
        setRefraction(Refraction);
        setMaterialType(MaterialType);
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

    public double getRefraction() {
        return Refraction;
    }

    public void setRefraction(double refraction) {
        Refraction = refraction;
    }

    public String getMaterialType() {
        return MaterialType;
    }

    public void setMaterialType(String materialType) {
        MaterialType = materialType;
    }

    /**
     * calculateSpecular, calculates the specular value according the Blinn-phong calculation.
     * @param light
     * @param closesIntersection
     * @param cameraPosition
     * @return Specular value
     */
    public static double calculateSpecular(Light light, Intersection closesIntersection, Vector3D cameraPosition){
        double shininess = closesIntersection.getObject().getMaterial().getShininess();
        Vector3D lightNormalize = Vector3D.normalize(light.getPosition());
        Vector3D intersectionNormalize = Vector3D.normalize(cameraPosition);
        Vector3D lightPlusCamera = Vector3D.normalize(Vector3D.sum(lightNormalize, intersectionNormalize));
        double magnitudeLightPlusView = Vector3D.magnitude(lightPlusCamera);

        //Half Vector = (Light + Viewer) / (|light + viewer|)
        Vector3D halfVector = Vector3D.normalize(Vector3D.scalarMultiplication(lightPlusCamera, 1/magnitudeLightPlusView));
        //Specular = (Normal·HalfVector)^Shininess
        double specular = Math.pow((Vector3D.dotProduct(halfVector, closesIntersection.getNormal())), shininess);

        return specular;
    }

    /**
     * calculateReflection, this function calculates the reflection of the objects with the "reflective" material type,
     * using reflective calculation and throwing a ray to check if the current object reflects any object.
     * @param viewer
     * @param closestIntersection
     * @param objects
     * @return the colors of the object intersected that will be painted in the current object
     */
    public static float[] calculateReflection(Vector3D viewer, Intersection closestIntersection,  ArrayList<Object3D> objects){
        float[] reflectiveColor = {0f, 0f, 0f};
        Vector3D viewerCalculate = Vector3D.substract(closestIntersection.getPosition(), viewer);

        //R = 2(viewer·normal)(normal)+viewer
        double VdotN = (Vector3D.dotProduct(closestIntersection.getNormal(), viewerCalculate) * -2);
        Vector3D Rvalue  = Vector3D.sum(Vector3D.scalarMultiplication(closestIntersection.getNormal(), VdotN), viewerCalculate);

        //Throw ray from closesIntersection to Rvalue, get color in that intersection, and return the color
        Ray reflectionRay = new Ray(closestIntersection.getPosition(), Rvalue);
        Intersection reflectionIntersection = Raytracer.raycast(reflectionRay, objects, closestIntersection.getObject(), null);

        if (reflectionIntersection != null && closestIntersection.getObject() != reflectionIntersection.getObject()){
            reflectiveColor[0] = reflectionIntersection.getObject().getColor().getRed()/255.0f;
            reflectiveColor[1] = reflectionIntersection.getObject().getColor().getGreen()/255.0f;
            reflectiveColor[2] = reflectionIntersection.getObject().getColor().getBlue()/255.0f;
        }

        return reflectiveColor;
    }

    public static double calculateRefraction(){
        double refraction = 0;

        return refraction;
    }
}

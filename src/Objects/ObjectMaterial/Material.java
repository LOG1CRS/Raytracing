package Objects.ObjectMaterial;

import Lights.Light;
import Lights.PointLight;
import Main.Raytracer;
import Objects.Object3D;
import Objects.Sphere;
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
    private String MaterialType;

    //Constructor
    public Material(double diffuse, double shininess, String MaterialType) {
        setDiffuse(diffuse);
        setShininess(shininess);
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

    /**
     *
     * @param viewer
     * @param ray
     * @param closestIntersection
     * @param objects
     * @return
     */
    public static float[] calculateRefraction(Vector3D viewer, Ray ray, Intersection closestIntersection,  ArrayList<Object3D> objects){
        //Method #1 failed
        /*Vector3D V = ray.getDirection();
        Vector3D P = closestIntersection.getPosition();
        Vector3D N = closestIntersection.getNormal();
        double C1 = Vector3D.dotProduct(closestIntersection.getNormal(), V);
        double C2 = Math.sqrt((1.0f - (C1 * C1)));
        Vector3D refractionVector = Vector3D.sum(V, Vector3D.scalarMultiplication(N, C1-C2));*/

        /*float refractionIndex = 1.4f;
        Vector3D finalValue;

        //method #2 failed
        float cosi = Raytracer.clamp(-1, 1, (float) Vector3D.dotProduct(closestIntersection.getPosition(), closestIntersection.getNormal()));
        float etai = 5, etat = refractionIndex;
        float eta = etai / etat;
        float k = 1 - eta * eta * (1 - cosi * cosi);
        if(k < 0){
            finalValue = Vector3D.ZERO();
        } else{
            Vector3D intersectionEta = Vector3D.scalarMultiplication(closestIntersection.getPosition(), eta);
            double etaCosi = ((eta * cosi) - Math.sqrt(k));
            Vector3D normalEtaCosiK = Vector3D.scalarMultiplication(closestIntersection.getNormal(), etaCosi);
            finalValue = Vector3D.sum(normalEtaCosiK, intersectionEta);
        }*/

        float[] refractiveColor = {0f, 0f, 0f};

        //Try to make an object transparent
        Ray refractionRay = new Ray(closestIntersection.getPosition(), ray.getDirection());
        Intersection refractionIntersection = Raytracer.raycast(refractionRay, objects, closestIntersection.getObject(), null);

        if (refractionIntersection != null && closestIntersection.getObject() != refractionIntersection.getObject()){
            System.out.println(refractiveColor);
            refractiveColor[0] = refractionIntersection.getObject().getColor().getRed()/255.0f;
            refractiveColor[1] = refractionIntersection.getObject().getColor().getGreen()/255.0f;
            refractiveColor[2] = refractionIntersection.getObject().getColor().getBlue()/255.0f;
        }


        return refractiveColor;
    }
}

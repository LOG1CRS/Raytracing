package Main;

import Lights.Light;
import Lights.PointLight;
import Objects.Object3D;
import Objects.Sphere;
import Tools.FileReader.OBJReader;
import Tools.MathTools.Intersection;
import Tools.MathTools.Ray;
import Tools.MathTools.Vector3D;
import Tools.RunTime.RunTimeCalculator;
import Tools.SceneTools.Camera;
import Tools.SceneTools.Scene;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

/**
 *  Raytracer Class, this class guides the code, calling other important classes.
 *  @author LOG1CRS
 *  @author Jafet Rodríguez
 */
public class Raytracer {

    /**
     * Starts Raytracer and initializes a scene, camera, lights and put the objects in scene.
     */
    public static void startRaytracer() {
        System.out.println("Raytracer is running!");
        System.out.println("Please wait...");

        Instant startTime = RunTimeCalculator.start();

        Scene scene01 = new Scene();
        scene01.setCamera(new Camera(new Vector3D(0, 0.5, -5.5), 135, 135, 1024, 1024, 0f, 100f));
        scene01.addLight(new PointLight(new Vector3D(0f, 50f, -65f), Color.WHITE, 40));
        //scene01.addLight(new PointLight(new Vector3D(0f, 10f, 0f), Color.WHITE, 5));
        scene01.addObject(new Sphere(new Vector3D(-10f, -14f, 28f), 3.5f, new Color(28, 108, 169)));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/VWBug.obj", new Vector3D(-11,2, 40), new Color(169,217,227)));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Stand.obj", new Vector3D(-8,-18,40), Color.white));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Cube.obj", new Vector3D(7,-30,30), Color.white));
        //scene01.addObject(OBJReader.GetPolygon("ObjFiles/Ring.obj", new Vector3D(0,-14,28), new Color(255, 164, 27)));
        //scene01.addObject(OBJReader.GetPolygon("ObjFiles/SmallTeapot.obj", new Vector3D(7f, -8f, 30f), new Color(255, 243, 205)));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Floor.obj", new Vector3D(0,-18.3,30), Color.white));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Floor.obj", new Vector3D(0,21.8,30), Color.white));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Wall.obj", new Vector3D(20,-10,20), Color.GREEN));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Wall.obj", new Vector3D(-20,-10,20), Color.RED));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Wall2.obj", new Vector3D(0,-5,48), Color.white));

        BufferedImage image = raytrace(scene01);
        File outputImage = new File("image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException ioe) {
            System.out.println("Something failed");
        }

        Instant finishTime = RunTimeCalculator.end();
        RunTimeCalculator.printRuntime(startTime, finishTime);
    }


    /**
     * Creates the BufferedImage, get each intersection of each ray with the objects, the lights and creates the image.
     * @param scene
     * @return BufferedImage the rendered scene with specified resolution from the camera
     */
    private static BufferedImage raytrace(Scene scene) {
        Camera mainCamera = scene.getCamera();
        ArrayList<Light> lights = scene.getLights();
        float[] nearFarPlanes = mainCamera.getNearFarPlanes();
        BufferedImage image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        ArrayList<Object3D> objects = scene.getObjects();

        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        for (int i = 0; i < positionsToRaytrace.length; i++) {
            for (int j = 0; j < positionsToRaytrace[i].length; j++) {
                double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
                double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();

                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                float cameraZ = (float) mainCamera.getPosition().getZ();
                Intersection closestIntersection = raycast(ray, objects, null, new float[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

                //Background color
                Color pixelColor = Color.BLACK;
                if (closestIntersection != null) {
                    pixelColor = Color.BLACK;
                    for (Light light : lights) {
                        float nDotL = light.getNDotL(closestIntersection);
                        float intensity = (float) light.getIntensity(light.getPosition(), closestIntersection.getPosition()) * nDotL;
                        Color lightColor = light.getColor();
                        Color objColor = closestIntersection.getObject().getColor();
                        float[] lightColors = new float[]{lightColor.getRed() / 255.0f, lightColor.getGreen() / 255.0f, lightColor.getBlue() / 255.0f};
                        float[] objColors = new float[]{objColor.getRed() / 255.0f, objColor.getGreen() / 255.0f, objColor.getBlue() / 255.0f};
                        for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                            objColors[colorIndex] *= intensity * lightColors[colorIndex];
                        }
                        Color diffuse = new Color(clamp(objColors[0], 0, 1),clamp(objColors[1], 0, 1),clamp(objColors[2], 0, 1));
                        pixelColor = addColor(pixelColor, diffuse);
                    }
                }
                image.setRGB(i, j, pixelColor.getRGB());
            }
        }

        return image;
    }

    /**
     * Returns value clamped to according range of min and max.
     * @param value
     * @param min
     * @param max
     * @return min or max value
     */
    private static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * Allows to add custom RGB color to an object.
     * @param original
     * @param otherColor
     * @return new Color
     */
    private static Color addColor(Color original, Color otherColor){
        float red = clamp((original.getRed() / 255.0f) + (otherColor.getRed() / 255.0f), 0, 1);
        float green = clamp((original.getGreen() / 255.0f) + (otherColor.getGreen() / 255.0f), 0, 1);
        float blue = clamp((original.getBlue() / 255.0f) + (otherColor.getBlue() / 255.0f), 0, 1);
        return new Color(red, green, blue);
    }


    /**
     * Raycast checks what is the closest intersection, object by object.
     * @param ray
     * @param objects
     * @param caster
     * @return the closest intersection
     */
    private static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, float[] clippingPlanes) {
        Intersection closestIntersection = null;

        for (int k = 0; k < objects.size(); k++) {
            Object3D currentObj = objects.get(k);
            if (caster == null || !currentObj.equals(caster)) {
                Intersection intersection = currentObj.getIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.getDistance();
                    if (distance >= 0 &&
                            (closestIntersection == null || distance < closestIntersection.getDistance()) &&
                            (clippingPlanes == null || (intersection.getPosition().getZ() >= clippingPlanes[0] &&
                                    intersection.getPosition().getZ() <= clippingPlanes[1]))) {
                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }
}

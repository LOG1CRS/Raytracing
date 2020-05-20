package Main;

import Lights.DirectionalLight;
import Lights.Light;
import Lights.PointLight;
import Objects.Object3D;
import Objects.Sphere;
import Tools.*;
import Tools.FileReader.OBJReader;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Raytracer Class, this class guides the code, calling other important classes
 *  @author LOG1CRS
 *  @author Jafet Rodríguez
 */
public class Raytracer {

    /**
     * Starts Raytracer and initializes a scene, camera, lights and put the objects in scene
     */
    public static void startRaytracer() {
        int arrayTime[] = new int[4];

        System.out.println("Raytracer is running!");
        System.out.println("Please wait...");

        arrayTime[0] = LocalDateTime.now().getSecond();
        arrayTime[1] = LocalDateTime.now().getMinute();

        Scene scene01 = new Scene();
        scene01.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800, 8.2f, 50f));
        scene01.addLight(new PointLight(new Vector3D(-1.2f, 2.5f, -2f), Color.WHITE, 2.5));
        /*scene01.addLight(new DirectionalLight(Vector3D.ZERO(), new Vector3D(0.0, 0.0, 1.0), Color.WHITE, 1.1));
        scene01.addLight(new DirectionalLight(Vector3D.ZERO(), new Vector3D(0.0, -1.0, 0.0), Color.GREEN, 0.1));
        scene01.addObject(new Sphere(new Vector3D(0f, 1f, 5f), 0.5f, Color.RED));
        scene01.addObject(new Sphere(new Vector3D(0.35f, 1f, 4.5f), 0.3f, Color.BLUE));
        scene01.addObject(new Sphere(new Vector3D(4.85f, 1f, 4.5f), 0.3f, Color.PINK));
        scene01.addObject(new Sphere(new Vector3D(2.85f, 1f, 304.5f), 0.5f, Color.BLUE));*/
        scene01.addObject(new Sphere(new Vector3D(0f, -0.5f, 7.5f), 1f, new Color(15, 189, 186)));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Cube.obj", new Vector3D(2.5f, -0.8f, 4f), Color.green));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/CubeQuad.obj", new Vector3D(-3.8f, -0.5f, 9.3f), Color.red));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/SmallTeapot.obj", new Vector3D(0f, 1f, 1.2f), Color.LIGHT_GRAY));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Ring.obj", new Vector3D(0f, -2.8f, 4.5f), Color.BLUE));

        BufferedImage image = raytrace(scene01);
        File outputImage = new File("image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException ioe) {
            System.out.println("Something failed");
        }

        arrayTime[2] = LocalDateTime.now().getSecond();
        arrayTime[3] = LocalDateTime.now().getMinute();

        showRunTimeCode(arrayTime);
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
     * Allows to add custom RGB color to an object
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
     * Raycast checks what is the closest intersection, object by object
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

    /**
     * Prints the code runtime
     * @param arrayTime
     */
    private static void showRunTimeCode(int arrayTime[]){
        //position: 0 = initial seconds
        //position: 1 = initial minutes
        //position: 2 = final seconds
        //position: 3 = final minutes

        if(arrayTime[1] == arrayTime[3]){
            System.out.println("The code runtime was: " + (arrayTime[2] - arrayTime[0]) + " seconds");
        } else{
            System.out.println("The code runtime was: " + ((arrayTime[2] + 60) - arrayTime[0]) + " seconds");
        }
    }
}

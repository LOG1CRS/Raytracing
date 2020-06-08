package Main;

import Lights.Light;
import Lights.PointLight;
import Objects.Object3D;
import Objects.ObjectMaterial.Material;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        //Design 1
        Scene scene01 = new Scene();
        scene01.setCamera(new Camera(new Vector3D(0, 0.5, -5.5), 135, 135, 1200, 1200, 0f, 100f));
        scene01.addLight(new PointLight(new Vector3D(0f, 15f, -10f), Color.WHITE, 7));
        scene01.addLight(new PointLight(new Vector3D(7.5f, 0f, -10f), Color.WHITE, 6));
        scene01.addLight(new PointLight(new Vector3D(-7.5f, 0f, -10f), Color.WHITE, 6));
        scene01.addObject(new Sphere(new Vector3D(-10f, -14f, 28f), 3.5f, Color.white, new Material(2f,4f, "reflective")));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/VWBug.obj", new Vector3D(-11,2, 40), new Color(169,217,227), new Material(1f,2f,"lambert")));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Stand.obj", new Vector3D(-8,-18,40), Color.white, new Material(1f,1f,"reflective")));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Cube.obj", new Vector3D(7,-30,30), Color.white, new Material(1f,1f,"reflective")));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Ring.obj", new Vector3D(0,-14,28), new Color(255, 164, 27), new Material(1f,2f,"lambert")));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/SmallTeapot.obj", new Vector3D(7f, -8f, 30f), new Color(255, 243, 205), new Material(2f,5f,"lambert")));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Floor.obj", new Vector3D(0,-18.3,30), Color.white, new Material(1f,1f,"lambert")));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Wall.obj", new Vector3D(20,-10,20), Color.GREEN, new Material(3,1,"lambert")));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Wall.obj", new Vector3D(-20,-10,20), Color.RED, new Material(3,1, "lambert")));
        scene01.addObject(OBJReader.GetPolygon("ObjFiles/Wall2.obj", new Vector3D(0,-5,48), Color.white, new Material(1,1, "lambert")));

        //Design 2
        /*Scene scene02 = new Scene();
        scene02.setCamera(new Camera(new Vector3D(0, 0.5, -5.5), 135, 135, 1200, 1200, 0f, 100f));
        scene02.addLight(new PointLight(new Vector3D(-7f, 7f, -10f), Color.WHITE, 12.5));
        scene02.addObject(new Sphere(new Vector3D(0f, -5.7f, 28f), 3f, Color.lightGray, new Material(2,1,"reflective")));
        scene02.addObject(OBJReader.GetPolygon("ObjFiles/Pedestal.obj", new Vector3D(0,-16.5,28), new Color(143, 68, 37), new Material(1,1,"lambert")));
        scene02.addObject(OBJReader.GetPolygon("ObjFiles/SmallTeapot.obj", new Vector3D(12.7f, -7.5f, 32f), new Color(255, 243, 205), new Material(0.4,0.4,"lambert")));
        scene02.addObject(OBJReader.GetPolygon("ObjFiles/Pedestal.obj", new Vector3D(13,-16,32), new Color(143, 68, 37), new Material(1,1,"lambert")));
        scene02.addObject(OBJReader.GetPolygon("ObjFiles/VWBug.obj", new Vector3D(-15,-13, 31), new Color(169,217,227), new Material(0.2,0.2,"lambert")));
        scene02.addObject(OBJReader.GetPolygon("ObjFiles/Pedestal.obj", new Vector3D(-13,-16,32), new Color(143, 68, 37), new Material(1,1,"lambert")));
        scene02.addObject(OBJReader.GetPolygon("ObjFiles/Floor.obj", new Vector3D(0,-18.3,40), Color.white, new Material(1f,1f,"lambert")));
        scene02.addObject(OBJReader.GetPolygon("ObjFiles/Wall2.obj", new Vector3D(0,-5,58), new Color(136,136,136), new Material(1,1, "lambert")));*/

        //Design 3
        /*Scene scene03 = new Scene();
        scene03.setCamera(new Camera(new Vector3D(0, 0.5, -5.5), 135, 135, 1200, 1200, 0f, 100f));
        scene03.addLight(new PointLight(new Vector3D(7f, 7f, -10f), Color.WHITE, 12));scene03.addLight(new PointLight(new Vector3D(0f, 0f, -15f), Color.WHITE, 35));
        scene03.addObject(new Sphere(new Vector3D(10f, -9f, 20f), 2f, new Color(56, 19, 96), new Material(1,1,"reflective")));
        scene03.addObject(new Sphere(new Vector3D(-10f, 3f, 20f), 1.8f, new Color(6, 223, 215), new Material(1,1,"reflective")));
        scene03.addObject(new Sphere(new Vector3D(-20f, -10f, 35f), 2f, new Color(48, 57, 96), new Material(1,1,"reflective")));
        scene03.addObject(new Sphere(new Vector3D(10f, 15f, 35f), 2.5f, new Color(255, 204, 0), new Material(1,1,"reflective")));
        scene03.addObject(new Sphere(new Vector3D(-5f, 10f, 30f), 2f, new Color(35, 88, 83), new Material(1,1,"reflective")));
        scene03.addObject(new Sphere(new Vector3D(8f,  1f, 27f), 2f, new Color(254, 170, 170), new Material(1,1,"reflective")));
        scene03.addObject(new Sphere(new Vector3D(-3f, -5.8f, 22f), 2.5f, new Color(132, 24, 24), new Material(1,1,"reflective")));
        scene03.addObject(OBJReader.GetPolygon("ObjFiles/Floor.obj", new Vector3D(0,-18.3,40), Color.white, new Material(1f,1f,"lambert")));
        scene03.addObject(OBJReader.GetPolygon("ObjFiles/Wall2.obj", new Vector3D(0,-5,58), new Color(7,7,7), new Material(1,1, "lambert")));*/

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

        ExecutorService executorService = Executors.newFixedThreadPool(6);

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

                //Here begins the parallel system
                Runnable runnable = draw(i, j, mainCamera, ray, objects, nearFarPlanes, lights, image);
                executorService.execute(runnable);
            }
        }

        executorService.shutdown();
        try{
            if(!executorService.awaitTermination(10, TimeUnit.MINUTES)){
                executorService.shutdownNow();
            }
        }catch (InterruptedException e){
            executorService.shutdownNow();
        } finally {
            if(!executorService.isTerminated()){
                System.out.println("Cancel non-finished");
            }
        }
        executorService.isTerminated();

        return image;
    }

    /**
     * draw is a Runnable function, and runs parallel with multithreading
     * @param i
     * @param j
     * @param mainCamera
     * @param ray
     * @param objects
     * @param nearFarPlanes
     * @param lights
     * @param image
     * @return aRunnable,
     */
    private static Runnable draw(int i, int j, Camera mainCamera, Ray ray, ArrayList<Object3D> objects, float[] nearFarPlanes, ArrayList<Light> lights, BufferedImage image){
        Runnable aRunnable = new Runnable() {
            @Override
            public void run() {

                float cameraZ = (float) mainCamera.getPosition().getZ();
                Intersection closestIntersection = raycast(ray, objects, null, new float[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

                //Background color
                Color pixelColor = Color.BLACK;
                if (closestIntersection != null) {
                    pixelColor = Color.BLACK;
                    for (Light light : lights) {

                        //float[] finalColors = new float[3];

                        //Intensity & FallOff calculations
                        float nDotL = light.getNDotL(closestIntersection);
                        float intensity = (float) light.getIntensity() * nDotL;
                        double fallOff = light.getIntensity(light.getPosition(), closestIntersection.getPosition(), intensity);

                        //Specular calculation
                        double specular = Material.calculateSpecular(light, closestIntersection, mainCamera.getPosition());

                        //Reflection calculation
                        float[] reflectiveColor = {1f, 1f, 1f};
                        if(closestIntersection.getObject().getMaterial().getMaterialType() == "reflective"){
                            reflectiveColor = Material.calculateReflection(mainCamera.getPosition(), closestIntersection, objects);
                        }

                        //Refraction calculation
                        float[] refractiveColor = {1f, 1f, 1f};
                        if(closestIntersection.getObject().getMaterial().getMaterialType() == "refractive"){
                            refractiveColor = Material.calculateRefraction(mainCamera.getPosition(), ray, closestIntersection, objects);
                        }

                        Color lightColor = light.getColor();
                        Color objColor = closestIntersection.getObject().getColor();
                        float diffuseMaterialValue = (float) closestIntersection.getObject().getMaterial().getDiffuse();
                        float[] lightColors = new float[]{lightColor.getRed() / 255.0f, lightColor.getGreen() / 255.0f, lightColor.getBlue() / 255.0f};
                        float[] objColors = new float[]{((objColor.getRed() * diffuseMaterialValue) / 255.0f), (objColor.getGreen() * diffuseMaterialValue)/ 255.0f, (objColor.getBlue() * diffuseMaterialValue) / 255.0f};

                        for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                            objColors[colorIndex] *= intensity * fallOff * reflectiveColor[colorIndex] * lightColors[colorIndex] * specular * refractiveColor[colorIndex];
                        }

                        //Shadows calculation
                        //This ray will come out from the position of the light
                        Ray shadowRay = new Ray(closestIntersection.getPosition(), light.getPosition());
                        Intersection rayShadowIntersection = raycast(shadowRay, objects, closestIntersection.getObject(), new float[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

                        Color diffuse = new Color(clamp(objColors[0], 0, 1),clamp(objColors[1], 0, 1),clamp(objColors[2], 0, 1));
                        //if there are intersections before intersection with the object, there is a shadow
                        if(rayShadowIntersection != null){
                            diffuse = Color.BLACK;
                        }
                        pixelColor = addColor(pixelColor, diffuse);
                    }
                }
                setRGB(image, i, j, pixelColor);
            }
        };
        return aRunnable;
    }

    /**
     * setRGB is a synchronized function that allows to Runnable function share the same resource
     * @param image
     * @param i
     * @param j
     * @param pixelColor
     */
    private static synchronized void setRGB(BufferedImage image, int i, int j, Color pixelColor){
        image.setRGB(i, j, pixelColor.getRGB());
    }

    /**
     * Returns value clamped to according range of min and max.
     * @param value
     * @param min
     * @param max
     * @return min or max value
     */
    public static float clamp(float value, float min, float max) {
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
    public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, float[] clippingPlanes) {
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

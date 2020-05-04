package Main;

import Objects.Object3D;
import Objects.Sphere;
import Tools.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Raytracer {
    /**
     * Starts the Raytracer and initializes a scene
     */
    public static void StartRaytracer(){
        System.out.println(new Date());

        Scene scene01 = new Scene(8.0f,18.0f);
        scene01.setCamera(new Camera(new Vector3D(0, 0, 0), 160, 160, 800, 800));
        scene01.addObject(new Sphere(new Vector3D(0, 0, 14), 0.5f, Color.RED));
        scene01.addObject(new Sphere(new Vector3D(0, 0, 18), 1f, Color.BLUE));

        BufferedImage image = raytrace(scene01);
        File outputImage = new File("image.png");
        try{
            ImageIO.write(image, "png", outputImage);
        } catch (IOException ioe){
            System.out.println("Something failed");
        }

        System.out.println(new Date());
    }

    /**
     * Creates de BufferedImage
     * @param scene
     * @return BufferedImage
     */
    public static BufferedImage raytrace(Scene scene) {
        Camera mainCamera = scene.getCamera();
        BufferedImage image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        ArrayList<Object3D> objects = scene.getObjects();

        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        for (int i = 0; i < positionsToRaytrace.length; i++) {
            for (int j = 0; j < positionsToRaytrace[i].length; j++) {
                double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
                double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();

                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                Intersection closestIntersection = raycast(ray, objects, null);

                //Background color
                Color pixelColor = Color.WHITE;

                if(closestIntersection != null && closestIntersection.getDistance() >= scene.getClippingPlaneNear() && closestIntersection.getDistance() <= scene.getClippingPlaneFar()){
                    pixelColor = closestIntersection.getObject().getColor();
                    System.out.println(closestIntersection.getDistance());
                }
                image.setRGB(i, j, pixelColor.getRGB());
            }
        }

        return image;
    }

    /**
     * raycaset checks what is the closest intersection, object by object
     * @param ray
     * @param objects
     * @param caster
     * @return intersection
     */
    public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster){
        Intersection closestIntersection = null;

        for(int k = 0; k < objects.size(); k++){
            Object3D currentObj = objects.get(k);
            if(caster == null || !currentObj.equals(caster)){
                Intersection intersection = currentObj.getIntersection(ray);
                if(intersection != null){
                    double distance = intersection.getDistance();
                    if(distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance())){
                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }
}

package Main;

import Objects.Object3D;
import Objects.Sphere;
import Tools.Camera;
import Tools.Ray;
import Tools.Scene;
import Tools.Vector3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Raytracer {
    /**
     * Starts the Raytracer and initializes a scene
     */
    public static void StartRaytracer(){
        Scene scene01 = new Scene();
        scene01.setCamera(new Camera(new Vector3D(0.0,0.0,-8.0), 160, 160, 800, 800));
        scene01.addObject(new Sphere(new Vector3D(0,1,5), 0.5f, Color.RED));
    }

    /**
     * Creates de BufferedImage
     * @param scene
     * @return BufferedImage
     */
    public static BufferedImage raytrace(Scene scene){
        Camera mainCamera = scene.getCamera();
        BufferedImage image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        ArrayList<Object3D> objects = scene.getObjects();

        //Calculates the position of each ray in each pixels in the mesh
        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        for(int i = 0; i<positionsToRaytrace.length; i++){
            for(int j = 0; j<positionsToRaytrace[i].length; j++){
                double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                double y = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getY();
                double z = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getZ();

                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
            }
        }

        return image;
    }
}

package Tools;

import Lights.Light;
import Objects.Object3D;


import java.util.ArrayList;

/**
 * Scene Class, this class contains the basic properties to crate a scene
 * @author LOG1CRS
 * @author Jafet Rodríguez
 */
public class Scene {

    private Camera camera;
    private ArrayList<Object3D> objects;
    private ArrayList<Light> lights;

    //Constructor
    public Scene(){
        setObjects(new ArrayList<Object3D>());
        setLights(new ArrayList<Light>());
    }

    //Getters & Setters
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public ArrayList<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object3D> objects) {
        this.objects = objects;
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }

    /**
     * Adds object to scene
     * @param object
     */
    public void addObject(Object3D object){
        getObjects().add(object);
    }

    /**
     * Adds light to scene
     * @param light
     */
    public void addLight(Light light){
        getLights().add(light);
    }
}

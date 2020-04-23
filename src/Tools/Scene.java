package Tools;

import Objects.Object3D;

import java.util.ArrayList;
import java.util.List;


public class Scene {
    private Camera camera;
    private List<Object3D> objects;

    /**
     * Scene constructor
     * Initialize the ArrayList of objects.
     */
    public Scene(){
        setObjects(new ArrayList<Object3D>());
    }

    //Getters & Setters

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public List<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(List<Object3D> objects) {
        this.objects = objects;
    }

    /**
     * Add object to scene
     * @param object
     */
    public void addObject(Object3D object){
        getObjects().add(object);
    }
}

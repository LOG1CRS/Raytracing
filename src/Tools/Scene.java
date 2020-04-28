package Tools;

import Objects.Object3D;

import java.util.ArrayList;


public class Scene {
    private Camera camera;
    private ArrayList<Object3D> objects;
    private double clippingPlaneNear;
    private double clippingPlaneFar;

    /**
     * Scene constructor
     * Initialize the ArrayList of objects.
     * @param clippingPlaneNear
     * @param clippingPlaneFar
     */
    public Scene(double clippingPlaneNear, double clippingPlaneFar) {
        setObjects(new ArrayList<Object3D>());
        setClippingPlaneNear(clippingPlaneNear);
        setClippingPlaneFar(clippingPlaneFar);
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

    public double getClippingPlaneNear() {
        return clippingPlaneNear;
    }

    public void setClippingPlaneNear(double clippingPlaneNear) {
        this.clippingPlaneNear = clippingPlaneNear;
    }

    public double getClippingPlaneFar() {
        return clippingPlaneFar;
    }

    public void setClippingPlaneFar(double clippingPlaneFar) {
        this.clippingPlaneFar = clippingPlaneFar;
    }

    /**
     * Adds object to scene
     * @param object
     */
    public void addObject(Object3D object){
        getObjects().add(object);
    }
}

package Tools;

import Objects.Object3D;

import java.awt.*;

public class Camera extends Object3D {

    // 0 is horizontal and 1 is vertical
    private float[] fieldOfView = new float[2];
    // 0 is width and 1 is height
    private int[] resolution;
    private float defaultZ = 15f;

    /**
     * Camera constructor
     * @param position
     * @param fieldOfViewHorizontal
     * @param fieldOfViewVertical
     * @param widthResolution
     * @param heightResolution
     */
    public Camera(Vector3D position, float fieldOfViewHorizontal, float fieldOfViewVertical, int widthResolution , int heightResolution) {
        super(position, Color.black);
        setFieldOfViewHorizontal(fieldOfViewHorizontal);
        setFieldOfViewVertical(fieldOfViewVertical);
        setResolution(new int[]{widthResolution, heightResolution});
    }

    //Getters & Setters

    public float[] getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(float[] fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    public float getDefaultZ() {
        return defaultZ;
    }

    public void setDefaultZ(float defaultZ) {
        this.defaultZ = defaultZ;
    }

    public int[] getResolution() {
        return resolution;
    }

    public void setResolution(int[] resolution) {
        this.resolution = resolution;
    }

    public float getFieldOfViewHorizontal(){
        return fieldOfView[0];
    }

    public void setFieldOfViewHorizontal(float fovHorizontal){
        fieldOfView[0] = fovHorizontal;
    }

    public float getFieldOfViewVertical(){
        return fieldOfView[1];
    }

    public void setFieldOfViewVertical(float fovVertical){
        fieldOfView[1] = fovVertical;
    }
}
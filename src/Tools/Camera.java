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

    public int getResolutionWidth(){
        return getResolution()[0];
    }

    public int getResolutionHeight(){
        return getResolution()[1];
    }

    /**
     *Calculates the ray mesh of the camera and return a two-dimensional matrix with the position of each pixel
     * @return Vector3D[][]
     */
    public Vector3D[][] calculatePositionsToRay(){
        //Calculate the size of the ray mesh in x that is the width of the mesh
        float angleMaxInX = 90 - (getFieldOfViewHorizontal() / 2f);
        float radiusMaxInX = getDefaultZ() / (float) Math.cos(Math.toRadians(angleMaxInX));
        float maxInX = (float) Math.sin(Math.toRadians(angleMaxInX)) * radiusMaxInX;
        float minInX = -maxInX;

        //Calculate the size of the ray mesh in y that is the height of the mesh
        float angleMaxInY = 90 - (getFieldOfViewVertical() / 2f);
        float radiusMaxInY = getDefaultZ() / (float) Math.cos(Math.toRadians(angleMaxInY));
        float maxInY = (float) Math.sin(Math.toRadians(angleMaxInY)) * radiusMaxInY;
        float minInY = -maxInY;

        Vector3D[][] positions = new Vector3D[getResolutionWidth()][getResolutionHeight()];

        //calculate the position of each pixel in the mesh
        float posZ = getDefaultZ();
        for(int x = 0; x<positions.length; x++){
            for(int y = 0; y<positions[x].length; y++){
                float posX = minInX + (((maxInX - minInX) / (float) getResolutionWidth()) * x);
                float posY = minInY + (((maxInY - minInY) / (float) getResolutionWidth()) * y);
                positions[x][y] = new Vector3D(posX, posY, posZ);
            }
        }

        return positions;
    }
}
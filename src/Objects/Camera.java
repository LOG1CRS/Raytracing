package Objects;

import java.awt.*;

public class Camera extends Object3D {

    private int width;
    private int height;

    /**
     * Camera constructor
     * @param origin
     * @param width
     * @param height
     */
    public Camera(Vector3D origin, int width, int height) {
        super(origin, Color.black);
        setWidth(width);
        setHeight(height);
    }

    //Getters & Setters

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
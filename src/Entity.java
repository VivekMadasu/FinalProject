import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public abstract class Entity
{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex = 0;


    public Entity(
            String id,
            Point position,
            List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
    }


    public PImage getCurrentImage() {
        return getImages().get(getImageIndex());
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;

    }

    public List<PImage> getImages() {
        return images;
    }

    protected int getImageIndex() {
        return imageIndex;
    }

    protected void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
}

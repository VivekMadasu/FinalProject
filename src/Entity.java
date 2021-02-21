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


    protected PImage getCurrentImage() {
        return getImages().get(getImageIndex());
    }

    protected String getId() {
        return id;
    }

    protected Point getPosition() {
        return position;
    }

    protected void setPosition(Point position) {
        this.position = position;

    }

    protected List<PImage> getImages() {
        return images;
    }

    protected int getImageIndex() {
        return imageIndex;
    }

    protected void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
}

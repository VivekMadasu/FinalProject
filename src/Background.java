import java.util.List;

import processing.core.PImage;

public final class Background
{
    private final String id;
    private final List<PImage> images;
    private final int imageIndex = 0;

    public PImage getCurrentImage() {
            return getImages().get(
                    imageIndex);
    }

    public String getId() {
        return id;
    }

    public List<PImage> getImages() {
        return images;
    }


    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }
}

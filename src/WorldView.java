import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

public final class WorldView
{
    private PApplet screen;
    private WorldModel world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

    public PApplet getScreen() {
        return screen;
    }

    public WorldModel getWorld() {
        return world;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public WorldView(
            int numRows,
            int numCols,
            PApplet screen,
            WorldModel world,
            int tileWidth,
            int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    public void shiftView(int colDelta, int rowDelta) {
        int newCol = Functions.clamp(this.viewport.getCol() + colDelta, 0,
                this.world.numCols - this.viewport.getNumCols());
        int newRow = Functions.clamp(this.viewport.getRow() + rowDelta, 0,
                this.world.numRows - this.viewport.getNumRows());

        this.viewport.shift(newCol, newRow);
    }

    public void drawBackground() {
        for (int row = 0; row < this.viewport.getNumRows(); row++) {
            for (int col = 0; col < this.viewport.getNumCols(); col++) {
                Point worldPoint = this.viewport.viewportToWorld(col, row);
                Optional<PImage> image =
                        Functions.getBackgroundImage(this.world, worldPoint);
                if (image.isPresent()) {
                    this.screen.image(image.get(), col * this.tileWidth,
                            row * this.tileHeight);
                }
            }
        }
    }

    public void drawEntities() {
        for (Entity entity : this.world.entities) {
            Point pos = entity.position;

            if (this.viewport.contains(pos)) {
                Point viewPoint = this.viewport.worldToViewport(pos.getX(), pos.getY());
                this.screen.image(Functions.getCurrentImage(entity),
                        viewPoint.getX() * this.tileWidth,
                        viewPoint.getY() * this.tileHeight);
            }
        }
    }

    public void drawViewport() {
        drawBackground();
        drawEntities();
    }


}

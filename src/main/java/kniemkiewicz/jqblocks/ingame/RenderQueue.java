package kniemkiewicz.jqblocks.ingame;

import kniemkiewicz.jqblocks.ingame.object.RenderableObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * User: krzysiek
 * Date: 08.07.12
 */
@Component
public class RenderQueue implements Renderable {

  @Autowired
  PointOfView pointOfView;

  EnumMap<RenderableObject.Layer, Set<RenderableObject>> renderableObjects = new EnumMap<RenderableObject.Layer, Set<RenderableObject>>(RenderableObject.Layer.class);
  Set<Renderable> renderables = new HashSet<Renderable>();
  
  public static final Color SKY = new Color(26f/255, 100f/255, 191f/255);

  RenderQueue() {
    for (RenderableObject.Layer l : RenderableObject.Layer.values()) {
      renderableObjects.put(l, new HashSet<RenderableObject>());
    }
  }

  public void add(RenderableObject renderable) {
    renderableObjects.get(renderable.getLayer()).add(renderable);
  }

  public void add(Renderable renderable) {
    renderables.add(renderable);
  }

  public void render(Graphics g) {
    g.setBackground(SKY);
    g.setLineWidth(1);
    g.translate(-pointOfView.getShiftX(), -pointOfView.getShiftY());
    Rectangle window = new Rectangle(pointOfView.getShiftX(), pointOfView.getShiftY(), Sizes.WINDOW_WIDTH, Sizes.WINDOW_HEIGHT);
    for (RenderableObject.Layer l : RenderableObject.Layer.values()) {
      for (RenderableObject r : renderableObjects.get(l)) {
        if (window.intersects(r.getShape())) {
          r.renderObject(g, pointOfView);
        }
      }
    }
    g.translate(pointOfView.getShiftX(), pointOfView.getShiftY());
    for (Renderable r : renderables) {
      r.render(g);
    }
  }

  public void remove(RenderableObject block) {
    renderableObjects.get(block.getLayer()).remove(block);
  }
}

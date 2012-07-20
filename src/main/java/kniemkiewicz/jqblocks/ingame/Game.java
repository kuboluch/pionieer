package kniemkiewicz.jqblocks.ingame;

import kniemkiewicz.jqblocks.ingame.controller.EndGameController;
import kniemkiewicz.jqblocks.ingame.controller.InventoryController;
import kniemkiewicz.jqblocks.ingame.controller.PlayerController;
import kniemkiewicz.jqblocks.ingame.level.LevelGenerator;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * User: krzysiek
 * Date: 08.07.12
 */
@Component
public class Game extends BasicGame{
  public Game() {
    super("");
  }

  @Autowired
  PlayerController playerController;

  @Autowired
  EndGameController endGameController;

  @Autowired
  InventoryController inventoryController;

  @Autowired
  RenderQueue renderQueue;

  @Autowired
  ClickCounter clickCounter;

  @Autowired
  LevelGenerator levelGenerator;

  List<InputListener> inputListeners = new ArrayList<InputListener>();

  @Override
  public void init(GameContainer gameContainer) throws SlickException {
    inputListeners.add(playerController);
    inputListeners.add(endGameController);
    inputListeners.add(inventoryController);
    clickCounter.add(inventoryController);
    gameContainer.getInput().addMouseListener(clickCounter);
    levelGenerator.setSeed(1);
    levelGenerator.generate();
    playerController.init();
  }

  @Override
  public void update(GameContainer gameContainer, int delta) throws SlickException {
    clickCounter.update();
    for (InputListener l : inputListeners) {
      l.listen(gameContainer.getInput(), delta);
    }
  }

  public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
    renderQueue.render(graphics);
  }
}

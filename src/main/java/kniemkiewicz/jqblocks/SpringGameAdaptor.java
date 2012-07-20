package kniemkiewicz.jqblocks;

import kniemkiewicz.jqblocks.ingame.controller.EndGameController;
import kniemkiewicz.jqblocks.ingame.Game;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: krzysiek
 * Date: 07.07.12
 * TODO: StateBasedGame
 */
public class SpringGameAdaptor extends BasicGame {

  public static final String gameContextPath = "context/ingame.xml";
  
  BeanFactory gameBeanFactory;
  Game game;
  EndGameController endGameController;
  
  public SpringGameAdaptor() {
    super("JPioneer");
  }

  @Override
  public void init(GameContainer gameContainer) throws SlickException {
    // Some beans cannot be initialized before here.
    initInternal(gameContainer);
  }

  void initInternal(GameContainer gameContainer) throws SlickException {
    gameBeanFactory = new ClassPathXmlApplicationContext(gameContextPath);
    game = gameBeanFactory.getBean(Game.class);
    endGameController = gameBeanFactory.getBean(EndGameController.class);
    game.init(gameContainer);
  }

  @Override
  public void update(GameContainer gameContainer, int delta) throws SlickException {
    if (endGameController.isGameShouldRestart()) {
      initInternal(gameContainer);
    }
    if (endGameController.isGameShouldEnd()) {
      gameContainer.exit();
    }
    game.update(gameContainer, delta);
  }

  public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
    game.render(gameContainer, graphics);
  }
}

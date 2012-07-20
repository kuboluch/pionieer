package kniemkiewicz.jqblocks.ingame.ui;

import kniemkiewicz.jqblocks.ingame.Renderable;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * User: knie
 * Date: 7/20/12
 * Time: 9:40 PM
 */
@Component
public class TimingInfo implements Renderable {
  public static class Timer {
    long startTime = 0;
    long displayTime = 0;
    long lastUpdate = 0;
    long currentSum = 0;
    long currentCount = 0;

    Timer() {
      reset();
    }

    public void reset() {
      startTime = nanoToMicro(System.nanoTime());
    }

    public long nanoToMicro(long nano) {
      if (nano < 0) {
        return nano / 1000 - 1;
      } else {
        return nano / 1000;
      }
    }

    public void record() {
      long current = nanoToMicro(System.nanoTime());
      currentSum += current - startTime;
      currentCount++;
      if (current - lastUpdate > 1000000) {
        displayTime = currentSum / currentCount;
        currentSum = 0;
        currentCount = 0;
        lastUpdate = current;
      }
    }
  }

  Map<String, Timer> timers = new HashMap<String, Timer>();

  public Timer getTimer(String name) {
    Timer t;
    if (!timers.containsKey(name))  {
       t = new Timer();
       timers.put(name, t);
    } else {
      t = timers.get(name);
      t.reset();
    }
    return t;
  }

  @Override
  public void render(Graphics g) {
    int y = 25;
    g.setColor(Color.white);
    for (String name : timers.keySet()) {
      g.drawString(name + " : " + timers.get(name).displayTime, 4, y);
      y += 13;
    }
  }
}

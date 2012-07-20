package kniemkiewicz.jqblocks.ingame.util;

/**
 * User: krzysiek
 * Date: 08.07.12
 */
public class LimitedSpeed {
  float pos;
  float speed;
  float maxSpeed;
  float acceleration = 0;
  float defaultDeceleration;

  public LimitedSpeed(float maxSpeed, float speed, float x, float defaultDeceleration) {
    this.maxSpeed = maxSpeed;
    this.speed = speed;
    this.pos = x;
    this.defaultDeceleration = defaultDeceleration;
  }

  public float getPos() {
    return pos;
  }

  public float getSpeed() {
    return speed;
  }

  public float getAcceleration() {
    return acceleration;
  }

  public void setAcceleration(float acceleration) {
    this.acceleration = acceleration;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public void update(int delta) {
    if (acceleration != 0) {
      speed += acceleration * delta;
      acceleration = 0;
      if (speed > maxSpeed) {
        speed = maxSpeed;
      } else if (speed < -maxSpeed) {
        speed = -maxSpeed;  
      }
    } else {
      if (Math.abs(speed) < defaultDeceleration * delta) {
        speed = 0;
      } else if (speed > 0) {
        speed -= defaultDeceleration * delta;
      } else {
        speed += defaultDeceleration * delta;
      }
    }
    pos += speed * delta;
  }

  public void setPos(float pos) {
    this.pos = pos;
  }

  @Override
  public String toString() {
    return "LimitedSpeed{" +
        "x=" + pos +
        ", v=" + speed +
        ", a=" + acceleration +
        '}';
  }
}

package org.example;

public abstract class Trigger {

  private boolean isActiveLast = false;

  public abstract boolean get();

  public boolean hasActivated() {
    if (get()) {
      if (!isActiveLast) {
        isActiveLast = true;
        return true;
      }
    } else {
      isActiveLast = false;
    }
    return false;
  }
}

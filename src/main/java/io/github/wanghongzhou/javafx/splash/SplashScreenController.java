package io.github.wanghongzhou.javafx.splash;

/**
 * @author Brian
 */
public interface SplashScreenController {

    void runWithoutApplicationContext();

    void runWithApplicationContext(Runnable completedCallback);
}

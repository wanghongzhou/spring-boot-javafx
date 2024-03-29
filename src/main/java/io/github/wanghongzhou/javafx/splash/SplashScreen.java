package io.github.wanghongzhou.javafx.splash;

import io.github.wanghongzhou.javafx.stage.DecoratedStage;
import org.springframework.context.ApplicationContext;

/**
 * @author Brian
 */
public interface SplashScreen {

    void runWithoutApplicationContext();

    void runWithApplicationContext(ApplicationContext applicationContext);

    void registerCompletedCallback(Runnable runnable);

    void settingStage(DecoratedStage stage);
}

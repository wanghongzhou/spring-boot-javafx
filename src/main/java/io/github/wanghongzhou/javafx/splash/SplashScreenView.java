package io.github.wanghongzhou.javafx.splash;

import io.github.wanghongzhou.javafx.stage.DecoratedStage;
import io.github.wanghongzhou.javafx.view.AbstractView;
import org.springframework.context.ApplicationContext;

/**
 * @author Brian
 */
public abstract class SplashScreenView extends AbstractView implements SplashScreen {

    protected Runnable completedCallback;

    @Override
    public void registerCompletedCallback(Runnable runnable) {
        this.completedCallback = runnable;
    }

    @Override
    public void runWithApplicationContext(ApplicationContext applicationContext) {

    }

    @Override
    public void settingStage(DecoratedStage stage) {

    }
}

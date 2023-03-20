package io.github.wanghongzhou.javafx.splash;

import io.github.wanghongzhou.javafx.stage.DecoratedStage;
import io.github.wanghongzhou.javafx.view.fxml.AbstractFxmlView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * @author Brian
 */
@Slf4j
public abstract class SplashScreenFxmlView<T extends SplashScreenController> extends AbstractFxmlView implements SplashScreen {

    protected T controller;
    protected Runnable completedCallback;

    @Override
    @SuppressWarnings("unchecked")
    protected Object controllerFactory(Class<?> clazz) {
        try {
            controller = (T) clazz.getDeclaredConstructor().newInstance();
            log.debug("Create controller {} in {}", controller, this);
        } catch (Exception e) {
            log.error("Create controller fail: ", e);
            throw new RuntimeException(e);
        }
        return this.controller;
    }

    @Override
    public void runWithoutApplicationContext() {
        if (Objects.nonNull(this.controller)) {
            this.controller.runWithoutApplicationContext();
        }
    }

    @Override
    public void runWithApplicationContext(ApplicationContext applicationContext) {
        if (Objects.nonNull(this.controller)) {
            try {
                applicationContext.getAutowireCapableBeanFactory().autowireBean(this.controller);
            } catch (Exception e) {
                log.error("Inject controller fail: ", e);
                throw new RuntimeException(e);
            }
            this.controller.runWithApplicationContext(this.completedCallback);
        }
    }

    @Override
    public void registerCompletedCallback(Runnable runnable) {
        this.completedCallback = runnable;
    }

    @Override
    public void settingStage(DecoratedStage stage) {

    }
}

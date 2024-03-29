package io.github.wanghongzhou.javafx.test.stage.factory;

import io.github.wanghongzhou.javafx.application.AbstractApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Brian
 */
@SpringBootApplication
public class SpringStageFactoryTestApplication extends AbstractApplication {

    public static void main(String[] args) {
        launch(SpringStageFactoryTestApplication.class, MainView.class, args);
    }
}

package io.github.wanghongzhou.javafx.test.splash;

import io.github.wanghongzhou.javafx.application.AbstractApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Brian
 */
@SpringBootApplication
public class SplashScreenViewTestApplication extends AbstractApplication {

    public static void main(String[] args) {
        launch(SplashScreenViewTestApplication.class, MainView.class, SplashScreenTestView.class, args);
    }
}

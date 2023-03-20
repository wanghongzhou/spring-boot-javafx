package io.github.wanghongzhou.javafx.test.splash.fxml;

import io.github.wanghongzhou.javafx.application.AbstractApplication;
import io.github.wanghongzhou.javafx.test.splash.SplashScreenViewTestApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Brian
 */
@SpringBootApplication
public class SplashScreenFxmlViewTestApplication extends AbstractApplication {

    public static void main(String[] args) {
        launch(SplashScreenViewTestApplication.class, MainFxmlView.class, SplashScreenTestFxmlView.class, args);
    }
}

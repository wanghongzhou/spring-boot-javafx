package com.github.wanghongzhou.javafx.test.splash.fxml;

import com.github.wanghongzhou.javafx.application.AbstractApplication;
import com.github.wanghongzhou.javafx.test.splash.SplashScreenViewTestApplication;
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

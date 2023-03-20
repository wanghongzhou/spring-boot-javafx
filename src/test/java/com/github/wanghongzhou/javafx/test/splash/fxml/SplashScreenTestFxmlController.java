package com.github.wanghongzhou.javafx.test.splash.fxml;

import com.github.wanghongzhou.javafx.splash.SplashScreenController;
import com.github.wanghongzhou.javafx.test.splash.SplashScreenTestService;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * @author Brian
 */
@Slf4j
public class SplashScreenTestFxmlController implements SplashScreenController, Initializable {

    @FXML
    private Label message;

    @Resource
    private SplashScreenTestService splashScreenService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("view initialize");
        message.setText("view initialize");
    }

    @Override
    public void runWithoutApplicationContext() {
        log.info("spring be initializing");
        message.setText("spring be initializing");
    }

    @Override
    public void runWithApplicationContext(Runnable completedCallback) {
        log.info("spring Initialization complete");
        message.setText("spring Initialization complete");
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> message.setText(splashScreenService.getMessage()));
                Thread.sleep(1000);
                completedCallback.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

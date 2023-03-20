package io.github.wanghongzhou.javafx.test.splash;

import io.github.wanghongzhou.javafx.splash.SplashScreenView;
import io.github.wanghongzhou.javafx.stage.StageStyle;
import io.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CompletableFuture;


/**
 * @author Brian
 */
@Slf4j
public class SplashScreenTestView extends SplashScreenView {

    private Label message;

    @Override
    public Parent getView() {
        StackPane stackPane = new StackPane();
        message = new Label("view initialize");
        stackPane.getChildren().add(message);
        stackPane.setPrefWidth(400);
        stackPane.setPrefHeight(400);
        return stackPane;
    }

    @Override
    public void runWithApplicationContext(ApplicationContext applicationContext) {
        log.info("spring Initialization complete");
        message.setText("spring Initialization complete");
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> message.setText(applicationContext.getBean(SplashScreenTestService.class).getMessage()));
                Thread.sleep(1000);
                this.completedCallback.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void runWithoutApplicationContext() {
        log.info("spring be initializing");
        message.setText("spring be initializing");
    }

    @Override
    public String getTitle() {
        return "SplashScreen View";
    }

    @Override
    public StageStyle getStageStyle() {
        return DefaultStageStyle.TRANSPARENT;
    }
}

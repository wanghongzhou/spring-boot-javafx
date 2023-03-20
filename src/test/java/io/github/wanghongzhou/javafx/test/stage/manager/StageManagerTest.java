package io.github.wanghongzhou.javafx.test.stage.manager;

import io.github.wanghongzhou.javafx.stage.StageManager;
import io.github.wanghongzhou.javafx.stage.manager.DefaultStageManager;
import io.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import io.github.wanghongzhou.javafx.test.stage.manager.view.MainView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

/**
 * @author Brian
 */
public class StageManagerTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        StageManager manager = new DefaultStageManager();
        manager.setPrimaryStage(primaryStage);
        manager.setDefaultStageStyle(DefaultStageStyle.COMPLEX);
        manager.setDefaultTitle("StageManager");
        manager.setDefaultIcons(List.of(new Image(Objects.requireNonNull(getClass().getResource("/logo/logo_24x24.png")).toExternalForm())));
        manager.setDefaultTheme(Objects.requireNonNull(getClass().getResource("/styles/blue.css")).toExternalForm());
        manager.switchScene(new MainView(manager)).show();
    }

    public static void main(String[] args) {
        Application.launch(StageManagerTest.class, args);
    }
}
package io.github.wanghongzhou.javafx.test.stage.decorated;

import io.github.wanghongzhou.javafx.stage.DecoratedStage;
import io.github.wanghongzhou.javafx.stage.decorated.UnadornedStage;
import io.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Brian
 */
public class UnadornedStageTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        DecoratedStage stage = new UnadornedStage(primaryStage);
        stage.initStyle(DefaultStageStyle.DECORATED);
        stage.setTitle("UnadornedStage Test");
        stage.setWidth(400);
        stage.setHeight(400);
        stage.setContent(new StackPane(new Label("Hello World !!!")));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(UnadornedStageTest.class, args);
    }
}

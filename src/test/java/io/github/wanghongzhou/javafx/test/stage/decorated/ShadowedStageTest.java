package io.github.wanghongzhou.javafx.test.stage.decorated;

import io.github.wanghongzhou.javafx.stage.decorated.ShadowedStage;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Brian
 */
public class ShadowedStageTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        ShadowedStage stage = new ShadowedStage(primaryStage);
        Button button1 = new Button("top = 10.0");
        Button button2 = new Button("right = 10.0");
        Button button3 = new Button("bottom = 10.0");
        Button button4 = new Button("left = 10.0");
        Button button5 = new Button("isIconified = " + stage.isIconified());
        Button button6 = new Button("isMaximized = " + stage.isMaximized());
        Button button7 = new Button("isFullScreen = " + stage.isFullScreen());
        button1.setOnAction(event -> {
            stage.getStageShadow().setTop((stage.getStageShadow().getTop() + 1) % 11);
            button1.setText("top = " + stage.getStageShadow().getTop());
        });
        button2.setOnAction(event -> {
            stage.getStageShadow().setRight((stage.getStageShadow().getRight() + 1) % 11);
            button2.setText("right = " + stage.getStageShadow().getRight());
        });
        button3.setOnAction(event -> {
            stage.getStageShadow().setBottom((stage.getStageShadow().getBottom() + 1) % 11);
            button3.setText("bottom = " + stage.getStageShadow().getBottom());
        });
        button4.setOnAction(event -> {
            stage.getStageShadow().setLeft((stage.getStageShadow().getLeft() + 1) % 11);
            button4.setText("left = " + stage.getStageShadow().getLeft());
        });
        button5.setOnAction(event -> {
            stage.setIconified(!stage.isIconified());
            button5.setText("isIconified = " + stage.isIconified());
        });
        button6.setOnAction(event -> {
            stage.setMaximized(!stage.isMaximized());
            button6.setText("isMaximized = " + stage.isMaximized());
        });
        button7.setOnAction(event -> {
            stage.setFullScreen(!stage.isFullScreen());
            button7.setText("isFullScreen = " + stage.isFullScreen());
        });
        stage.setContent(new StackPane(new Group(new VBox(button1, button2, button3, button4, button5, button6, button7))));
        stage.setTitle("ShadowedStage Test");
        stage.setWidth(400);
        stage.setHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(ShadowedStageTest.class, args);
    }
}

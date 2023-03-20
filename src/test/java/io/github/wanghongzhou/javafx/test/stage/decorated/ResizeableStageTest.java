package io.github.wanghongzhou.javafx.test.stage.decorated;

import io.github.wanghongzhou.javafx.stage.decorated.ResizeableStage;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Brian
 */
public class ResizeableStageTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        ResizeableStage stage = new ResizeableStage(primaryStage);
        Button button1 = new Button("isIconified = " + stage.isIconified());
        Button button2 = new Button("isMaximized = " + stage.isMaximized());
        Button button3 = new Button("isResizable = " + stage.isResizable());
        Button button4 = new Button("isFullScreen = " + stage.isFullScreen());
        button1.setOnAction(event -> {
            stage.setIconified(!stage.isIconified());
            button1.setText("isIconified = " + stage.isIconified());
        });
        button2.setOnAction(event -> {
            stage.setMaximized(!stage.isMaximized());
            button2.setText("isMaximized = " + stage.isMaximized());
        });
        button3.setOnAction(event -> {
            stage.setResizable(!stage.isResizable());
            button3.setText("isResizable = " + stage.isResizable());
        });
        button4.setOnAction(event -> {
            stage.setFullScreen(!stage.isFullScreen());
            button4.setText("isFullScreen = " + stage.isFullScreen());
        });
        stage.setContent(new StackPane(new Group(new VBox(button1, button2, button3, button4))));
        stage.setTitle("ResizeableStage Test");
        stage.setShowBorder(true);
        stage.setWidth(400);
        stage.setHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(ResizeableStageTest.class, args);
    }
}

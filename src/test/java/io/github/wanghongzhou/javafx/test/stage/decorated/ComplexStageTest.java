package io.github.wanghongzhou.javafx.test.stage.decorated;

import io.github.wanghongzhou.javafx.stage.decorated.ComplexStage;
import io.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * @author Brian
 */
public class ComplexStageTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        ComplexStage stage = new ComplexStage(primaryStage);
        Button button = new Button("Change Stage style !!!");
        button.setOnAction(event -> {
            if (stage.getStyle().equals(DefaultStageStyle.ICON)) {
                stage.initStyle(DefaultStageStyle.CLOSE);
            } else if (stage.getStyle().equals(DefaultStageStyle.CLOSE)) {
                stage.initStyle(DefaultStageStyle.MINIMIZE);
            } else if (stage.getStyle().equals(DefaultStageStyle.MINIMIZE)) {
                stage.initStyle(DefaultStageStyle.MAXIMIZE);
            } else if (stage.getStyle().equals(DefaultStageStyle.MAXIMIZE)) {
                stage.initStyle(DefaultStageStyle.FULLSCREEN);
            } else if (stage.getStyle().equals(DefaultStageStyle.FULLSCREEN)) {
                stage.initStyle(DefaultStageStyle.SIMPLEST);
            } else if (stage.getStyle().equals(DefaultStageStyle.SIMPLEST)) {
                stage.initStyle(DefaultStageStyle.SIMPLE);
            } else if (stage.getStyle().equals(DefaultStageStyle.SIMPLE)) {
                stage.initStyle(DefaultStageStyle.NORMAL);
            } else if (stage.getStyle().equals(DefaultStageStyle.NORMAL)) {
                stage.initStyle(DefaultStageStyle.COMPLEX);
            } else if (stage.getStyle().equals(DefaultStageStyle.COMPLEX)) {
                stage.initStyle(DefaultStageStyle.TITLED);
            } else if (stage.getStyle().equals(DefaultStageStyle.TITLED)) {
                stage.initStyle(DefaultStageStyle.ICON);
            }
        });
        stage.initStyle(DefaultStageStyle.COMPLEX);
        stage.setTheme(Objects.requireNonNull(getClass().getResource("/styles/blue.css")).toExternalForm());
        stage.setContent(new StackPane(button));
        stage.setTitle("ComplexStage Test");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(ComplexStageTest.class, args);
    }
}

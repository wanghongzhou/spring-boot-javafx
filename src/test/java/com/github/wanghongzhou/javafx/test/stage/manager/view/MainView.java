package com.github.wanghongzhou.javafx.test.stage.manager.view;


import com.github.wanghongzhou.javafx.stage.StageManager;
import com.github.wanghongzhou.javafx.view.AbstractView;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Brian
 */
public class MainView extends AbstractView {

    private final StageManager stageManager;

    public MainView(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public Parent getView() {
        StackPane pane = new StackPane();
        Button button1 = new Button("Switch View");
        Button button2 = new Button("Popup Window");
        Button button3 = new Button("Popup Modal Window");
        button1.setOnAction(event -> {
            stageManager.switchScene(new SwitchView(stageManager));
        });
        button2.setOnAction(event -> {
            stageManager.showPopWindow(new PopupView());
        });
        button3.setOnAction(event -> {
            stageManager.showModalPopWindow(new PopupView());
        });
        pane.getChildren().addAll(new Group(new VBox(button1, button2, button3)));
        pane.setPrefHeight(400);
        pane.setPrefHeight(400);
        return pane;
    }
}

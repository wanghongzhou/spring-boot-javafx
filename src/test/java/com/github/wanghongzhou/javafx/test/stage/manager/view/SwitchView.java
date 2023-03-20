package com.github.wanghongzhou.javafx.test.stage.manager.view;

import com.github.wanghongzhou.javafx.stage.StageManager;
import com.github.wanghongzhou.javafx.stage.StageStyle;
import com.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import com.github.wanghongzhou.javafx.view.AbstractView;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * @author Brian
 */
public class SwitchView extends AbstractView {

    private final StageManager stageManager;

    public SwitchView(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public Parent getView() {
        StackPane pane = new StackPane();
        Button button = new Button("To Main View");
        button.setOnAction(event -> stageManager.switchScene(new MainView(stageManager)));
        pane.getChildren().add(button);
        pane.setPrefWidth(600);
        pane.setPrefHeight(600);
        return pane;
    }

    @Override
    public String getTitle() {
        return "Switch View";
    }

    @Override
    public String getTheme() {
        return "/theme/default.css";
    }

    @Override
    public StageStyle getStageStyle() {
        return DefaultStageStyle.NORMAL;
    }
}

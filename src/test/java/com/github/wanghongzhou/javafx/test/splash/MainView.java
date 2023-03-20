package com.github.wanghongzhou.javafx.test.splash;


import com.github.wanghongzhou.javafx.view.AbstractView;
import com.github.wanghongzhou.javafx.view.View;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * @author Brian
 */
@View
public class MainView extends AbstractView {

    @Override
    public Parent getView() {
        StackPane pane = new StackPane();
        pane.getChildren().addAll(new Label("Main View"));
        pane.setPrefWidth(400);
        pane.setPrefHeight(400);
        return pane;
    }
}

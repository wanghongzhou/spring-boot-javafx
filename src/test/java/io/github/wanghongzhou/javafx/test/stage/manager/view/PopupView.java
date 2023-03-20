package io.github.wanghongzhou.javafx.test.stage.manager.view;

import io.github.wanghongzhou.javafx.view.AbstractView;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * @author Brian
 */
public class PopupView extends AbstractView {

    @Override
    public Parent getView() {
        StackPane pane = new StackPane();
        pane.getChildren().add(new Label("Popup View"));
        pane.setPrefHeight(400);
        pane.setPrefHeight(400);
        return pane;
    }
}

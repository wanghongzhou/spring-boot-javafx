package com.github.wanghongzhou.javafx.stage;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.util.List;

/**
 * @author Brian
 */
public interface DecoratedStage {

    Scene getScene();

    Stage getStage();

    Window getOwner();

    void initOwner(Window owner);

    StageStyle getStyle();

    void initStyle(StageStyle style);

    Modality getModality();

    void initModality(Modality modality);

    double getX();

    void setX(double value);

    ReadOnlyDoubleProperty xProperty();

    double getY();

    void setY(double value);

    ReadOnlyDoubleProperty yProperty();

    double getWidth();

    void setWidth(double value);

    ReadOnlyDoubleProperty widthProperty();

    double getMinWidth();

    void setMinWidth(double value);

    ReadOnlyDoubleProperty minWidthProperty();

    double getMaxWidth();

    void setMaxWidth(double value);

    ReadOnlyDoubleProperty maxWidthProperty();

    double getHeight();

    void setHeight(double value);

    ReadOnlyDoubleProperty heightProperty();

    double getMinHeight();

    void setMinHeight(double value);

    ReadOnlyDoubleProperty minHeightProperty();

    double getMaxHeight();

    void setMaxHeight(double value);

    ReadOnlyDoubleProperty maxHeightProperty();

    String getTitle();

    void setTitle(String value);

    StringProperty titleProperty();

    void setIcons(List<Image> icons);

    ObservableList<Image> iconsProperty();

    void setTheme(String theme);

    StringProperty themeProperty();

    boolean isResizable();

    void setResizable(boolean value);

    BooleanProperty resizableProperty();

    boolean isIconified();

    void setIconified(boolean value);

    ReadOnlyBooleanProperty iconifiedProperty();

    boolean isMaximized();

    void setMaximized(boolean value);

    ReadOnlyBooleanProperty maximizedProperty();

    boolean isFullScreen();

    void setFullScreen(boolean value);

    ReadOnlyBooleanProperty fullScreenProperty();

    void setContent(Parent parent);

    void setOnShown(EventHandler<WindowEvent> value);

    void setOnShowing(EventHandler<WindowEvent> value);

    void setOnHidden(EventHandler<WindowEvent> value);

    void setOnHiding(EventHandler<WindowEvent> value);

    void setOnCloseRequest(EventHandler<WindowEvent> value);

    void centerOnScreen();

    void setAlwaysOnTop(boolean value);

    void show();

    void showAndWait();

    void hide();

    void close();
}

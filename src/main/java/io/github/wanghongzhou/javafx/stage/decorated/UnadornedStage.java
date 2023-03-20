package io.github.wanghongzhou.javafx.stage.decorated;

import io.github.wanghongzhou.javafx.stage.StageStyle;
import io.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
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
import java.util.Objects;
import java.util.Optional;

/**
 * @author Brian
 */
public class UnadornedStage extends AbstractStage {

    protected Boolean initialized = false;

    public UnadornedStage(Stage stage) {
        super(stage);
    }

    @Override
    public Scene getScene() {
        return this.stage.getScene();
    }

    @Override
    public Window getOwner() {
        return this.stage.getOwner();
    }

    @Override
    public void initOwner(Window owner) {
        this.stage.initOwner(owner);
    }

    @Override
    public StageStyle getStyle() {
        return DefaultStageStyle.valueOf(this.stage.getStyle().name());
    }

    @Override
    public void initStyle(StageStyle style) {
        if (!this.initialized) {
            this.stage.initStyle(javafx.stage.StageStyle.valueOf(style.name()));
        }
    }

    @Override
    public Modality getModality() {
        return this.stage.getModality();
    }

    @Override
    public void initModality(Modality modality) {
        this.stage.initModality(modality);
    }

    @Override
    public double getX() {
        return this.stage.getX();
    }

    @Override
    public void setX(double value) {
        this.stage.setX(value);
    }

    @Override
    public ReadOnlyDoubleProperty xProperty() {
        return this.stage.xProperty();
    }

    @Override
    public double getY() {
        return this.stage.getY();
    }

    @Override
    public void setY(double value) {
        this.stage.setY(value);
    }

    @Override
    public ReadOnlyDoubleProperty yProperty() {
        return this.stage.yProperty();
    }

    @Override
    public double getWidth() {
        return this.stage.getWidth();
    }

    @Override
    public void setWidth(double value) {
        this.stage.setWidth(value);
    }

    @Override
    public ReadOnlyDoubleProperty widthProperty() {
        return this.stage.widthProperty();
    }

    @Override
    public double getMinWidth() {
        return this.stage.getMinWidth();
    }

    @Override
    public void setMinWidth(double value) {
        this.stage.setMinWidth(value);
    }

    @Override
    public ReadOnlyDoubleProperty minWidthProperty() {
        return this.stage.minWidthProperty();
    }

    @Override
    public double getMaxWidth() {
        return this.stage.getMaxWidth();
    }

    @Override
    public void setMaxWidth(double value) {
        this.stage.setMaxWidth(value);
    }

    @Override
    public ReadOnlyDoubleProperty maxWidthProperty() {
        return this.stage.maxWidthProperty();
    }

    @Override
    public double getHeight() {
        return this.stage.getHeight();
    }

    @Override
    public void setHeight(double value) {
        this.stage.setHeight(value);
    }

    @Override
    public ReadOnlyDoubleProperty heightProperty() {
        return this.stage.heightProperty();
    }

    @Override
    public double getMinHeight() {
        return this.stage.getMinHeight();
    }

    @Override
    public void setMinHeight(double value) {
        this.stage.setMinHeight(value);
    }

    @Override
    public ReadOnlyDoubleProperty minHeightProperty() {
        return this.stage.minHeightProperty();
    }

    @Override
    public double getMaxHeight() {
        return this.stage.getMaxHeight();
    }

    @Override
    public void setMaxHeight(double value) {
        this.stage.setMaxHeight(value);
    }

    @Override
    public ReadOnlyDoubleProperty maxHeightProperty() {
        return this.stage.maxHeightProperty();
    }

    @Override
    public String getTitle() {
        return this.stage.getTitle();
    }

    @Override
    public void setTitle(String value) {
        this.stage.setTitle(value);
    }

    @Override
    public StringProperty titleProperty() {
        return this.stage.titleProperty();
    }

    @Override
    public void setIcons(List<Image> icons) {
        this.stage.getIcons().setAll(icons);
    }

    @Override
    public ObservableList<Image> iconsProperty() {
        return this.stage.getIcons();
    }

    @Override
    public void setTheme(String theme) {
    }

    @Override
    public StringProperty themeProperty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isResizable() {
        return this.stage.isResizable();
    }

    @Override
    public void setResizable(boolean value) {
        this.stage.setResizable(value);
    }

    @Override
    public BooleanProperty resizableProperty() {
        return this.stage.resizableProperty();
    }

    @Override
    public boolean isIconified() {
        return this.stage.isIconified();
    }

    @Override
    public void setIconified(boolean value) {
        this.stage.setIconified(value);
    }

    @Override
    public ReadOnlyBooleanProperty iconifiedProperty() {
        return this.stage.iconifiedProperty();
    }

    @Override
    public boolean isMaximized() {
        return this.stage.isMaximized();
    }

    @Override
    public void setMaximized(boolean value) {
        this.stage.setMaximized(value);
    }

    @Override
    public ReadOnlyBooleanProperty maximizedProperty() {
        return this.stage.maximizedProperty();
    }

    @Override
    public boolean isFullScreen() {
        return this.stage.isFullScreen();
    }

    @Override
    public void setFullScreen(boolean value) {
        this.stage.setFullScreen(value);
    }

    @Override
    public ReadOnlyBooleanProperty fullScreenProperty() {
        return this.stage.fullScreenProperty();
    }

    @Override
    public void setContent(Parent parent) {
        if (Objects.isNull(this.stage.getScene())) {
            this.stage.setScene(Optional.ofNullable(parent.getScene()).orElseGet(() -> new Scene(parent)));
        } else {
            this.stage.setScene(Optional.ofNullable(parent.getScene()).orElseGet(() -> new Scene(parent)));
            if (!(this.isMaximized() || this.isFullScreen())) {
                this.stage.sizeToScene();
            }
        }
    }

    @Override
    public void setOnShown(EventHandler<WindowEvent> value) {
        this.stage.setOnShown(value);
    }

    @Override
    public void setOnShowing(EventHandler<WindowEvent> value) {
        this.stage.setOnShowing(value);
    }

    @Override
    public void setOnHidden(EventHandler<WindowEvent> value) {
        this.stage.setOnHidden(value);
    }

    @Override
    public void setOnHiding(EventHandler<WindowEvent> value) {
        this.stage.setOnHiding(value);
    }

    @Override
    public void setOnCloseRequest(EventHandler<WindowEvent> value) {
        this.stage.setOnCloseRequest(value);
    }

    @Override
    public void centerOnScreen() {
        this.stage.centerOnScreen();
    }

    @Override
    public void setAlwaysOnTop(boolean value) {
        this.stage.setAlwaysOnTop(value);
    }

    @Override
    public void show() {
        this.initialized = true;
        this.stage.show();
    }

    @Override
    public void showAndWait() {
        this.initialized = true;
        this.stage.showAndWait();
    }

    @Override
    public void hide() {
        this.stage.hide();
    }

    @Override
    public void close() {
        this.stage.close();
    }
}

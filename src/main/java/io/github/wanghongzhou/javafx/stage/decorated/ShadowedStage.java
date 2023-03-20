package io.github.wanghongzhou.javafx.stage.decorated;

import io.github.wanghongzhou.javafx.stage.StageStyle;
import io.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * <pre>
 *
 * root (StackPane)
 * -- body (AnchorPane)
 *
 * </pre>
 *
 * @author Brian
 */
public class ShadowedStage extends UnadornedStage {

    protected StageShadow stageShadow = new StageShadow();

    protected final StackPane root = new StackPane();
    protected final AnchorPane body = new AnchorPane();

    protected final Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    protected final ObjectProperty<StageStyle> stageStyle = new SimpleObjectProperty<>();
    protected final StringProperty theme = new SimpleStringProperty(Objects.requireNonNull(ShadowedStage.class.getResource("/theme/default.css")).toExternalForm());

    public ShadowedStage(Stage stage) {
        super(stage);
        this.initStyle(DefaultStageStyle.SHADOWED);
    }

    @Override
    public StageStyle getStyle() {
        return this.stageStyle.get();
    }

    @Override
    public void initStyle(StageStyle style) {
        this.stageStyle.set(style);
    }

    protected void init() {
        if (!this.initialized) {
            this.initStage();
            this.bindEvents();
            this.initialized = true;
            Platform.runLater(this::initExecuteEvents);
        }
    }

    protected void initStage() {
        this.stage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
        this.stage.setScene(new Scene(this.initRoot(), Color.TRANSPARENT));
    }

    protected StackPane initRoot() {
        this.root.getStyleClass().setAll("root");
        this.root.getChildren().add(this.initBody());
        this.root.setStyle(this.stageShadow.getPadding());
        return this.root;
    }

    protected AnchorPane initBody() {
        this.body.getStyleClass().setAll("body");
        return this.body;
    }

    protected void bindEvents() {
        this.theme.addListener(this::stageThemeChanged);
        this.stageStyle.addListener(this::stageStyleChanged);
        this.stageShadow.addListener(this::stageShadowValueChanged);
        this.stageThemeChanged(this.theme, this.theme.get(), this.theme.get());
        this.stageStyleChanged(this.stageStyle, this.stageStyle.get(), this.stageStyle.get());
        this.iconifiedProperty().addListener(this::iconifiedHandler);
        this.maximizedProperty().addListener(this::maximizedHandler);
        this.fullScreenProperty().addListener(this::fullScreenHandler);
    }

    protected void initExecuteEvents() {
        this.iconifiedHandler(this.iconifiedProperty(), this.isIconified(), this.isIconified());
        this.maximizedHandler(this.maximizedProperty(), this.isMaximized(), this.isMaximized());
        this.fullScreenHandler(this.fullScreenProperty(), this.isFullScreen(), this.isFullScreen());
    }

    protected void stageThemeChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        this.root.getStylesheets().setAll(theme.get());
    }

    protected void stageStyleChanged(ObservableValue<? extends StageStyle> observable, StageStyle oldValue, StageStyle newValue) {

    }

    protected void stageShadowValueChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        this.root.setStyle(this.stageShadow.getPadding());
    }

    protected void iconifiedHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (oldValue != newValue) {
            if (newValue) {
                this.stageShadow.setDisable(true);
            } else {
                if (this.isMaximized()) {
                    this.maximizedHandler(this.maximizedProperty(), this.isMaximized(), this.isMaximized());
                } else {
                    this.stageShadow.setDisable(false);
                }
            }
        } else {
            this.stageShadow.setDisable(this.isIconified() || this.isMaximized() || this.isFullScreen());
        }
    }

    protected void maximizedHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (oldValue != newValue) {
            this.stageShadow.setDisable(newValue);
        } else {
            this.stageShadow.setDisable(this.isIconified() || this.isMaximized() || this.isFullScreen());
        }
        if (newValue) {
            this.setX(visualBounds.getMinX());
            this.setY(visualBounds.getMinY());
            this.setWidth(visualBounds.getWidth());
            this.setHeight(visualBounds.getHeight());
        }
    }

    protected void fullScreenHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (oldValue != newValue) {
            if (newValue) {
                this.stageShadow.setDisable(true);
            } else {
                if (this.isMaximized()) {
                    this.maximizedHandler(this.maximizedProperty(), this.isMaximized(), this.isMaximized());
                } else {
                    this.stageShadow.setDisable(false);
                }
            }
        } else {
            this.stageShadow.setDisable(this.isIconified() || this.isMaximized() || this.isFullScreen());
        }
    }

    protected void updateStageShadow() {
        this.stageShadow.setTop(this.getY() + this.stageShadow.getTop() - visualBounds.getMinY());
        this.stageShadow.setLeft(this.getX() + this.stageShadow.getLeft() - visualBounds.getMinX());
        this.stageShadow.setRight(visualBounds.getWidth() - (this.getX() + this.getWidth() - this.stageShadow.getRight()));
        this.stageShadow.setBottom(visualBounds.getHeight() - (this.getY() + this.getHeight() - this.stageShadow.getBottom()));
    }

    public void setStageShadow(StageShadow stageShadow) {
        this.stageShadow = stageShadow;
        this.stageShadow.addListener(this::stageShadowValueChanged);
    }

    public StageShadow getStageShadow() {
        return this.stageShadow;
    }

    @Override
    public void setTheme(String theme) {
        this.theme.set(theme);
    }

    @Override
    public StringProperty themeProperty() {
        return this.theme;
    }

    @Override
    public void setContent(Parent parent) {
        if (this.body.getChildren().isEmpty()) {
            this.body.getChildren().add(parent);
        } else {
            this.body.getChildren().clear();
            this.body.getChildren().add(parent);
            if (!(this.isMaximized() || this.isFullScreen())) {
                this.stage.sizeToScene();
            }
        }
        AnchorPane.setTopAnchor(parent, 0.0);
        AnchorPane.setLeftAnchor(parent, 0.0);
        AnchorPane.setRightAnchor(parent, 0.0);
        AnchorPane.setBottomAnchor(parent, 0.0);
    }

    public static class StageShadow {

        private final double top;
        private final double right;
        private final double bottom;
        private final double left;
        private final SimpleDoubleProperty topProperty;
        private final SimpleDoubleProperty rightProperty;
        private final SimpleDoubleProperty bottomProperty;
        private final SimpleDoubleProperty leftProperty;

        public StageShadow() {
            this(10, 10, 10, 10);
        }

        public StageShadow(double top, double right, double bottom, double left) {
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.left = left;
            this.topProperty = new SimpleDoubleProperty(top);
            this.rightProperty = new SimpleDoubleProperty(right);
            this.bottomProperty = new SimpleDoubleProperty(bottom);
            this.leftProperty = new SimpleDoubleProperty(left);
        }

        public void setDisable(boolean disable) {
            if (disable) {
                this.setTop(0);
                this.setRight(0);
                this.setBottom(0);
                this.setLeft(0);
            } else {
                this.setTop(this.top);
                this.setRight(this.right);
                this.setBottom(this.bottom);
                this.setLeft(this.left);
            }
        }

        public void setDisableY(boolean disable) {
            if (disable) {
                this.setTop(0);
                this.setBottom(0);
            } else {
                this.setTop(this.top);
                this.setBottom(this.bottom);
            }
        }

        public void disableLeftTop() {
            this.setTop(0);
            this.setLeft(0);
        }

        public void disableLeftBottom() {
            this.setLeft(0);
            this.setBottom(0);
        }

        public void disableLeftTopBottom() {
            this.setTop(0);
            this.setLeft(0);
            this.setBottom(0);
        }

        public void disableRightTop() {
            this.setTop(0);
            this.setRight(0);
        }

        public void disableRightBottom() {
            this.setRight(0);
            this.setBottom(0);
        }

        public void disableRightTopBottom() {
            this.setTop(0);
            this.setRight(0);
            this.setBottom(0);
        }

        public double getTop() {
            return this.topProperty.get();
        }

        public void setTop(double top) {
            this.topProperty.set(Math.max(0, Math.min(this.top, top)));
        }

        public double getRight() {
            return this.rightProperty.get();
        }

        public void setRight(double right) {
            this.rightProperty.set(Math.max(0, Math.min(this.right, right)));
        }

        public double getBottom() {
            return this.bottomProperty.get();
        }

        public void setBottom(double bottom) {
            this.bottomProperty.set(Math.max(0, Math.min(this.bottom, bottom)));
        }

        public double getLeft() {
            return this.leftProperty.get();
        }

        public void setLeft(double left) {
            this.leftProperty.set(Math.max(0, Math.min(this.left, left)));
        }

        public SimpleDoubleProperty topProperty() {
            return this.topProperty;
        }

        public SimpleDoubleProperty rightProperty() {
            return this.rightProperty;
        }

        public SimpleDoubleProperty bottomProperty() {
            return this.bottomProperty;
        }

        public SimpleDoubleProperty leftProperty() {
            return this.leftProperty;
        }

        public void addListener(ChangeListener<? super Number> listener) {
            this.topProperty.addListener(listener);
            this.rightProperty.addListener(listener);
            this.bottomProperty.addListener(listener);
            this.leftProperty.addListener(listener);
        }

        public void removeListener(ChangeListener<? super Number> listener) {
            this.topProperty.removeListener(listener);
            this.rightProperty.removeListener(listener);
            this.bottomProperty.removeListener(listener);
            this.leftProperty.removeListener(listener);
        }

        public String getPadding() {
            return "-fx-padding: " + topProperty.get() + " " + rightProperty.get() + " " + bottomProperty.get() + " " + leftProperty.get() + "; -fx-background-color: transparent;";
        }
    }

    @Override
    public void show() {
        this.init();
        super.show();
    }

    @Override
    public void showAndWait() {
        this.init();
        super.showAndWait();
    }
}


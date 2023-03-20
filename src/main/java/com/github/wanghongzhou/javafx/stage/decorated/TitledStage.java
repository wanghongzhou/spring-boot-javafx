package com.github.wanghongzhou.javafx.stage.decorated;

import com.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

/**
 * <pre>
 * root (StackPane)
 * -- body (AnchorPane)
 *    -- bar (HBox)
 *       -- barTitle (HBox)
 *    -- container (ScrollPane)
 * </pre>
 *
 * @author Brian
 */
public class TitledStage extends ResizeableStage {

    protected double initX = -1.0;
    protected double initY = -1.0;

    protected final HBox bar = new HBox();
    protected final HBox barTitle = new HBox();
    protected final ScrollPane container = new ScrollPane();
    protected final Label titleLabel = new Label("JavaFx Application");
    protected final DoubleProperty barHeight = new SimpleDoubleProperty(30.0);

    protected final TranslateTransition openTransition = new TranslateTransition(Duration.millis(100.0), this.bar);
    protected final TranslateTransition closeTransition = new TranslateTransition(Duration.millis(100.0), this.bar);

    public TitledStage(Stage stage) {
        super(stage);
        this.initStyle(DefaultStageStyle.TITLED);
    }

    @Override
    protected AnchorPane initBody() {
        this.body.getChildren().add(this.initBar());
        this.body.getChildren().add(this.initContainer());
        return super.initBody();
    }

    protected HBox initBar() {
        this.bar.setAlignment(Pos.CENTER);
        this.bar.getStyleClass().add("bar");
        this.bar.minHeightProperty().bind(this.barHeight);
        this.bar.prefHeightProperty().bind(this.barHeight);
        this.bar.getChildren().add(this.initBarTitle());
        AnchorPane.setTopAnchor(this.bar, 0.0);
        AnchorPane.setLeftAnchor(this.bar, 0.0);
        AnchorPane.setRightAnchor(this.bar, 0.0);
        return this.bar;
    }

    protected HBox initBarTitle() {
        this.titleLabel.setAlignment(Pos.CENTER);
        this.titleLabel.getStyleClass().add("title");
        this.titleLabel.textProperty().bind(this.stage.titleProperty());
        this.barTitle.setAlignment(Pos.CENTER);
        this.barTitle.getStyleClass().add("barTitle");
        this.barTitle.minHeightProperty().bind(this.barHeight.subtract(1));
        this.barTitle.prefHeightProperty().bind(this.barHeight.subtract(1));
        this.barTitle.getChildren().add(this.titleLabel);
        HBox.setHgrow(this.barTitle, Priority.ALWAYS);
        return this.barTitle;
    }

    protected ScrollPane initContainer() {
        this.container.setFitToWidth(true);
        this.container.setFitToHeight(true);
        this.container.getStyleClass().add("container");
        AnchorPane.setTopAnchor(this.container, this.barHeight.get());
        AnchorPane.setLeftAnchor(this.container, 0.0);
        AnchorPane.setRightAnchor(this.container, 0.0);
        AnchorPane.setBottomAnchor(this.container, 0.0);
        return this.container;
    }

    @Override
    protected void fullScreenHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        super.fullScreenHandler(observable, oldValue, newValue);
        if (newValue) {
            this.bar.setOnMouseClicked(null);
            this.bar.setOnMousePressed(null);
            this.bar.setOnMouseDragged(null);
            this.bar.setOnMouseReleased(null);
            this.bar.setOnMouseExited(this::barMouseExitedHandler);
            this.bar.setOnMouseEntered(this::barMouseEnteredHandler);
            this.root.setOnMouseMoved(this::rootMouseMovedHandler);
        } else {
            this.bar.setOnMouseClicked(this::barMouseClickedHandler);
            this.bar.setOnMousePressed(this::barMousePressedHandler);
            this.bar.setOnMouseDragged(this::barMouseDraggedHandler);
            this.bar.setOnMouseReleased(this::barMouseReleasedHandler);
            this.bar.setOnMouseExited(null);
            this.bar.setOnMouseEntered(null);
            this.root.setOnMouseMoved(null);
        }
        if (oldValue != newValue) {
            this.showBarInFullScreen(!newValue);
        } else {
            if (newValue) {
                this.showBarInFullScreen(false);
            }
        }
    }

    protected void showBarInFullScreen(boolean show) {
        if (show) {
            this.openTransition.setFromY(-this.barHeight.get());
            this.openTransition.setByY(this.bar.getMinHeight());
            this.openTransition.play();
            AnchorPane.setTopAnchor(this.container, this.barHeight.get());
        } else {
            AnchorPane.setTopAnchor(this.container, 0.0);
            this.closeTransition.setFromY(this.barHeight.get() * -1.0);
            this.closeTransition.setByY(-this.barHeight.get());
            this.closeTransition.play();
        }
    }

    protected void barMouseClickedHandler(MouseEvent event) {
        if (event.getClickCount() == 2 && this.isResizable()) {
            this.setMaximized(!this.isMaximized());
            event.consume();
        }
    }

    protected void barMouseEnteredHandler(MouseEvent event) {
        if (this.isFullScreen()) {
            this.root.setOnMouseMoved(null);
            event.consume();
        }
    }

    protected void barMouseExitedHandler(MouseEvent event) {
        if (this.isFullScreen() && event.getY() > 0.0) {
            this.showBarInFullScreen(false);
            this.root.setOnMouseMoved(this::rootMouseMovedHandler);
            event.consume();
        }
    }

    protected void rootMouseMovedHandler(MouseEvent event) {
        if (this.stage.isFullScreen() && event.getY() == 0.0) {
            this.showBarInFullScreen(true);
            this.root.setOnMouseMoved(null);
            event.consume();
        }
    }

    protected void barMousePressedHandler(MouseEvent event) {
        if (event.isPrimaryButtonDown() && (!this.isMaximized() || this.isResizable())) {
            if (this.isMaximized()) {
                initX = (savedBounds.width - savedBounds.left - savedBounds.right) * (event.getSceneX() / this.getWidth()) + savedBounds.left;
                initY = (savedBounds.height - savedBounds.top - savedBounds.bottom) * (event.getSceneY() / this.getHeight()) + savedBounds.top;
            } else {
                initX = event.getSceneX();
                initY = event.getSceneY();
                this.updateSaveBounds();
            }
            event.consume();
        }
        this.lockCursor();
    }

    protected void barMouseDraggedHandler(MouseEvent event) {
        if (event.isPrimaryButtonDown() && !event.isStillSincePress() && (!this.isMaximized() || this.isResizable())) {
            this.stage.setX(event.getScreenX() - initX);
            this.stage.setY(event.getScreenY() - initY);
            if (this.isMaximized()) {
                if (this.isResizable()) {
                    this.setMaximized(false);
                    this.stage.setWidth(savedBounds.width);
                    this.stage.setHeight(savedBounds.height);
                }
            } else if (this.isResizable()) {
                if (event.getScreenX() == 0) {
                    if (event.getScreenY() < 30) {
                        this.showPreview(1, 0, 0, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2);
                    } else if (event.getScreenY() > (visualBounds.getHeight() - 30)) {
                        this.showPreview(2, 0, visualBounds.getHeight() / 2, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2);
                    } else {
                        this.showPreview(3, 0, 0, visualBounds.getWidth() / 2, visualBounds.getHeight());
                    }
                } else if (event.getScreenX() == visualBounds.getMaxX() - 1) {
                    if (event.getScreenY() < 30) {
                        this.showPreview(4, visualBounds.getWidth() / 2, 0, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2);
                    } else if (event.getScreenY() > (visualBounds.getHeight() - 30)) {
                        this.showPreview(5, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2);
                    } else {
                        this.showPreview(6, visualBounds.getWidth() / 2, 0, visualBounds.getWidth() / 2, visualBounds.getHeight());
                    }
                } else if (event.getScreenY() == 0) {
                    if (event.getScreenX() < 30) {
                        this.showPreview(1, 0, 0, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2);
                    } else if (event.getScreenX() > (visualBounds.getMaxX() - 30)) {
                        this.showPreview(4, visualBounds.getWidth() / 2, 0, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2);
                    } else {
                        this.showPreview(7, 0, 0, visualBounds.getWidth(), visualBounds.getHeight());
                    }
                } else {
                    this.closePreview();
                }
                this.updateStageShadow();
            }
            this.dragging = true;
            event.consume();
        }
    }

    protected void barMouseReleasedHandler(MouseEvent event) {
        if (this.dragging) {
            this.dragging = false;
            if (this.previewing > 0) {
                switch (this.previewing) {
                    case 1 -> this.stageShadow.disableLeftTop(); // left top
                    case 2 -> this.stageShadow.disableLeftBottom(); // left bottom
                    case 3 -> this.stageShadow.disableLeftTopBottom(); // left
                    case 4 -> this.stageShadow.disableRightTop();  //  right top
                    case 5 -> this.stageShadow.disableRightBottom(); // right bottom
                    case 6 -> this.stageShadow.disableRightTopBottom(); // right
                }
                if (this.previewing == 7) {
                    this.setMaximized(true);
                } else {
                    this.setX(this.previewStage.getX());
                    this.setY(this.previewStage.getY());
                    this.setWidth(this.previewStage.getWidth());
                    this.setHeight(this.previewStage.getHeight());
                }
                this.closePreview();
            }
            event.consume();
        }
        this.unlockCursor();
    }

    public DoubleProperty barHeightProperty() {
        return this.barHeight;
    }

    public ObjectProperty<Pos> barTitleAlignmentProperty() {
        return this.barTitle.alignmentProperty();
    }

    @Override
    public void setContent(Parent parent) {
        if (Objects.isNull(this.container.getContent())) {
            this.container.setContent(parent);
        } else {
            this.container.setContent(parent);
            if (!(this.isMaximized() || this.isFullScreen())) {
                this.stage.sizeToScene();
            }
        }
    }
}

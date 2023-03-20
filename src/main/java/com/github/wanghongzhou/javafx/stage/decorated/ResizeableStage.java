package com.github.wanghongzhou.javafx.stage.decorated;

import com.github.wanghongzhou.javafx.stage.DecoratedStage;
import com.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import com.github.wanghongzhou.javafx.tools.User32;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.WinDef;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.stream.Stream;

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
public class ResizeableStage extends ShadowedStage {

    protected int previewing;
    protected boolean dragging;
    protected boolean showBorder = false;

    protected final Pane top = new Pane();
    protected final Pane left = new Pane();
    protected final Pane right = new Pane();
    protected final Pane bottom = new Pane();
    protected final Path topLeft = new Path();
    protected final Path topRight = new Path();
    protected final Path bottomLeft = new Path();
    protected final Path bottomRight = new Path();

    protected final Rectangle savedBounds = new Rectangle();
    protected final ShadowedStage previewStage = new ShadowedStage(new Stage());
    protected final StageAnimation previewAnimation = new StageAnimation(previewStage);

    protected static final double border = 3.0;
    protected static final double corner = 20.0;

    public ResizeableStage(Stage stage) {
        super(stage);
        this.initStyle(DefaultStageStyle.RESIZEABLE);
    }

    @Override
    protected void initStage() {
        super.initStage();
        this.previewStage.initOwner(this.stage);
        this.previewStage.initStyle(DefaultStageStyle.TRANSPARENT);
        this.previewStage.setStageShadow(new StageShadow());
        this.previewStage.init();
        this.previewStage.getScene().getRoot().getStyleClass().setAll("preview");
    }

    @Override
    protected AnchorPane initBody() {
        this.body.getChildren().add(this.initTop());
        this.body.getChildren().add(this.initLeft());
        this.body.getChildren().add(this.initRight());
        this.body.getChildren().add(this.initBottom());
        this.body.getChildren().add(this.initTopLeft());
        this.body.getChildren().add(this.initTopRight());
        this.body.getChildren().add(this.initBottomLeft());
        this.body.getChildren().add(this.initBottomRight());
        return super.initBody();
    }

    protected Pane initTop() {
        this.top.setId("top");
        this.top.setMinHeight(border);
        this.setBorderPanel(this.top);
        AnchorPane.setTopAnchor(this.top, 0.0);
        AnchorPane.setRightAnchor(this.top, corner);
        AnchorPane.setLeftAnchor(this.top, corner);
        return this.top;
    }

    protected Pane initLeft() {
        this.left.setId("left");
        this.left.setMinWidth(border);
        this.setBorderPanel(this.left);
        AnchorPane.setTopAnchor(this.left, corner);
        AnchorPane.setLeftAnchor(this.left, 0.0);
        AnchorPane.setBottomAnchor(this.left, corner);
        return this.left;
    }

    protected Pane initRight() {
        this.right.setId("right");
        this.right.setMinWidth(border);
        this.setBorderPanel(this.right);
        AnchorPane.setTopAnchor(this.right, corner);
        AnchorPane.setRightAnchor(this.right, 0.0);
        AnchorPane.setBottomAnchor(this.right, corner);
        return this.right;
    }

    protected Pane initBottom() {
        this.bottom.setId("bottom");
        this.bottom.setMinHeight(border);
        this.setBorderPanel(this.bottom);
        AnchorPane.setBottomAnchor(this.bottom, 0.0);
        AnchorPane.setRightAnchor(this.bottom, corner);
        AnchorPane.setLeftAnchor(this.bottom, corner);
        return this.bottom;
    }

    protected void setBorderPanel(Pane pane) {
        pane.setOpacity(showBorder ? 1 : 0);
        pane.setStyle("-fx-background-color : red");
    }

    protected Path initTopLeft() {
        this.topLeft.setId("top_left");
        this.setBorderPath(this.topLeft);
        AnchorPane.setTopAnchor(this.topLeft, 0.0);
        AnchorPane.setLeftAnchor(this.topLeft, 0.0);
        return this.topLeft;
    }

    protected Path initTopRight() {
        this.topRight.setId("top_right");
        this.topRight.setRotate(90.0);
        this.setBorderPath(this.topRight);
        AnchorPane.setTopAnchor(this.topRight, 0.0);
        AnchorPane.setRightAnchor(this.topRight, 0.0);
        return this.topRight;
    }

    protected Path initBottomRight() {
        this.bottomRight.setId("bottom_right");
        this.bottomRight.setRotate(180.0);
        this.setBorderPath(this.bottomRight);
        AnchorPane.setRightAnchor(this.bottomRight, 0.0);
        AnchorPane.setBottomAnchor(this.bottomRight, 0.0);
        return this.bottomRight;
    }

    protected Path initBottomLeft() {
        this.bottomLeft.setId("bottom_left");
        this.bottomLeft.setRotate(270.0);
        this.setBorderPath(this.bottomLeft);
        AnchorPane.setLeftAnchor(this.bottomLeft, 0.0);
        AnchorPane.setBottomAnchor(this.bottomLeft, 0.0);
        return this.bottomLeft;
    }

    protected void setBorderPath(Path path) {
        path.setFill(Color.BLACK);
        path.setStroke(Color.BLACK);
        path.setOpacity(showBorder ? 1 : 0);
        path.setStrokeType(StrokeType.INSIDE);
        path.getElements().addAll(new MoveTo(0.0, 0.0), new LineTo(corner, 0.0), new LineTo(corner, border), new LineTo(border, border), new LineTo(border, corner), new LineTo(0, corner), new ClosePath());
    }

    @Override
    protected void bindEvents() {
        super.bindEvents();
        this.resizableProperty().addListener(this::resizableHandler);
    }

    protected void resizableHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        Stream<Node> nodes = Stream.of(top, left, right, bottom, topLeft, topRight, bottomLeft, bottomRight);
        if (newValue) {
            this.top.cursorProperty().set(Cursor.N_RESIZE);
            this.left.cursorProperty().set(Cursor.W_RESIZE);
            this.right.cursorProperty().set(Cursor.E_RESIZE);
            this.bottom.cursorProperty().set(Cursor.S_RESIZE);
            this.topLeft.cursorProperty().set(Cursor.NW_RESIZE);
            this.topRight.cursorProperty().set(Cursor.NE_RESIZE);
            this.bottomLeft.cursorProperty().set(Cursor.SW_RESIZE);
            this.bottomRight.cursorProperty().set(Cursor.SE_RESIZE);
            this.top.setOnMouseClicked(this::topBorderClickHandler);
            nodes.forEach(node -> {
                node.setOnMousePressed(this::resizeMousePressedHandler);
                node.setOnMouseDragged(this::resizeMouseDraggedHandler);
                node.setOnMouseReleased(this::resizeMouseReleasedHandler);
            });
        } else {
            nodes.forEach(node -> {
                node.setOnMousePressed(null);
                node.setOnMouseDragged(null);
                node.setOnMouseReleased(null);
                node.cursorProperty().set(Cursor.DEFAULT);
            });
        }
    }

    @Override
    protected void iconifiedHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        super.iconifiedHandler(observable, oldValue, newValue);
        if (this.isResizable()) {
            this.resizableHandler(this.resizableProperty(), !newValue, !newValue);
        }
    }

    @Override
    protected void maximizedHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        super.maximizedHandler(observable, oldValue, newValue);
        if (this.isResizable()) {
            this.resizableHandler(this.resizableProperty(), !newValue, !newValue);
        }
    }

    @Override
    protected void fullScreenHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        super.fullScreenHandler(observable, oldValue, newValue);
        if (this.isResizable()) {
            this.resizableHandler(this.resizableProperty(), !newValue, !newValue);
        }
    }

    protected void topBorderClickHandler(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (this.getHeight() == visualBounds.getHeight()) {
                this.stageShadow.setDisableY(false);
                this.setY(this.savedBounds.y);
                this.setHeight(this.savedBounds.height);
            } else {
                this.updateSaveBounds();
                this.stageShadow.setDisableY(true);
                this.setY(visualBounds.getMinY());
                this.setHeight(visualBounds.getHeight());
            }
            event.consume();
        }
    }

    protected void resizeMouseReleasedHandler(MouseEvent event) {
        if (this.dragging) {
            this.dragging = false;
            this.closePreview();
            if (event.getScreenY() == visualBounds.getMinY() || event.getScreenY() >= visualBounds.getMaxY() - 1) {
                this.stageShadow.setDisableY(true);
                this.setY(visualBounds.getMinY());
                this.setHeight(visualBounds.getHeight());
            } else {
                double minWidth = this.body.getWidth() + this.stageShadow.getLeft() + this.stageShadow.getRight();
                if (this.getWidth() < minWidth) {
                    this.setWidth(minWidth);
                }
                double minHeight = this.body.getHeight() + this.stageShadow.getTop() + this.stageShadow.getBottom();
                if (this.getHeight() < minHeight) {
                    this.setHeight(minHeight);
                }
            }
            event.consume();
        }
        this.unlockCursor();
    }

    protected void resizeMousePressedHandler(MouseEvent event) {
        this.lockCursor();
    }

    protected void resizeMouseDraggedHandler(MouseEvent event) {
        if (event.isPrimaryButtonDown() && !event.isStillSincePress()) {
            Cursor cursor = ((Node) event.getSource()).getCursor();
            if (!this.dragging) {
                if (Cursor.N_RESIZE.equals(cursor)) {
                    if (stage.getY() == visualBounds.getMinY()) {
                        this.setHeight(savedBounds.height + savedBounds.y - visualBounds.getMinY());
                    } else {
                        this.updateSaveBounds();
                    }
                }
            } else {
                double deltaX = event.getScreenX() - this.getX();
                double deltaY = Math.min(event.getScreenY(), visualBounds.getMaxY()) - this.getY();
                if (Cursor.E_RESIZE.equals(cursor)) {
                    deltaX += this.stageShadow.getRight();
                    this.setStageWidth(deltaX);
                } else if (Cursor.W_RESIZE.equals(cursor)) {
                    deltaX -= this.stageShadow.getLeft();
                    if (this.setStageWidth(this.getWidth() - deltaX)) {
                        this.setX(this.getX() + deltaX);
                    }
                } else if (Cursor.S_RESIZE.equals(cursor)) {
                    deltaY += this.stageShadow.getBottom();
                    this.setStageHeight(deltaY);
                    if (event.getScreenY() >= visualBounds.getMaxY() - 1) {
                        this.showPreview(1, this.getX(), 0, this.getWidth(), visualBounds.getHeight());
                    } else {
                        this.closePreview();
                    }
                } else if (Cursor.N_RESIZE.equals(cursor)) {
                    deltaY -= this.stageShadow.getTop();
                    if (this.setStageHeight(this.getHeight() - deltaY)) {
                        this.setY(this.getY() + deltaY);
                    }
                    if (event.getScreenY() == visualBounds.getMinY()) {
                        this.showPreview(2, this.getX(), this.getY(), this.getWidth(), visualBounds.getHeight());
                    } else {
                        this.closePreview();
                    }
                } else if (Cursor.NE_RESIZE.equals(cursor)) {
                    deltaX += this.stageShadow.getRight();
                    deltaY -= this.stageShadow.getTop();
                    this.setStageWidth(deltaX);
                    if (this.setStageHeight(this.getHeight() - deltaY)) {
                        this.setY(this.getY() + deltaY);
                    }
                } else if (Cursor.NW_RESIZE.equals(cursor)) {
                    deltaX -= this.stageShadow.getLeft();
                    deltaY -= this.stageShadow.getTop();
                    if (this.setStageWidth(this.getWidth() - deltaX)) {
                        this.setX(this.getX() + deltaX);
                    }
                    if (this.setStageHeight(this.getHeight() - deltaY)) {
                        this.setY(this.getY() + deltaY);
                    }
                } else if (Cursor.SE_RESIZE.equals(cursor)) {
                    deltaX += this.stageShadow.getRight();
                    deltaY += this.stageShadow.getBottom();
                    this.setStageWidth(deltaX);
                    this.setStageHeight(deltaY);
                } else if (Cursor.SW_RESIZE.equals(cursor)) {
                    deltaX -= this.stageShadow.getLeft();
                    deltaY += this.stageShadow.getBottom();
                    this.setStageHeight(deltaY);
                    if (this.setStageWidth(this.getWidth() - deltaX)) {
                        this.setX(this.getX() + deltaX);
                    }
                }
            }
            this.updateStageShadow();
            this.dragging = true;
            event.consume();
        }
    }

    protected void updateSaveBounds() {
        this.savedBounds.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.stageShadow.getTop(), this.stageShadow.getRight(), this.stageShadow.getBottom(), this.stageShadow.getLeft());
    }

    private boolean setStageWidth(double width) {
        if (width >= Math.min(this.getWidth(), this.getMinWidth())) {
            this.setWidth(width);
            return true;
        }
        return false;
    }

    private boolean setStageHeight(double height) {
        if (height >= Math.min(this.getHeight(), this.getMinHeight())) {
            this.setHeight(height);
            return true;
        }
        return false;
    }

    protected void lockCursor() {
        if (Platform.isWindows()) {
            WinDef.RECT rect = new WinDef.RECT();
            rect.top = (int) visualBounds.getMinY();
            rect.left = (int) visualBounds.getMinX();
            rect.right = (int) visualBounds.getWidth();
            rect.bottom = (int) visualBounds.getHeight();
            User32.INSTANCE.ClipCursor(rect);
        }
    }

    protected void unlockCursor() {
        if (Platform.isWindows()) {
            User32.INSTANCE.ClipCursor(null);
        }
    }

    protected void showPreview(int previewStyle, double toX, double toY, double toWidth, double toHeight) {
        if (this.previewing == 0) {
            this.previewing = previewStyle;
            this.previewStage.show();
            this.setAlwaysOnTop(true);
            this.previewAnimation.play(this.stage.getX(), this.stage.getY(), this.stage.getWidth(), this.stage.getHeight(), toX, toY, toWidth, toHeight);
        } else if (this.previewing != previewStyle) {
            this.previewing = previewStyle;
            this.previewAnimation.play(this.previewStage.getX(), this.previewStage.getY(), this.previewStage.getWidth(), this.previewStage.getHeight(), toX, toY, toWidth, toHeight);
        }
    }

    protected void closePreview() {
        if (this.previewing > 0) {
            this.previewing = 0;
            this.previewStage.close();
            this.previewAnimation.stop();
            this.setAlwaysOnTop(false);
        }
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    @Override
    public void setContent(Parent parent) {
        if (this.body.getChildren().isEmpty()) {
            this.body.getChildren().add(0, parent);
        } else {
            this.body.getChildren().set(0, parent);
        }
        AnchorPane.setTopAnchor(parent, 0.0);
        AnchorPane.setLeftAnchor(parent, 0.0);
        AnchorPane.setRightAnchor(parent, 0.0);
        AnchorPane.setBottomAnchor(parent, 0.0);
        parent.toBack();
    }

    @Override
    public void show() {
        super.show();
        this.updateSaveBounds();
    }

    public static class StageAnimation extends Transition {

        private double fromX;
        private double fromY;
        private double fromWidth;
        private double fromHeight;
        private double toX;
        private double toY;
        private double toWidth;
        private double toHeight;
        private final DecoratedStage stage;

        public StageAnimation(DecoratedStage stage) {
            this.stage = stage;
            this.setInterpolator(Interpolator.LINEAR);
            this.setCycleDuration(Duration.millis(200));
        }

        @Override
        protected void interpolate(double fraction) {
            this.stage.setX(fromX + (toX - fromX) * fraction);
            this.stage.setY(fromY + (toY - fromY) * fraction);
            this.stage.setWidth(fromWidth + (toWidth - fromWidth) * fraction);
            this.stage.setHeight(fromHeight + (toHeight - fromHeight) * fraction);
        }

        public void play(double fromX, double fromY, double fromWidth, double fromHeight, double toX, double toY, double toWidth, double toHeight) {
            this.fromX = fromX;
            this.fromY = fromY;
            this.fromWidth = fromWidth;
            this.fromHeight = fromHeight;
            this.toX = toX;
            this.toY = toY;
            this.toWidth = toWidth;
            this.toHeight = toHeight;
            this.play();
        }
    }

    public static class Rectangle {

        public double x;
        public double y;
        public double width;
        public double height;
        public double top;
        public double right;
        public double bottom;
        public double left;

        public void setBounds(double x, double y, double width, double height, double top, double right, double bottom, double left) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.left = left;
        }
    }
}


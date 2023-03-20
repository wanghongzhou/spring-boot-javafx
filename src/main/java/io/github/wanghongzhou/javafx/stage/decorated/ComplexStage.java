package io.github.wanghongzhou.javafx.stage.decorated;

import io.github.wanghongzhou.javafx.stage.StageStyle;
import io.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * root (StackPane)
 * -- body (AnchorPane)
 *    -- bar (HBox)
 *       -- barIcon (HBox)
 *       -- barTitle (HBox)
 *       -- BarButtons (HBox)
 *    -- container (ScrollPane)
 * </pre>
 *
 * @author Brian
 */
public class ComplexStage extends TitledStage {

    protected final HBox barIcon = new HBox(1);
    protected final HBox barButtons = new HBox(1);

    protected final Button btnIcon = new Button();
    protected final Button btnClose = new Button();
    protected final Button btnMaximize = new Button();
    protected final Button btnMinimize = new Button();
    protected final Button btnFullScreen = new Button();

    protected final SVGPath icon = new SVGPath();
    protected final ImageView viewClose = new ImageView(new Image(Objects.requireNonNull(ComplexStage.class.getResource("/icons/close.png")).toExternalForm()));
    protected final ImageView viewMinimize = new ImageView(new Image(Objects.requireNonNull(ComplexStage.class.getResource("/icons/minimize.png")).toExternalForm()));
    protected final ImageView viewMaximize = new ImageView(new Image(Objects.requireNonNull(ComplexStage.class.getResource("/icons/maximize.png")).toExternalForm()));
    protected final ImageView viewRestore = new ImageView(new Image(Objects.requireNonNull(ComplexStage.class.getResource("/icons/restore.png")).toExternalForm()));
    protected final ImageView viewFullScreen = new ImageView(new Image(Objects.requireNonNull(ComplexStage.class.getResource("/icons/fullscreen.png")).toExternalForm()));
    protected final ImageView viewUnFullScreen = new ImageView(new Image(Objects.requireNonNull(ComplexStage.class.getResource("/icons/unfullscreen.png")).toExternalForm()));

    protected final DoubleBinding buttonWidth = barHeight.add(15);
    protected final DoubleBinding buttonHeight = barHeight.subtract(1);

    public ComplexStage(Stage stage) {
        super(stage);
    }

    @Override
    protected HBox initBar() {
        this.bar.getChildren().add(this.initBarIcon());
        super.initBar();
        this.bar.getChildren().add(this.initBarButtons());
        return this.bar;
    }

    protected HBox initBarIcon() {
        this.barIcon.setAlignment(Pos.CENTER_LEFT);
        this.barIcon.getStyleClass().add("barIcon");
        this.barIcon.minHeightProperty().bind(this.buttonHeight);
        this.barIcon.prefHeightProperty().bind(this.buttonHeight);
        this.barIcon.minWidthProperty().bind(this.barButtons.minWidthProperty());
        this.initIcon();
        return this.barIcon;
    }

    protected Node initIcon() {
        this.btnIcon.getStyleClass().add("btnIcon");
        this.btnIcon.minWidthProperty().bind(this.buttonHeight);
        this.btnIcon.prefWidthProperty().bind(this.buttonHeight);
        this.btnIcon.minHeightProperty().bind(this.buttonHeight);
        this.btnIcon.prefHeightProperty().bind(this.buttonHeight);
        this.btnIcon.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        if (Objects.isNull(this.btnIcon.getGraphic())) {
            this.icon.getStyleClass().add("icon");
            this.icon.setContent("M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z");
            this.btnIcon.setGraphic(this.icon);
        }
        return this.btnIcon;
    }

    protected HBox initBarButtons() {
        this.barButtons.setAlignment(Pos.CENTER_RIGHT);
        this.barButtons.getStyleClass().add("barButtons");
        this.barButtons.minHeightProperty().bind(this.buttonHeight);
        this.barButtons.prefHeightProperty().bind(this.buttonHeight);
        this.initButtonClose();
        this.initButtonMinimize();
        this.initButtonMaximize();
        this.initButtonFullScreen();
        return this.barButtons;
    }

    protected void initButtonClose() {
        this.btnClose.setGraphic(this.viewClose);
        this.btnClose.getStyleClass().add("close");
        this.btnClose.minWidthProperty().bind(this.buttonWidth);
        this.btnClose.prefWidthProperty().bind(this.buttonWidth);
        this.btnClose.minHeightProperty().bind(this.buttonHeight);
        this.btnClose.prefHeightProperty().bind(this.buttonHeight);
        this.btnClose.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    protected void initButtonMinimize() {
        this.btnMinimize.setGraphic(this.viewMinimize);
        this.btnMinimize.getStyleClass().add("minimize");
        this.btnMinimize.minWidthProperty().bind(this.buttonWidth);
        this.btnMinimize.prefWidthProperty().bind(this.buttonWidth);
        this.btnMinimize.minHeightProperty().bind(this.buttonHeight);
        this.btnMinimize.prefHeightProperty().bind(this.buttonHeight);
        this.btnMinimize.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    protected void initButtonMaximize() {
        this.btnMaximize.setGraphic(this.viewMaximize);
        this.btnMaximize.getStyleClass().add("maximize");
        this.btnMaximize.minWidthProperty().bind(this.buttonWidth);
        this.btnMaximize.prefWidthProperty().bind(this.buttonWidth);
        this.btnMaximize.minHeightProperty().bind(this.buttonHeight);
        this.btnMaximize.prefHeightProperty().bind(this.buttonHeight);
        this.btnMaximize.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    protected void initButtonFullScreen() {
        this.btnFullScreen.setGraphic(this.viewFullScreen);
        this.btnFullScreen.getStyleClass().add("fullscreen");
        this.btnFullScreen.minWidthProperty().bind(this.buttonWidth);
        this.btnFullScreen.prefWidthProperty().bind(this.buttonWidth);
        this.btnFullScreen.minHeightProperty().bind(this.buttonHeight);
        this.btnFullScreen.prefHeightProperty().bind(this.buttonHeight);
        this.btnFullScreen.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    protected void stageStyleChanged(ObservableValue<? extends StageStyle> observable, StageStyle oldValue, StageStyle newValue) {
        super.stageStyleChanged(observable, oldValue, newValue);
        this.resetBarIconByStyle();
        this.resetBarButtonsByStyle();
    }

    protected void resetBarIconByStyle() {
        this.barIcon.getChildren().clear();
        if (this.stageStyle.get().is(DefaultStageStyle.ICON)) {
            this.barIcon.getChildren().addAll(this.initIcon());
            this.btnIcon.setOnMouseClicked(this::iconButtonMouseClickedHandler);
        }
    }

    protected void resetBarButtonsByStyle() {
        this.barButtons.getChildren().clear();
        if (this.stageStyle.get().is(DefaultStageStyle.FULLSCREEN)) {
            this.barButtons.getChildren().add(this.btnFullScreen);
            this.btnFullScreen.setOnMouseClicked(this::fullScreenButtonMouseClickedHandler);
        }
        if (this.stageStyle.get().is(DefaultStageStyle.MINIMIZE)) {
            this.barButtons.getChildren().add(this.btnMinimize);
            this.btnMinimize.setOnMouseClicked(this::minimizeButtonMouseClickedHandler);
        }
        if (this.stageStyle.get().is(DefaultStageStyle.MAXIMIZE)) {
            this.barButtons.getChildren().add(this.btnMaximize);
            this.btnMaximize.setOnMouseClicked(this::maximizeButtonMouseClickedHandler);
        }
        if (this.stageStyle.get().is(DefaultStageStyle.CLOSE)) {
            this.barButtons.getChildren().add(this.btnClose);
            this.btnClose.setOnMouseClicked(this::closeButtonMouseClickedHandler);
        }
        this.barButtons.setMinWidth(this.buttonWidth.get() * this.barButtons.getChildren().size());
        this.barButtons.getChildren().stream().peek((node) -> ((Button) node).minHeightProperty().bind(this.buttonHeight)).peek((node) -> ((Button) node).prefHeightProperty().bind(this.buttonHeight)).peek((node) -> ((Button) node).minWidthProperty().bind(this.buttonWidth)).forEach((node) -> ((Button) node).prefWidthProperty().bind(this.buttonWidth));
    }

    protected void iconButtonMouseClickedHandler(MouseEvent event) {
        if (event.getClickCount() == 2) {
            this.close();
        }
    }

    protected void closeButtonMouseClickedHandler(MouseEvent event) {
        this.close();
    }

    protected void maximizeButtonMouseClickedHandler(MouseEvent event) {
        this.setMaximized(!this.isMaximized());
    }

    protected void minimizeButtonMouseClickedHandler(MouseEvent event) {
        this.setIconified(true);
    }

    protected void fullScreenButtonMouseClickedHandler(MouseEvent event) {
        this.setFullScreen(!this.isFullScreen());
    }

    @Override
    protected void resizableHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        super.resizableHandler(observable, oldValue, newValue);
        if (oldValue != newValue) {
            this.btnMaximize.setDisable(!newValue);
        }
    }

    @Override
    protected void maximizedHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        super.maximizedHandler(observable, oldValue, newValue);
        if (newValue) {
            this.btnMaximize.setGraphic(viewRestore);
        } else {
            this.btnMaximize.setGraphic(viewMaximize);
        }
    }

    @Override
    protected void fullScreenHandler(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        super.fullScreenHandler(observable, oldValue, newValue);
        if (newValue) {
            this.btnFullScreen.setGraphic(viewUnFullScreen);
            this.barButtons.getChildren().removeAll(this.btnMaximize, this.btnMinimize);
            this.barButtons.setMinWidth(this.buttonWidth.get() * (double) this.barButtons.getChildren().size());
        } else {
            if (oldValue) {
                this.btnFullScreen.setGraphic(viewFullScreen);
                this.barButtons.getChildren().addAll(this.btnMinimize, this.btnMaximize);
                this.barButtons.setMinWidth(this.buttonWidth.get() * (double) this.barButtons.getChildren().size());
                this.btnMaximize.toFront();
                this.btnClose.toFront();
            }
        }
    }

    public final DoubleBinding buttonHeightBinding() {
        return this.buttonHeight;
    }

    public final DoubleBinding buttonWidthBinding() {
        return this.buttonWidth;
    }

    @Override
    public void setIcons(List<Image> icons) {
        this.stage.getIcons().setAll(icons);
        this.btnIcon.setGraphic(new ImageView(icons.get(0)));
    }
}

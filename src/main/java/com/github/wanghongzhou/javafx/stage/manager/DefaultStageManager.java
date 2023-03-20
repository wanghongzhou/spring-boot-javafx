package com.github.wanghongzhou.javafx.stage.manager;

import com.github.wanghongzhou.javafx.splash.SplashScreen;
import com.github.wanghongzhou.javafx.stage.DecoratedStage;
import com.github.wanghongzhou.javafx.stage.StageStyle;
import com.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import com.github.wanghongzhou.javafx.view.AbstractView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Brian
 */
@Slf4j
public class DefaultStageManager extends AbstractStageManager {

    protected DecoratedStage primaryDecoratedStage;
    protected ObservableMap<Class<? extends AbstractView>, DecoratedStage> popupWindows = FXCollections.observableHashMap();

    @Override
    public DecoratedStage getPrimaryStage() {
        return this.primaryDecoratedStage;
    }

    @Override
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @Override
    public ObservableMap<Class<? extends AbstractView>, DecoratedStage> getPopupWindows() {
        return this.popupWindows;
    }

    @Override
    public DecoratedStage switchScene(AbstractView view) {
        if (Objects.isNull(this.primaryDecoratedStage)) {
            this.primaryDecoratedStage = this.defaultStageFactory.buildDecoratedStage(detectStageStyle(view), this.primaryStage);
        }
        return this.switchScene(this.primaryDecoratedStage, view);
    }

    @Override
    public DecoratedStage switchScene(Class<? extends AbstractView> from, AbstractView to) {
        return this.switchScene(this.popupWindows.get(from), to);
    }

    protected DecoratedStage switchScene(DecoratedStage stage, AbstractView view) {
        stage.initStyle(detectStageStyle(view));
        if (StringUtils.hasText(view.getTitle())) {
            stage.setTitle(view.getTitle());
        } else {
            if (StringUtils.hasText(getDefaultTitle())) {
                stage.setTitle(getDefaultTitle());
            }
        }
        if (StringUtils.hasText(view.getTheme())) {
            stage.setTheme(view.getTheme());
        } else {
            if (StringUtils.hasText(getDefaultTheme())) {
                stage.setTheme(getDefaultTheme());
            }
        }
        if (Objects.nonNull(view.getIcons()) && view.getIcons().length > 0) {
            stage.setIcons(Arrays.stream(view.getIcons()).map(Image::new).toList());
        } else {
            if (Objects.nonNull(getDefaultIcons())) {
                stage.setIcons(getDefaultIcons());
            }
        }
        if (view.getWidth() > 0) {
            stage.setWidth(view.getWidth());
        }
        if (view.getHeight() > 0) {
            stage.setHeight(view.getHeight());
        }
        stage.setMaximized(view.isMaximize());
        stage.setResizable(view.isResizeable());
        stage.setFullScreen(view.isFullscreen());
        stage.setContent(view.getView());
        return stage;
    }

    protected StageStyle detectStageStyle(AbstractView view) {
        if (Objects.isNull(view.getStageStyle())) {
            if (Objects.isNull(this.getDefaultStageStyle())) {
                return DefaultStageStyle.DECORATED;
            }
            return this.getDefaultStageStyle();
        }
        return view.getStageStyle();
    }

    @Override
    public DecoratedStage showPopWindow(AbstractView view) {
        return this.showPopWindow(view, stage -> {
        });
    }

    @Override
    public DecoratedStage showPopWindow(AbstractView view, Consumer<DecoratedStage> settings) {
        return this.showPopWindow(view, this.primaryStage, settings);
    }

    @Override
    public DecoratedStage showPopWindow(AbstractView view, Class<? extends AbstractView> ownerClass) {
        return this.showPopWindow(view, this.popupWindows.get(ownerClass).getStage(), stage -> {
        });
    }

    @Override
    public DecoratedStage showPopWindow(AbstractView view, Class<? extends AbstractView> ownerClass, Consumer<DecoratedStage> settings) {
        return this.showPopWindow(view, this.popupWindows.get(ownerClass).getStage(), settings);
    }

    protected DecoratedStage showPopWindow(AbstractView view, Stage ownerClass, Consumer<DecoratedStage> settings) {
        DecoratedStage stage = this.defaultStageFactory.buildDecoratedStage(detectStageStyle(view));
        stage.initOwner(ownerClass);
        stage.centerOnScreen();
        stage.setOnHidden(event -> this.popupWindows.remove(view.getClass()));
        try {
            settings.accept(stage);
            this.switchScene(stage, view).show();
            this.popupWindows.put(view.getClass(), stage);
        } catch (Exception exception) {
            this.showErrorAlert(exception);
        }
        return stage;
    }

    @Override
    public DecoratedStage showModalPopWindow(AbstractView view) {
        return this.showModalPopWindow(view, stage -> {
        });
    }

    @Override
    public DecoratedStage showModalPopWindow(AbstractView view, Consumer<DecoratedStage> settings) {
        return this.showPopWindow(view, stage -> {
            settings.accept(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
        });
    }

    @Override
    public DecoratedStage showModalPopWindow(AbstractView view, Class<? extends AbstractView> ownerClass) {
        return this.showModalPopWindow(view, ownerClass, stage -> {
        });
    }

    @Override
    public DecoratedStage showModalPopWindow(AbstractView view, Class<? extends AbstractView> ownerClass, Consumer<DecoratedStage> settings) {
        return this.showPopWindow(view, ownerClass, stage -> {
            settings.accept(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
        });
    }

    @Override
    public <T extends AbstractView & SplashScreen> DecoratedStage showSplashScreen(T splashScreen) {
        DecoratedStage stage = this.defaultStageFactory.buildDecoratedStage(detectStageStyle(splashScreen));
        stage.centerOnScreen();
        stage.setAlwaysOnTop(true);
        splashScreen.settingStage(stage);
        this.switchScene(stage, splashScreen).show();
        return stage;
    }

    @Override
    public void closePopWindow(Class<? extends AbstractView> viewClass) {
        DecoratedStage stage = this.popupWindows.remove(viewClass);
        if (Objects.nonNull(stage)) {
            stage.close();
        }
    }

    @Override
    public boolean isOpenedPopWindow(Class<? extends AbstractView> viewClass) {
        return this.popupWindows.containsKey(viewClass);
    }

    @Override
    public DecoratedStage getOpenedPopWindow(Class<? extends AbstractView> viewClass) {
        return this.popupWindows.get(viewClass);
    }

    @Override
    public void showInfoAlert(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }

    @Override
    public void showErrorAlert(String message) {
        log.error("Error: {}", message);
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    @Override
    public void showErrorAlert(Throwable throwable) {
        log.error("Error: ", throwable);
        new Alert(Alert.AlertType.ERROR, throwable.getMessage()).show();
    }

    @Override
    public void showErrorAlertAndExit(String message) {
        log.error("Error: {}", message);
        new Alert(Alert.AlertType.ERROR, message).showAndWait().ifPresent(response -> Platform.exit());
    }

    @Override
    public void showErrorAlertAndExit(Throwable throwable) {
        log.error("Error: ", throwable);
        new Alert(Alert.AlertType.ERROR, throwable.getMessage()).showAndWait().ifPresent(response -> Platform.exit());
    }
}

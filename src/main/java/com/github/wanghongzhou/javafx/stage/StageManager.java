package com.github.wanghongzhou.javafx.stage;

import com.github.wanghongzhou.javafx.splash.SplashScreen;
import com.github.wanghongzhou.javafx.view.AbstractView;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Brian
 */
public interface StageManager {

    String getDefaultTitle();

    void setDefaultTitle(String title);

    String getDefaultTheme();

    void setDefaultTheme(String theme);

    List<Image> getDefaultIcons();

    void setDefaultIcons(List<Image> icons);

    StageStyle getDefaultStageStyle();

    void setDefaultStageStyle(StageStyle stageStyle);

    StageFactory getDefaultStageFactory();

    void setDefaultStageFactory(StageFactory stageFactory);

    DecoratedStage getPrimaryStage();

    void setPrimaryStage(Stage stage);

    ObservableMap<Class<? extends AbstractView>, DecoratedStage> getPopupWindows();

    DecoratedStage switchScene(AbstractView view);

    DecoratedStage switchScene(Class<? extends AbstractView> from, AbstractView to);

    DecoratedStage showPopWindow(AbstractView view);

    DecoratedStage showPopWindow(AbstractView view, Consumer<DecoratedStage> settings);

    DecoratedStage showPopWindow(AbstractView view, Class<? extends AbstractView> ownerClass);

    DecoratedStage showPopWindow(AbstractView view, Class<? extends AbstractView> ownerClass, Consumer<DecoratedStage> settings);

    DecoratedStage showModalPopWindow(AbstractView view);

    DecoratedStage showModalPopWindow(AbstractView view, Consumer<DecoratedStage> settings);

    DecoratedStage showModalPopWindow(AbstractView view, Class<? extends AbstractView> ownerClass);

    DecoratedStage showModalPopWindow(AbstractView view, Class<? extends AbstractView> ownerClass, Consumer<DecoratedStage> settings);

    <T extends AbstractView & SplashScreen> DecoratedStage showSplashScreen(T splashScreen);

    void closePopWindow(Class<? extends AbstractView> viewClass);

    boolean isOpenedPopWindow(Class<? extends AbstractView> viewClass);

    DecoratedStage getOpenedPopWindow(Class<? extends AbstractView> viewClass);

    void showInfoAlert(String message);

    void showErrorAlert(String message);

    void showErrorAlert(Throwable throwable);

    void showErrorAlertAndExit(String message);

    void showErrorAlertAndExit(Throwable throwable);
}

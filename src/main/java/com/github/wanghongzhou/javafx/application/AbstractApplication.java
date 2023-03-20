package com.github.wanghongzhou.javafx.application;

import com.github.wanghongzhou.javafx.splash.SplashScreen;
import com.github.wanghongzhou.javafx.stage.DecoratedStage;
import com.github.wanghongzhou.javafx.stage.StageFactory;
import com.github.wanghongzhou.javafx.stage.StageManager;
import com.github.wanghongzhou.javafx.stage.StageStyle;
import com.github.wanghongzhou.javafx.stage.manager.DefaultStageManager;
import com.github.wanghongzhou.javafx.view.AbstractView;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author Brian
 */
@Slf4j
public class AbstractApplication extends Application {

    protected CompletableFuture<Runnable> splashIsShowing = new CompletableFuture<>();

    protected static String[] savedArgs;
    protected static Object splashScreen;
    protected static SystemTray systemTray;
    protected static HostServices hostServices;
    protected static StageManager stageManager;
    protected static Class<? extends AbstractView> savedMainView;
    protected static ConfigurableApplicationContext applicationContext;

    public final static String KEY_CSS = "javafx.css";
    public final static String KEY_TITLE = "javafx.title";
    public final static String KEY_APP_ICONS = "javafx.icons";
    public final static String KEY_STAGE_STYLE = "javafx.stage.style";
    public final static String KEY_STAGE_THEME = "javafx.stage.theme";

    @Override
    public void init() {
        CompletableFuture.supplyAsync(() -> applicationContext = SpringApplication.run(this.getClass(), savedArgs)).whenComplete((ctx, throwable) -> {
            if (Objects.nonNull(throwable)) {
                log.error("Failed to load spring application context: ", throwable);
                CompletableFuture.runAsync(() -> stageManager.showErrorAlertAndExit(throwable), Platform::runLater);
            } else {
                CompletableFuture.runAsync(() -> launchApplication(ctx), Platform::runLater);
                CompletableFuture.runAsync(() -> ((SplashScreen) splashScreen).runWithApplicationContext(ctx), Platform::runLater);
            }
        }).thenAcceptBothAsync(splashIsShowing, (ctx, splashCompleted) -> Platform.runLater(splashCompleted));
    }

    @Override
    public void start(Stage stage) {
        this.showSplashScreen(stage);
        AbstractApplication.hostServices = this.getHostServices();
    }

    protected void showSplashScreen(Stage stage) {
        try {
            if (Objects.isNull(splashScreen)) {
                splashIsShowing.complete(() -> {
                    this.initStageManager(stage);
                    this.showMainStage();
                });
            } else {
                SplashScreen splash = ((SplashScreen) splashScreen);
                DecoratedStage splashStage = stageManager.showSplashScreen((AbstractView & SplashScreen) splash);
                splash.registerCompletedCallback(() -> splashIsShowing.complete(() -> {
                    this.initStageManager(stage);
                    this.showMainStage();
                    splashStage.close();
                    splashScreen = null;
                }));
                CompletableFuture.runAsync(splash::runWithoutApplicationContext, Platform::runLater);
            }
        } catch (Throwable t) {
            log.error("Failed to load splash screen: ", t);
            stageManager.showErrorAlertAndExit(t);
        }
    }

    protected void initStageManager(Stage stage) {
        stageManager.setPrimaryStage(stage);
        String defaultTitle = applicationContext.getEnvironment().getProperty(KEY_TITLE, String.class);
        if (StringUtils.hasText(defaultTitle)) {
            stageManager.setDefaultTitle(defaultTitle);
            log.debug("Set default title: {}", defaultTitle);
        }
        String defaultTheme = applicationContext.getEnvironment().getProperty(KEY_STAGE_THEME, String.class);
        if (StringUtils.hasText(defaultTheme)) {
            stageManager.setDefaultTheme(Objects.requireNonNull(getClass().getResource(defaultTheme)).toExternalForm());
            log.debug("Set default theme: {}", defaultTheme);
        }
        String defaultStyle = applicationContext.getEnvironment().getProperty(KEY_STAGE_STYLE, String.class);
        if (StringUtils.hasText(defaultStyle)) {
            try {
                int index = defaultStyle.lastIndexOf(".");
                Class<?> clazz = Class.forName(defaultStyle.substring(0, index));
                StageStyle stageStyle = (StageStyle) clazz.getDeclaredField(defaultStyle.substring(index + 1)).get(null);
                stageManager.setDefaultStageStyle(stageStyle);
                log.debug("Set default stage style: {}", stageStyle);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                log.error("Failed to load default stage style: ", e);
                throw new RuntimeException(e);
            }
        }
        List<String> appIcons = Binder.get(applicationContext.getEnvironment()).bind(KEY_APP_ICONS, Bindable.listOf(String.class)).orElse(Collections.emptyList());
        if (!appIcons.isEmpty()) {
            log.debug("Set default icons: {}", appIcons);
            stageManager.setDefaultIcons(appIcons.stream().map((s) -> new Image(Objects.requireNonNull(getClass().getResource(s)).toExternalForm())).toList());
        }

        Map<String, StageFactory> stageFactoryMap = applicationContext.getBeansOfType(StageFactory.class);
        if (!stageFactoryMap.isEmpty()) {
            log.debug("Found StageFactory bean: {}", stageFactoryMap);
            stageManager.setDefaultStageFactory(applicationContext.getBean(StageFactory.class));
        }
    }

    protected void showMainStage() {
        try {
            stageManager.switchScene(applicationContext.getBean(savedMainView)).show();
        } catch (Throwable t) {
            log.error("Failed to load main view: ", t);
            stageManager.showErrorAlertAndExit(t);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (Objects.nonNull(applicationContext)) {
            applicationContext.close();
        }
    }

    public void launchApplication(final ConfigurableApplicationContext ctx) {

    }

    public static DecoratedStage getPrimaryStage() {
        return stageManager.getPrimaryStage();
    }

    public static ObservableMap<Class<? extends AbstractView>, DecoratedStage> getPopupWindows() {
        return stageManager.getPopupWindows();
    }

    public static DecoratedStage switchScene(Class<? extends AbstractView> viewClass) {
        return stageManager.switchScene(applicationContext.getBean(viewClass));
    }

    public static DecoratedStage switchScene(Class<? extends AbstractView> fromClass, Class<? extends AbstractView> toClass) {
        return stageManager.switchScene(fromClass, applicationContext.getBean(toClass));
    }

    public static DecoratedStage showPopWindow(Class<? extends AbstractView> viewClass) {
        return stageManager.showPopWindow(applicationContext.getBean(viewClass));
    }

    public static DecoratedStage showPopWindow(Class<? extends AbstractView> viewClass, Consumer<DecoratedStage> settings) {
        return stageManager.showPopWindow(applicationContext.getBean(viewClass), settings);
    }

    public static DecoratedStage showPopWindow(Class<? extends AbstractView> viewClass, Class<? extends AbstractView> ownerClass) {
        return stageManager.showPopWindow(applicationContext.getBean(viewClass), ownerClass);
    }

    public static DecoratedStage showPopWindow(Class<? extends AbstractView> viewClass, Class<? extends AbstractView> ownerClass, Consumer<DecoratedStage> settings) {
        return stageManager.showPopWindow(applicationContext.getBean(viewClass), ownerClass, settings);
    }

    public static DecoratedStage showModalPopWindow(Class<? extends AbstractView> viewClass) {
        return stageManager.showModalPopWindow(applicationContext.getBean(viewClass));
    }

    public static DecoratedStage showModalPopWindow(Class<? extends AbstractView> viewClass, Consumer<DecoratedStage> settings) {
        return stageManager.showModalPopWindow(applicationContext.getBean(viewClass), settings);
    }

    public static DecoratedStage showModalPopWindow(Class<? extends AbstractView> viewClass, Class<? extends AbstractView> ownerClass) {
        return stageManager.showModalPopWindow(applicationContext.getBean(viewClass), ownerClass);
    }

    public static DecoratedStage showModalPopWindow(Class<? extends AbstractView> viewClass, Class<? extends AbstractView> ownerClass, Consumer<DecoratedStage> settings) {
        return stageManager.showModalPopWindow(applicationContext.getBean(viewClass), ownerClass, settings);
    }

    public static void closePopWindow(Class<? extends AbstractView> viewClass) {
        stageManager.closePopWindow(viewClass);
    }

    public static boolean isOpenedPopWindow(Class<? extends AbstractView> viewClass) {
        return stageManager.isOpenedPopWindow(viewClass);
    }

    public static DecoratedStage getOpenedPopWindow(Class<? extends AbstractView> viewClass) {
        return stageManager.getOpenedPopWindow(viewClass);
    }

    public static void showInfoAlert(String message) {
        stageManager.showInfoAlert(message);
    }

    public static void showErrorAlert(String message) {
        stageManager.showErrorAlert(message);
    }

    public static void showErrorAlert(Throwable throwable) {
        stageManager.showErrorAlert(throwable);
    }

    public static void showErrorAlertAndExit(String message) {
        stageManager.showErrorAlertAndExit(message);
    }

    public static void showErrorAlertAndExit(Throwable throwable) {
        stageManager.showErrorAlertAndExit(throwable);
    }

    public static <S extends AbstractView & SplashScreen> void launch(final Class<? extends AbstractApplication> appClass, final Class<? extends AbstractView> mainView, final String[] args) {
        launch(appClass, mainView, DefaultStageManager.class, null, args);
    }

    public static <S extends AbstractView & SplashScreen> void launch(final Class<? extends AbstractApplication> appClass, final Class<? extends AbstractView> mainView, final Class<? extends S> splashScreen, final String[] args) {
        launch(appClass, mainView, DefaultStageManager.class, splashScreen, args);
    }

    public static <S extends AbstractView & SplashScreen> void launch(final Class<? extends AbstractApplication> appClass, final Class<? extends AbstractView> mainView, final Class<? extends StageManager> stageManager, final Class<? extends S> splashScreen, final String[] args) {
        AbstractApplication.savedArgs = args;
        AbstractApplication.savedMainView = mainView;

        if (SystemTray.isSupported()) {
            AbstractApplication.systemTray = SystemTray.getSystemTray();
        }

        try {
            if (Objects.nonNull(splashScreen)) {
                AbstractApplication.splashScreen = splashScreen.getDeclaredConstructor().newInstance();
            }
            AbstractApplication.stageManager = stageManager.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Application.launch(appClass, args);
    }

    public static StageManager getStageManager() {
        return stageManager;
    }

    public static SystemTray getSystemTray() {
        return systemTray;
    }

    public static HostServices getAppHostServices() {
        return hostServices;
    }
}

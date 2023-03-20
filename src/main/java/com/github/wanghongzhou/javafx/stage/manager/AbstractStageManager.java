package com.github.wanghongzhou.javafx.stage.manager;

import com.github.wanghongzhou.javafx.stage.StageFactory;
import com.github.wanghongzhou.javafx.stage.StageManager;
import com.github.wanghongzhou.javafx.stage.StageStyle;
import com.github.wanghongzhou.javafx.stage.factory.DefaultStageFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author Brian
 */
public abstract class AbstractStageManager implements StageManager {


    protected String defaultTitle;
    protected String defaultTheme;
    protected List<Image> defaultIcons;
    protected Stage primaryStage;
    protected StageStyle defaultStageStyle;
    protected StageFactory defaultStageFactory = new DefaultStageFactory();

    @Override
    public String getDefaultTitle() {
        return this.defaultTitle;
    }

    @Override
    public void setDefaultTitle(String title) {
        this.defaultTitle = title;
    }

    @Override
    public String getDefaultTheme() {
        return this.defaultTheme;
    }

    @Override
    public void setDefaultTheme(String theme) {
        this.defaultTheme = theme;
    }

    @Override
    public List<Image> getDefaultIcons() {
        return this.defaultIcons;
    }

    @Override
    public void setDefaultIcons(List<Image> icons) {
        this.defaultIcons = icons;
    }

    @Override
    public StageStyle getDefaultStageStyle() {
        return this.defaultStageStyle;
    }

    @Override
    public void setDefaultStageStyle(StageStyle stageStyle) {
        this.defaultStageStyle = stageStyle;
    }

    @Override
    public StageFactory getDefaultStageFactory() {
        return this.defaultStageFactory;
    }

    @Override
    public void setDefaultStageFactory(StageFactory stageFactory) {
        this.defaultStageFactory = stageFactory;
    }
}

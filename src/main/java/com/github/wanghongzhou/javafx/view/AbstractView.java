package com.github.wanghongzhou.javafx.view;

import com.github.wanghongzhou.javafx.stage.StageStyle;
import javafx.scene.Parent;

/**
 * @author Brian
 */
public abstract class AbstractView {

    public abstract Parent getView();

    public String getTitle() {
        return null;
    }

    public String getTheme() {
        return null;
    }

    public double getWidth() {
        return 0;
    }

    public double getHeight() {
        return 0;
    }

    public boolean isMaximize() {
        return false;
    }

    public boolean isResizeable() {
        return true;
    }

    public boolean isFullscreen() {
        return false;
    }

    public String[] getIcons() {
        return null;
    }

    public StageStyle getStageStyle() {
        return null;
    }
}

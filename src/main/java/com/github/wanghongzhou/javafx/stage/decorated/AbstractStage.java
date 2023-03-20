package com.github.wanghongzhou.javafx.stage.decorated;

import com.github.wanghongzhou.javafx.stage.DecoratedStage;
import javafx.stage.Stage;

/**
 * @author Brian
 */
public abstract class AbstractStage implements DecoratedStage {

    protected final Stage stage;

    public AbstractStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }
}

package com.github.wanghongzhou.javafx.stage.factory;

import com.github.wanghongzhou.javafx.stage.DecoratedStage;
import com.github.wanghongzhou.javafx.stage.StageFactory;
import com.github.wanghongzhou.javafx.stage.StageStyle;
import javafx.stage.Stage;

/**
 * @author Brian
 */
public abstract class AbstractStageFactory implements StageFactory {

    @Override
    public DecoratedStage buildDecoratedStage(StageStyle style) {
        return this.buildDecoratedStage(style, new Stage());
    }
}

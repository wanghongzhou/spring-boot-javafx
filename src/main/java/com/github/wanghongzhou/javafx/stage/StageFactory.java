package com.github.wanghongzhou.javafx.stage;

import javafx.stage.Stage;

/**
 * @author Brian
 */
public interface StageFactory {

    DecoratedStage buildDecoratedStage(StageStyle style);

    DecoratedStage buildDecoratedStage(StageStyle style, Stage stage);
}

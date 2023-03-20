package com.github.wanghongzhou.javafx.stage.factory;

import com.github.wanghongzhou.javafx.stage.DecoratedStage;
import com.github.wanghongzhou.javafx.stage.StageStyle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Brian
 */
@Slf4j
public class DefaultStageFactory extends AbstractStageFactory {

    @Override
    public DecoratedStage buildDecoratedStage(StageStyle style, Stage stage) {
        try {
            log.debug("Build stage by style {}", style);
            return style.stageClass().getDeclaredConstructor(Stage.class).newInstance(stage);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            log.error("Build stage fail: ", e);
            throw new RuntimeException(e);
        }
    }
}

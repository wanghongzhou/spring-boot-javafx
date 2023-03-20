package com.github.wanghongzhou.javafx.stage.factory;

import com.github.wanghongzhou.javafx.stage.DecoratedStage;
import com.github.wanghongzhou.javafx.stage.StageStyle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

/**
 * @author Brian
 */
@Slf4j
public class SpringStageFactory extends AbstractStageFactory {

    protected ApplicationContext applicationContext;

    public SpringStageFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public DecoratedStage buildDecoratedStage(StageStyle style, Stage stage) {
        log.debug("Build stage by style {}", style);
        return (DecoratedStage) applicationContext.getBean(style.stageClass().getSimpleName(), stage);
    }
}

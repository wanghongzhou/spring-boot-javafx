package io.github.wanghongzhou.javafx.config;

import io.github.wanghongzhou.javafx.stage.decorated.*;
import io.github.wanghongzhou.javafx.stage.factory.SpringStageFactory;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @author Brian
 */
public class JavaFxConfigSupport {

    @Scope("prototype")
    @Bean("UnadornedStage")
    public UnadornedStage unadornedStage(Stage stage) {
        return new UnadornedStage(stage);
    }

    @Scope("prototype")
    @Bean("ShadowedStage")
    public ShadowedStage shadowedStage(Stage stage) {
        return new ShadowedStage(stage);
    }

    @Scope("prototype")
    @Bean("ResizeableStage")
    public ResizeableStage resizeableStage(Stage stage) {
        return new ResizeableStage(stage);
    }

    @Scope("prototype")
    @Bean("TitledStage")
    public TitledStage titledStage(Stage stage) {
        return new TitledStage(stage);
    }

    @Scope("prototype")
    @Bean("ComplexStage")
    public ComplexStage complexStage(Stage stage) {
        return new ComplexStage(stage);
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringStageFactory stageFactory(ApplicationContext applicationContext) {
        return new SpringStageFactory(applicationContext);
    }
}

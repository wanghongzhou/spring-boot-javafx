package io.github.wanghongzhou.javafx.test.stage.factory;

import io.github.wanghongzhou.javafx.config.JavaFxConfigSupport;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author Brian
 */
@Configuration(proxyBeanMethods = false)
public class JavaFxConfig extends JavaFxConfigSupport {

    @Scope("prototype")
    @Bean("CustomStage")
    public CustomStage customStage(Stage stage) {
        return new CustomStage(stage);
    }
}

package io.github.wanghongzhou.javafx.view.fxml;

import io.github.wanghongzhou.javafx.stage.StageStyle;
import io.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Brian
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLView {

    String[] css() default {};

    String[] icons() default {};

    String value() default "";

    String theme() default "";

    String title() default "";

    String bundle() default "";

    String stageStyle() default "";

    Class<? extends StageStyle> stageStyleClass() default DefaultStageStyle.class;

    double width() default 0;

    double height() default 0;

    boolean maximize() default false;

    boolean resizeable() default true;

    boolean fullscreen() default false;
}

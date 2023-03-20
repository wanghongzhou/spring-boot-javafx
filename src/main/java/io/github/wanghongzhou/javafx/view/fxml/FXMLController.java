package io.github.wanghongzhou.javafx.view.fxml;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Brian
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLController {

    @AliasFor(annotation = Component.class)
    String value() default "";
}

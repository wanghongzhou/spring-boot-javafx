package com.github.wanghongzhou.javafx.stage;

import java.util.Arrays;

/**
 * @author Brian
 */
public interface StageStyle {

    int style();

    String name();

    Class<? extends DecoratedStage> stageClass();

    default boolean is(StageStyle style) {
        return (this.style() & style.style()) > 0;
    }

    static int compose(StageStyle... styles) {
        return Arrays.stream(styles).mapToInt(StageStyle::style).reduce(0, (a, b) -> a | b);
    }
}

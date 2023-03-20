package io.github.wanghongzhou.javafx.stage.style;

import io.github.wanghongzhou.javafx.stage.DecoratedStage;
import io.github.wanghongzhou.javafx.stage.StageStyle;
import io.github.wanghongzhou.javafx.stage.decorated.*;
import lombok.ToString;

/**
 * @author Brian
 */
@ToString
public enum DefaultStageStyle implements StageStyle {

    // javafx style
    DECORATED(UnadornedStage.class, javafx.stage.StageStyle.DECORATED.ordinal()),
    UNDECORATED(UnadornedStage.class, javafx.stage.StageStyle.UNDECORATED.ordinal()),
    TRANSPARENT(UnadornedStage.class, javafx.stage.StageStyle.TRANSPARENT.ordinal()),
    UTILITY(UnadornedStage.class, javafx.stage.StageStyle.UTILITY.ordinal()),
    UNIFIED(UnadornedStage.class, javafx.stage.StageStyle.UNIFIED.ordinal()),

    // custom style
    SHADOWED(ShadowedStage.class, 5),
    RESIZEABLE(ResizeableStage.class, 6),
    TITLED(TitledStage.class, 7),

    // custom combined style
    ICON(ComplexStage.class, 1 << 3),
    CLOSE(ComplexStage.class, 1 << 4),
    MINIMIZE(ComplexStage.class, 1 << 5),
    MAXIMIZE(ComplexStage.class, 1 << 6),
    FULLSCREEN(ComplexStage.class, 1 << 7),
    SIMPLEST(ComplexStage.class, StageStyle.compose(ICON, CLOSE)),
    SIMPLE(ComplexStage.class, StageStyle.compose(SIMPLEST, MINIMIZE)),
    NORMAL(ComplexStage.class, StageStyle.compose(SIMPLE, MAXIMIZE)),
    COMPLEX(ComplexStage.class, StageStyle.compose(NORMAL, FULLSCREEN));

    private final int style;
    private final Class<? extends DecoratedStage> stageClass;

    DefaultStageStyle(Class<? extends DecoratedStage> stageClass, int style) {
        this.style = style;
        this.stageClass = stageClass;
    }

    @Override
    public int style() {
        return this.style;
    }

    @Override
    public Class<? extends DecoratedStage> stageClass() {
        return this.stageClass;
    }
}

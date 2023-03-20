package com.github.wanghongzhou.javafx.test.stage.factory;

import com.github.wanghongzhou.javafx.stage.DecoratedStage;
import com.github.wanghongzhou.javafx.stage.StageStyle;
import com.github.wanghongzhou.javafx.stage.style.DefaultStageStyle;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Brian
 */
@Slf4j
public enum CustomStageStyle implements StageStyle {

    CUSTOM(CustomStage.class, StageStyle.compose(DefaultStageStyle.MINIMIZE, DefaultStageStyle.CLOSE));

    private final int style;
    private final Class<? extends DecoratedStage> stageClass;

    CustomStageStyle(Class<? extends DecoratedStage> stageClass, int style) {
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

package com.github.wanghongzhou.javafx.test.stage.factory;

import com.github.wanghongzhou.javafx.stage.decorated.ComplexStage;
import jakarta.annotation.Resource;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Brian
 */
@Slf4j
public class CustomStage extends ComplexStage {

    @Resource
    private CustomStageService customStageService;

    public CustomStage(Stage stage) {
        super(stage);
    }

    @Override
    public void show() {
        super.show();
        this.titleProperty().set(customStageService.getMessage());
    }
}

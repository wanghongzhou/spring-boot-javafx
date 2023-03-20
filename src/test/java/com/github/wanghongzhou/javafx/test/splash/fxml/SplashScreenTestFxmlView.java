package com.github.wanghongzhou.javafx.test.splash.fxml;

import com.github.wanghongzhou.javafx.splash.SplashScreenFxmlView;
import com.github.wanghongzhou.javafx.view.fxml.FXMLView;

/**
 * @author Brian
 */
@FXMLView(
        value = "/splash/SplashScreenFxmlView.fxml",
        title = "SplashScreen Fxml View",
        stageStyle = "TRANSPARENT"
)
public class SplashScreenTestFxmlView extends SplashScreenFxmlView<SplashScreenTestFxmlController> {
}

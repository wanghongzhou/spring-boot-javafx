module io.github.wanghongzhou.javafx.test {

    requires org.slf4j;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires io.github.wanghongzhou.javafx;
    requires jakarta.annotation;
    requires static lombok;

    opens splash;
    opens io.github.wanghongzhou.javafx.test.splash;
    opens io.github.wanghongzhou.javafx.test.splash.fxml;
    opens io.github.wanghongzhou.javafx.test.stage.decorated;
    opens io.github.wanghongzhou.javafx.test.stage.manager;
    opens io.github.wanghongzhou.javafx.test.stage.factory;
    opens io.github.wanghongzhou.javafx.test.stage.manager.view;

    exports io.github.wanghongzhou.javafx.test.splash;
    exports io.github.wanghongzhou.javafx.test.splash.fxml;
    exports io.github.wanghongzhou.javafx.test.stage.decorated;
    exports io.github.wanghongzhou.javafx.test.stage.manager;
    exports io.github.wanghongzhou.javafx.test.stage.factory;
    exports io.github.wanghongzhou.javafx.test.stage.manager.view;

}
module com.github.wanghongzhou.javafx.test {

    requires org.slf4j;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires com.github.wanghongzhou.javafx;
    requires jakarta.annotation;
    requires static lombok;

    opens splash;
    opens com.github.wanghongzhou.javafx.test.splash;
    opens com.github.wanghongzhou.javafx.test.splash.fxml;
    opens com.github.wanghongzhou.javafx.test.stage.decorated;
    opens com.github.wanghongzhou.javafx.test.stage.manager;
    opens com.github.wanghongzhou.javafx.test.stage.factory;
    opens com.github.wanghongzhou.javafx.test.stage.manager.view;

    exports com.github.wanghongzhou.javafx.test.splash;
    exports com.github.wanghongzhou.javafx.test.splash.fxml;
    exports com.github.wanghongzhou.javafx.test.stage.decorated;
    exports com.github.wanghongzhou.javafx.test.stage.manager;
    exports com.github.wanghongzhou.javafx.test.stage.factory;
    exports com.github.wanghongzhou.javafx.test.stage.manager.view;

}
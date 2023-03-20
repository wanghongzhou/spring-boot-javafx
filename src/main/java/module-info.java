module com.github.wanghongzhou.javafx {

    requires transitive javafx.base;
    requires transitive javafx.fxml;
    requires transitive javafx.web;
    requires transitive javafx.media;
    requires transitive javafx.swing;
    requires transitive javafx.controls;

    requires org.slf4j;
    requires spring.boot;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires com.google.common;
    requires static lombok;

    opens icons;
    opens theme;
    opens com.github.wanghongzhou.javafx.view;
    opens com.github.wanghongzhou.javafx.view.fxml;
    opens com.github.wanghongzhou.javafx.stage;
    opens com.github.wanghongzhou.javafx.splash;
    opens com.github.wanghongzhou.javafx.config;
    opens com.github.wanghongzhou.javafx.application;
    opens com.github.wanghongzhou.javafx.stage.style;
    opens com.github.wanghongzhou.javafx.stage.manager;
    opens com.github.wanghongzhou.javafx.stage.factory;
    opens com.github.wanghongzhou.javafx.stage.decorated;

    exports com.github.wanghongzhou.javafx.view;
    exports com.github.wanghongzhou.javafx.view.fxml;
    exports com.github.wanghongzhou.javafx.stage;
    exports com.github.wanghongzhou.javafx.splash;
    exports com.github.wanghongzhou.javafx.config;
    exports com.github.wanghongzhou.javafx.application;
    exports com.github.wanghongzhou.javafx.stage.style;
    exports com.github.wanghongzhou.javafx.stage.manager;
    exports com.github.wanghongzhou.javafx.stage.factory;
    exports com.github.wanghongzhou.javafx.stage.decorated;
}
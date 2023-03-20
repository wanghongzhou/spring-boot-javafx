module io.github.wanghongzhou.javafx {

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
    opens io.github.wanghongzhou.javafx.view;
    opens io.github.wanghongzhou.javafx.view.fxml;
    opens io.github.wanghongzhou.javafx.stage;
    opens io.github.wanghongzhou.javafx.splash;
    opens io.github.wanghongzhou.javafx.config;
    opens io.github.wanghongzhou.javafx.application;
    opens io.github.wanghongzhou.javafx.stage.style;
    opens io.github.wanghongzhou.javafx.stage.manager;
    opens io.github.wanghongzhou.javafx.stage.factory;
    opens io.github.wanghongzhou.javafx.stage.decorated;

    exports io.github.wanghongzhou.javafx.view;
    exports io.github.wanghongzhou.javafx.view.fxml;
    exports io.github.wanghongzhou.javafx.stage;
    exports io.github.wanghongzhou.javafx.splash;
    exports io.github.wanghongzhou.javafx.config;
    exports io.github.wanghongzhou.javafx.application;
    exports io.github.wanghongzhou.javafx.stage.style;
    exports io.github.wanghongzhou.javafx.stage.manager;
    exports io.github.wanghongzhou.javafx.stage.factory;
    exports io.github.wanghongzhou.javafx.stage.decorated;
}
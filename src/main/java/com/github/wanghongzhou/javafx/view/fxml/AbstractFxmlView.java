package com.github.wanghongzhou.javafx.view.fxml;

import com.github.wanghongzhou.javafx.application.AbstractApplication;
import com.github.wanghongzhou.javafx.stage.StageStyle;
import com.github.wanghongzhou.javafx.view.AbstractView;
import com.google.common.base.CaseFormat;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.AnnotationFormatError;
import java.net.URL;
import java.util.*;

/**
 * @author Brian
 */
@Getter
public abstract class AbstractFxmlView extends AbstractView implements ApplicationContextAware {

    protected FXMLLoader fxmlLoader;
    protected ApplicationContext applicationContext;

    protected final URL resource;
    protected final String theme;
    protected final String[] icons;
    protected final String fxmlRoot;
    protected final FXMLView annotation;
    protected final StageStyle stageStyle;
    protected final ResourceBundle resourceBundle;

    public AbstractFxmlView() {
        this.fxmlRoot = detectFxmlRoot();
        this.annotation = detectAnnotation();
        this.theme = detectTheme(this.annotation);
        this.icons = detectIcons(this.annotation);
        this.stageStyle = detectStageStyle(this.annotation);
        this.resource = detectURLResource(this.fxmlRoot, this.annotation);
        this.resourceBundle = detectResourceBundle(this.fxmlRoot, this.annotation);
    }

    protected String detectFxmlRoot() {
        return "/" + this.getClass().getPackage().getName().replace('.', '/') + "/";
    }

    protected FXMLView detectAnnotation() {
        FXMLView annotation = this.getClass().getAnnotation(FXMLView.class);
        if (Objects.isNull(annotation)) {
            throw new AnnotationFormatError("@FXMLView annotation not detected in " + this.getClass());
        }
        return annotation;
    }

    protected String detectTheme(final FXMLView annotation) {
        if (StringUtils.hasText(annotation.theme())) {
            return Objects.requireNonNull(this.getClass().getResource(annotation.theme())).toExternalForm();
        }
        return null;
    }

    protected String[] detectIcons(final FXMLView annotation) {
        return Arrays.stream(annotation.icons()).map(i -> Objects.requireNonNull(this.getClass().getResource(i)).toExternalForm()).toList().toArray(new String[0]);
    }

    protected StageStyle detectStageStyle(final FXMLView annotation) {
        if (StringUtils.hasText(annotation.stageStyle())) {
            for (StageStyle style : annotation.stageStyleClass().getEnumConstants()) {
                if (style.name().equals(annotation.stageStyle())) {
                    return style;
                }
            }
        }
        return null;
    }

    protected URL detectURLResource(final String fxmlRoot, final FXMLView annotation) {
        if (StringUtils.hasText(annotation.value())) {
            return getClass().getResource(annotation.value());
        } else {
            return getClass().getResource(fxmlRoot + getConventionalName(".fxml"));
        }
    }

    protected ResourceBundle detectResourceBundle(final String fxmlRoot, final FXMLView annotation) {
        if (StringUtils.hasText(annotation.bundle())) {
            return ResourceBundle.getBundle(annotation.bundle());
        } else {
            try {
                return ResourceBundle.getBundle(getClass().getPackage().getName() + "." + getConventionalName());
            } catch (MissingResourceException e) {
                return null;
            }
        }
    }

    private String getConventionalName(String suffix) {
        return getConventionalName() + suffix;
    }

    private String getConventionalName() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, getClass().getSimpleName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Parent getView() {
        if (Objects.isNull(fxmlLoader)) {

            // load fxml
            try {
                this.fxmlLoader = new FXMLLoader(resource, resourceBundle);
                this.fxmlLoader.setControllerFactory(this::controllerFactory);
                this.fxmlLoader.load();
            } catch (final IOException | IllegalStateException e) {
                throw new IllegalStateException("Cannot load " + resource, e);
            }

            Parent parent = fxmlLoader.getRoot();

            // add global css
            if (Objects.nonNull(applicationContext)) {
                List<String> cssList = applicationContext.getEnvironment().getProperty(AbstractApplication.KEY_CSS, List.class);
                if (Objects.nonNull(cssList)) {
                    cssList.forEach(css -> parent.getStylesheets().add(Objects.requireNonNull(getClass().getResource(css)).toExternalForm()));
                }
            }

            // add annotation css
            Arrays.stream(annotation.css()).forEach(css -> parent.getStylesheets().add(Objects.requireNonNull(getClass().getResource(css)).toExternalForm()));

            // add default path css
            final URL uri = getClass().getResource(fxmlRoot + getConventionalName(".css"));
            if (Objects.nonNull(uri)) {
                parent.getStylesheets().add(uri.toExternalForm());
            }
        }
        return fxmlLoader.getRoot();
    }

    protected Object controllerFactory(Class<?> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public String getTitle() {
        if (StringUtils.hasText(annotation.title())) {
            if (Objects.isNull(resourceBundle)) {
                return annotation.title();
            } else {
                return resourceBundle.getString(annotation.title());
            }
        }
        return null;
    }

    @Override
    public String getTheme() {
        return this.theme;
    }

    @Override
    public String[] getIcons() {
        return this.icons;
    }

    @Override
    public StageStyle getStageStyle() {
        return this.stageStyle;
    }

    @Override
    public double getWidth() {
        return this.annotation.width();
    }

    @Override
    public double getHeight() {
        return this.annotation.height();
    }

    @Override
    public boolean isMaximize() {
        return this.annotation.maximize();
    }

    @Override
    public boolean isResizeable() {
        return this.annotation.resizeable();
    }

    @Override
    public boolean isFullscreen() {
        return this.annotation.fullscreen();
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

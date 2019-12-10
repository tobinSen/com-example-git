package com.example.spring.logback;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

//log4j2自定义追加器
@Plugin(name = "MyAbstractAppender", category = "Core", elementType = "appender", printObject = true)
public class MyAbstractAppender extends AbstractAppender {

    public MyAbstractAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent logEvent) {

    }

    @PluginFactory
    public static MyAbstractAppender createAppender(@PluginAttribute("name") String name,
                                                    @PluginAttribute(value = "alias", defaultClass = String.class, defaultString = "wumart-scm-wms") String alias,
                                                    @PluginElement("Filter") final Filter filter,
                                                    @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                    @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("no name defined in conf.");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return new MyAbstractAppender(name, filter, layout, ignoreExceptions);
    }
}

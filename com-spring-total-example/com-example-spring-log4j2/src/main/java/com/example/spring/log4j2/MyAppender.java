package com.example.spring.log4j2;

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

@Plugin(name = "MyAppender", category = "Core", elementType = "appender", printObject = true)
public class MyAppender extends AbstractAppender {

    private volatile static String alias = null;

    public MyAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent logEvent) {
        System.out.println("alias:" + alias);
        System.out.println("message:" + logEvent.getMessage());
        System.out.println("logEvent:" + logEvent.getLevel());
    }

    @PluginFactory
    public static MyAppender createAppender(@PluginAttribute("name") String name,
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
        if (null == MyAppender.alias) {
            synchronized (MyAppender.class) {
                if (null == MyAppender.alias) {
                    MyAppender.alias = alias;
                }
            }
        }
        return new MyAppender(name, filter, layout, ignoreExceptions);
    }
}

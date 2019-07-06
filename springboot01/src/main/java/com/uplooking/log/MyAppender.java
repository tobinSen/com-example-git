package com.uplooking.log;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.Message;

import java.io.Serializable;

@Plugin(name = "myAppender", category = "core", elementType = "appender", printObject = true)
public class MyAppender extends AbstractAppender {

    public MyAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @Override
    public void append(LogEvent logEvent) {
        Message message = logEvent.getMessage();

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

        //return new MyAppender(name, filter, layout, ignoreExceptions);
        return null;
    }
}

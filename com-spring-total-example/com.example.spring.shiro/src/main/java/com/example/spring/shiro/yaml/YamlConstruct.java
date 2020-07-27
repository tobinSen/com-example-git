package com.example.spring.shiro.yaml;

import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;

import java.util.HashMap;
import java.util.Map;

public class YamlConstruct extends Constructor {

    private final Map<Class, Construct> typeConstructs = new HashMap<>();

    public YamlConstruct(Class<?> theRoot) {
        super(theRoot);
    }

    @Override
    protected Construct getConstructor(final Node node) {
        Construct construct = typeConstructs.getOrDefault(node.getType(), super.getConstructor(node));
        typeConstructs.put(node.getType(), construct);
        return construct;
    }

}

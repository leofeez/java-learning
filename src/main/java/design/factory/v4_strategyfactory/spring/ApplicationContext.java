package design.factory.v4_strategyfactory.spring;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leofee
 */
public class ApplicationContext {

    public <T> Map<String, T> getBeansOfType(Class<T> var1) {
        return new HashMap<>();
    }
}

package exercise;

import java.lang.reflect.Proxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Object.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Inspect.class)){
            Class beanClass = bean.getClass();
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        String message = String.format(
                                "Was called method: %s() with arguments: %s",
                                method.getName(),
                                Arrays.toString(args)
                        );
                        String logLevel = bean.getClass().getAnnotation(Inspect.class).level();
                        if (logLevel.equals("info")) {
                            LOGGER.info(message);
                        } else {
                            LOGGER.debug(message);
                        }
                        return method.invoke(bean, args);
                    }
            );
        }
        return bean;
    }
}
// END

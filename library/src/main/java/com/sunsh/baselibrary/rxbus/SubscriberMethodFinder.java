package com.sunsh.baselibrary.rxbus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by  sunsh on 2018/6/21.
 */
public class SubscriberMethodFinder {

    public List<Method> findSubscriberMethods(Class<?> c) {
        List<Method> methods = new ArrayList<>();
        Method[] declaredMethods = c.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            for (Annotation annotation : declaredMethod.getAnnotations()) {
                if (annotation instanceof Subscribe) {
                    Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                    if (parameterTypes.length == 1)
                        methods.add(declaredMethod);
                    else
                        throw new ParameterLimitException("can only one param in subscriberMethod");
                }
            }
        }
        return methods;
    }
}

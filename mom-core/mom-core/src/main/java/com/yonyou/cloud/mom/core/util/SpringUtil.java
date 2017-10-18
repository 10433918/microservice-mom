package com.yonyou.cloud.mom.core.util;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 获取spring容器
 */
@Service
public class SpringUtil implements ApplicationContextAware {

    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @throws org.springframework.beans.BeansException
     */
    public static Object getBean(Class<?> cls) throws BeansException {
        return applicationContext.getBean(cls);
    }

    /**
     * @throws org.springframework.beans.BeansException
     */
    public static Object getBeansOfType(Class<?> type) throws BeansException {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * @throws org.springframework.beans.BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * 获取使用 Proxy.newProxyInstance 生成的代理对象
     *
     * @throws Exception
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T getProxyObject(Object proxy, Class<T> thisClass) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        return (T) h.get(proxy);
    }
}
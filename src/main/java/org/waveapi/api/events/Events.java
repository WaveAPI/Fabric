package org.waveapi.api.events;

import org.waveapi.api.events.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Events {

    public static class EventListener {
        public Method method;
        public Object object;
    }
    public void register(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHandler.class) && !Modifier.isStatic(method.getModifiers())) {
                EventListener listener = new EventListener();
                listener.method = method;
                listener.object = object;
                if (method.getParameterCount() != 1) {
                    throw new RuntimeException("Bad listener " + method.getName());
                }
                Class<?> type = method.getParameterTypes()[0];
                if (!Event.class.isAssignableFrom(type)) {
                    throw new RuntimeException("Bad listener " + method.getName());
                }
                try {
                    Method register = type.getDeclaredMethod("register", EventListener.class);
                    register.invoke(null, listener);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException("Bad Event extending class: " + type.getName(), e);
                }
            }
        }
    }

    public void register(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHandler.class) && Modifier.isStatic(method.getModifiers())) {
                EventListener listener = new EventListener();
                listener.method = method;
                listener.object = null;
                if (method.getParameterCount() != 1) {
                    throw new RuntimeException("Bad listener " + method.getName());
                }
                Class<?> type = method.getParameterTypes()[0];
                if (!Event.class.isAssignableFrom(type)) {
                    throw new RuntimeException("Bad listener " + method.getName());
                }
                try {
                    Method register = type.getDeclaredMethod("register", EventListener.class);
                    register.invoke(null, listener);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException("Bad Event extending class: " + type.getName(), e);
                }
            }
        }
    }
}

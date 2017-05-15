package com.evideo.service.manager;

import java.util.ArrayList;
import java.util.List;

public class NotifyManager {

    private static class NotifyManagerHolder {
        private static final NotifyManager sInstance = new NotifyManager();
    }

    public static NotifyManager getInstance() {
        return NotifyManagerHolder.sInstance;
    }

    private List<ISomethingChangeListener> listenerList = new ArrayList<>();

    public void registerSomethingChangeListener(ISomethingChangeListener listener) {
        if (listener != null) {
            if (!listenerList.contains(listener)) {
                listenerList.add(listener);
            }
        }
    }

    public void unregisterSomethingChangeListener(ISomethingChangeListener listener) {
        if (listener != null) {
            listenerList.remove(listener);
        }
    }

    public void notifyToDoSomething() {
        synchronized (listenerList) {
            for (ISomethingChangeListener listener : listenerList) {
                listener.doSomething();
            }
        }
    }

    public interface ISomethingChangeListener {
        void doSomething();
    }
}

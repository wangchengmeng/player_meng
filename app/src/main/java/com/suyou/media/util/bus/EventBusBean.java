package com.suyou.media.util.bus;

import java.io.Serializable;

/**
 * EventBus传递数据的model
 *
 * @author zou.sq
 */
public class EventBusBean implements Serializable {

    private String eventBusAction;
    private Object eventBusObject;

    public EventBusBean() {
    }

    public EventBusBean(String eventBusAction, Object eventBusObject) {
        this.eventBusAction = eventBusAction;
        this.eventBusObject = eventBusObject;
    }

    public String getEventBusAction() {
        return eventBusAction;
    }

    public void setEventBusAction(String eventBusAction) {
        this.eventBusAction = eventBusAction;
    }

    public Object getEventBusObject() {
        return eventBusObject;
    }

    public void setEventBusObject(Object eventBusObject) {
        this.eventBusObject = eventBusObject;
    }

    @Override
    public String toString() {
        return "EventBusBean{" +
                "eventBusAction='" + eventBusAction + '\'' +
                ", eventBusObject=" + eventBusObject +
                '}';
    }
}

package com.bistral.app.bistral_bistro_service.service.notification.interfaces;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface INotificationDispatcher {

    public SseEmitter registerObserver(String userId);

    public void dispatch(String userId, INotification notification);

}

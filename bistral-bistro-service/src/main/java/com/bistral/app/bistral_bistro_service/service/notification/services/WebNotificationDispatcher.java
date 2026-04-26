package com.bistral.app.bistral_bistro_service.service.notification.services;

import com.bistral.app.bistral_bistro_service.service.notification.interfaces.INotification;
import com.bistral.app.bistral_bistro_service.service.notification.interfaces.INotificationDispatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebNotificationDispatcher implements INotificationDispatcher {

    private final ConcurrentHashMap<String, SseEmitter> activeUser = new ConcurrentHashMap<>();

    @Override
    public SseEmitter registerObserver(String userId) {
        return activeUser.computeIfAbsent(userId, id -> {
            SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
            emitter.onCompletion(() -> activeUser.remove(id));
            emitter.onTimeout(() -> activeUser.remove(id));
            return emitter;
        });
    }

    @Override
    public void dispatch(String userId, INotification notification) {
        SseEmitter emitter = activeUser.get(userId);

        if (emitter != null) {
            synchronized (emitter) {
                try {
                    emitter.send(SseEmitter.event()
                            .name("notification")
                            .data(notification));
                } catch (Exception e) {
                    activeUser.remove(userId);
                }
            }
        }
    }
}

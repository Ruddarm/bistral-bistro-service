package com.bistral.app.bistral_bistro_service.service.notification.services;

import com.bistral.app.bistral_bistro_service.service.notification.interfaces.INotification;
import com.bistral.app.bistral_bistro_service.service.notification.interfaces.INotificationBroker;
import com.bistral.app.bistral_bistro_service.service.notification.interfaces.INotificationDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebNotificationBroker implements INotificationBroker {
    private final INotificationDispatcher notificationDispatcher;

    @Override
    public void saveAndNotify(INotification notification) {
        //
        notificationDispatcher.dispatch(notification.getReceiver(),notification);
    }
}

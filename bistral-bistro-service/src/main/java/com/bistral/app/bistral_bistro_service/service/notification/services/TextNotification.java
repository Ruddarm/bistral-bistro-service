package com.bistral.app.bistral_bistro_service.service.notification.services;

import com.bistral.app.bistral_bistro_service.service.notification.interfaces.INotification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TextNotification implements INotification {
    private String receiver;
    @Getter
    private String msg;
    @Override
    public String getReceiver() {
        return receiver;
    }

}

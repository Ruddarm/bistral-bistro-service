package com.bistral.app.bistral_bistro_service.service.menuItem.service;


import com.bistral.app.bistral_bistro_service.service.notification.interfaces.INotificationBroker;
import com.bistral.app.bistral_bistro_service.service.notification.services.TextNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemExcelImporterAsync {

    private final MenuItemExcelImporter menuItemExcelImporter;

    private final INotificationBroker broker;

    @Async
    public void ImportFromExcel(UUID menuID, MultipartFile multipartFile) {
        menuItemExcelImporter.importItem(menuID, multipartFile);
        broker.saveAndNotify(TextNotification.builder()
                .receiver("123")
                .msg("Your export import completed").build());
    }

}

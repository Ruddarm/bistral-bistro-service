package com.bistral.app.bistral_bistro_service.service.menuItem.interfaces;


import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IMenuItemExcelImporter {

    public Workbook importItem(UUID menuId, MultipartFile multipartFile);
}

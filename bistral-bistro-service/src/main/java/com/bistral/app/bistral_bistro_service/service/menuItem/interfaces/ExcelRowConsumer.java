package com.bistral.app.bistral_bistro_service.service.menuItem.interfaces;

import java.util.List;

public interface ExcelRowConsumer {

    void accept(List<String> values);
    void flush();

}

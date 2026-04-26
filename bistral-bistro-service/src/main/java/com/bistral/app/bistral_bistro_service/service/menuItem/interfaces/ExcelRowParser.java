package com.bistral.app.bistral_bistro_service.service.menuItem.interfaces;

import java.util.List;
import java.util.Optional;

public interface ExcelRowParser<T>{
    Optional<T> parse(List<String> row);
}

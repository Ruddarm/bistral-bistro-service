package com.bistral.app.bistral_bistro_service.service.menuItem.interfaces;

import java.util.List;

public interface BatchInserter<T> {

    void insertBatch(List<T> batch);

}

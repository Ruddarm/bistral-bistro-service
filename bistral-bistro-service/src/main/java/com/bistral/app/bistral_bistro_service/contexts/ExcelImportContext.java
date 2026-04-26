package com.bistral.app.bistral_bistro_service.contexts;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Scope("prototype")
public class ExcelImportContext {
    private final Map<String, UUID> itemCodeToId = new HashMap<>();

    public void registerItem(String code, UUID id) {
        itemCodeToId.put(code, id);
    }

    public UUID getItemId(String code) {
        return itemCodeToId.get(code);
    }
}



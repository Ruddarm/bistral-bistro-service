package com.bistral.app.bistral_bistro_service.entity.enums;

public enum ItemUnit {
    PIECE("piece"),
    PLATE("plate"),
    ML("ml"),
    LITRE("litre"),
    G("g"),
    KG("kg"),
    Glass("glass"),
    Inch("inch");

    private final String displayName;

    ItemUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

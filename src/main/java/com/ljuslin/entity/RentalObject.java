package com.ljuslin.entity;

import java.math.BigDecimal;

public interface RentalObject {
    long getItemId();
    Material getMaterial();
    void setMaterial(Material material);
    String getColor();
    void setColor(String color);
    BigDecimal getPricePerDay();
    void setPricePerDay(BigDecimal pricePerDay);
    boolean isAvailable();
    void setAvailable(boolean available);
    boolean isActive();
    void setActive(boolean active);
    RentalType getRentalType();
}

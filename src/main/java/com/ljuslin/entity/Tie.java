package com.ljuslin.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tie")
public class Tie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tie_id", nullable = false)
    private Long itemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "material", length = 25, nullable = false)
    private Material material;

    @Column(name = "color", nullable = false, length = 20)
    private String color;

    @Column(name = "price_per_day", nullable = false, precision = 6, scale = 2)
    private BigDecimal pricePerDay;

    @Column(name = "available", nullable = false)
    private boolean available;

    @Column(name = "active", nullable = false)
    private boolean active;

    protected Tie() {
    }

    public Tie(Material material, String color, BigDecimal pricePerDay) {
        this.material = material;
        this.color = color;
        this.pricePerDay = pricePerDay;
        this.available = true;
    }

    public long getItemId() {
        return itemId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format("%s slips i %s, pris per dag: %s (%s)",
                color,
                material,
                pricePerDay,
                available ? "tillgänglig" : "ej tillgänglig");
    }
}

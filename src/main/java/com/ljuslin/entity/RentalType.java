package com.ljuslin.entity;

public enum RentalType {
    TIE("Slips"),
    BOWTIE("Fluga"),
    TIECLIP("Slipsnål");

    private final String swedishName;

    RentalType(final String swedishName) {
        this.swedishName = swedishName;
    }

    public String getSwedishName() {
        return this.swedishName;
    }

    @Override
    public String toString() {
        return this.getSwedishName().toLowerCase();
    }
}

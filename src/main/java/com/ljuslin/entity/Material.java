package com.ljuslin.entity;
/**
 * Enum, material
 *
 * @author Tina Ljuslin
 */
public enum Material {
    POLYESTER("Polyester"),
    WOOL("Ull"),
    COTTON("Bomull"),
    SILK("Siden");
    private final String swedishName;

    Material(final String swedishName) {
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

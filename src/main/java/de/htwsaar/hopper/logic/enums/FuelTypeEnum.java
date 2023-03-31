package de.htwsaar.hopper.logic.enums;

/**
 * Enum für die unterschiedlichen Kraftstofftypen.
 * @author Sosthene
 */
public enum FuelTypeEnum {
    DIESEL("Diesel"),
    BENZIN("Benzin"),
    ELEKTRO("Elektro");

    private final String label;

    FuelTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

package com.ljuslin.pricing;

/**
 * Pricing methods for level student
 *
 * @author Tina Ljuslin
 */
public class StudentPricing implements PricePolicy {
    private final int DISCOUNT_PERCENTAGE = 10;

    public StudentPricing() {
    }

    public double getPricePerDay(double price) {
        return price * (100 - DISCOUNT_PERCENTAGE) / 100;
    }

    public double getTotalPrice(double price, int days) {
        return price * days * (100 - DISCOUNT_PERCENTAGE) / 100;
    }
}

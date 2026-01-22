package com.ljuslin.pricing;

/**
 * Pricing methods for level standard
 *
 * @author Tina Ljuslin
 */
public class StandardPricing implements PricePolicy {
    public StandardPricing() {
    }

    public double getPricePerDay(double price) {
        return price;
    }

    public double getTotalPrice(double price, int days) {
        return price * days;
    }

}

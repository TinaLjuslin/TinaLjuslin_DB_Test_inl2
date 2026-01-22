package com.ljuslin.pricing;
/**
 * Interface for prices
 *
 * @author Tina Ljuslin
 */
public interface PricePolicy {
    public double getPricePerDay(double price);
    public double getTotalPrice(double price, int days);

}


package com.ljuslin.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Pricing methods for level student
 *
 * @author Tina Ljuslin
 */
public class StudentPricing implements PricePolicy {
    private final int DISCOUNT_PERCENTAGE = 10;

    public StudentPricing() {
    }

    public BigDecimal getPricePerDay(BigDecimal price) {
        BigDecimal discountFactor = BigDecimal.valueOf(100 - DISCOUNT_PERCENTAGE);
        BigDecimal divisor = BigDecimal.valueOf(100);

        return price.multiply(discountFactor)
                .divide(divisor, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalPrice(BigDecimal price, int days) {
        BigDecimal daysBD = BigDecimal.valueOf(days);
        BigDecimal discountFactor = BigDecimal.valueOf(100 - DISCOUNT_PERCENTAGE);
        BigDecimal divisor = BigDecimal.valueOf(100);

        return price.multiply(daysBD)
                .multiply(discountFactor)
                .divide(divisor, 2, RoundingMode.HALF_UP);   }
}

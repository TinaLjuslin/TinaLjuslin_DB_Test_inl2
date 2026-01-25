package com.ljuslin.pricing;

import java.math.BigDecimal;

/**
 * Interface for prices
 *
 * @author Tina Ljuslin
 */
public interface PricePolicy {
    public BigDecimal getPricePerDay(BigDecimal price);
    public BigDecimal getTotalPrice(BigDecimal price, int days);

}


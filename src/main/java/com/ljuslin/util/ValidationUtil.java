package com.ljuslin.util;

import com.ljuslin.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ValidationUtil {
    private static final DateTimeFormatter STANDARD_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String getNow() {
        return LocalDateTime.now().format(STANDARD_FORMATTER);
    }
    public static LocalDateTime getNowAsLocalDateTime() {
        return LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
    }

    public static BigDecimal checkBigDecimal(String sNumber) {
        try {
            sNumber = sNumber.replace(",", ".");
            BigDecimal bdNumber = new BigDecimal(sNumber);
            if (bdNumber.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException("Vänligen ange ett giltigt pris.");
            }
                return bdNumber;
        } catch (NumberFormatException e) {
            throw new ValidationException("Vänligen ange ett giltigt pris.");
        }
    }
}

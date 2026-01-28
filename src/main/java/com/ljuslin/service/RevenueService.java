package com.ljuslin.service;

import com.ljuslin.entity.RentalObject;
import com.ljuslin.exception.ValidationException;
import com.ljuslin.repo.RentalRepository;

import java.math.BigDecimal;

/**
 * Handles revenues
 *
 * @author Tina Ljuslin
 */
public class RevenueService {

    private RentalRepository rentalRepo;

    public RevenueService() {
    }

    public RevenueService(RentalRepository rentalRepo) {
        this.rentalRepo = rentalRepo;
    }
    public String getTotalRevenue() {
     BigDecimal bdRevenue = rentalRepo.getTotalRevenue();

        if (bdRevenue == null || bdRevenue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Ingen vinst");
        }
        return bdRevenue.toString();
    }
    public String getRevenuePerRentalObject(RentalObject rentalObject) {
        if(rentalObject == null) throw new ValidationException("Välj en vara att visa vinst för.");
        BigDecimal bdRevenue = rentalRepo.getRevenuePerRentalObject(rentalObject.getRentalType(),
                rentalObject.getItemId());
        if (bdRevenue == null || bdRevenue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Ingen vinst");
        }
        return bdRevenue.toString();
    }



/*
    public String getTotalRevenue() {
        List<Rental> rentals = rentalRepo.getRentals();
        double totalRevenue = 0;
        for (Rental rental : rentals) {
            if (rental.getReturnDate() != null) {
                totalRevenue += rental.getTotalRevenue();
            }
        }
        if (totalRevenue == 0) {
            throw new RevenueException("Ingen vinst");
        }
        return String.format("%.2f", totalRevenue);
    }

    public String getRevenuePerItem(Item item) throws DBException, RevenueException {

        List<Rental> rentals = rentalRepo.getRentals();
        double totalRevenue = 0;
        for (Rental rental : rentals) {
            if (rental.getReturnDate() != null
                    && rental.getItem().getItemId() == item.getItemId()) {
                totalRevenue += rental.getTotalRevenue();
            }
        }
        if (totalRevenue == 0) {
            throw new RevenueException("Ingen vinst för denna vara");
        }
        return String.format("%.2f", totalRevenue);
    }*/
}

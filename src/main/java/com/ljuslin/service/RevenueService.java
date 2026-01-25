package com.ljuslin.service;

import com.ljuslin.entity.Rental;
import com.ljuslin.repo.RentalRepository;

import java.util.List;

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

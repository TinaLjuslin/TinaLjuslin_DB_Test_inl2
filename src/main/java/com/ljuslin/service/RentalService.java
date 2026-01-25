package com.ljuslin.service;

import com.ljuslin.entity.*;
import com.ljuslin.pricing.PremiumPricing;
import com.ljuslin.pricing.StandardPricing;
import com.ljuslin.pricing.StudentPricing;
import com.ljuslin.repo.RentalRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalService {
    RentalRepositoryImpl rentalRepository;

    public RentalService(RentalRepositoryImpl rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void newRental(Member member, RentalType type, long itemId) {
        Rental rental = new Rental(member, itemId, type);
        rental.setRentalDate(LocalDate.now());
        rental.setTotalRevenue(BigDecimal.ZERO);
        rentalRepository.save(rental);
    }
    public List<Rental> getRentals() {
        return rentalRepository.getAll();
    }
    /*
    public void endRental(Rental rental) {
        //kolla så rental inte redan har ett return date

        rental.setReturnDate(LocalDate.now());
        int days = (int) ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate());

        BigDecimal price = getTotalPrice(rental.getMember(), rental.get )
    }
    private BigDecimal getPrice(Rental rental) {
        switch (rental.getRentalType()) {
            case BOWTIE:
        }
    }*/
    private BigDecimal getTotalPrice(Member member, BigDecimal pricePerDay, int days) {
        switch (member.getMemberLevel()) {
            case PREMIUM:
                return (new PremiumPricing()).getTotalPrice(pricePerDay, days);
            case STUDENT:
                return (new StudentPricing()).getTotalPrice(pricePerDay, days);
            default:
                return (new StandardPricing()).getTotalPrice(pricePerDay, days);
        }
    }
    public List<Rental> searchRentals(String search) {
    //List<Rental> searchRentals = rentalService.searchRentals(search);
    return null;
    }


}

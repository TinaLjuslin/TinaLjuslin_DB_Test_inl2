package com.ljuslin.service;

import com.ljuslin.entity.*;
import com.ljuslin.exception.ValidationException;
import com.ljuslin.pricing.PremiumPricing;
import com.ljuslin.pricing.StandardPricing;
import com.ljuslin.pricing.StudentPricing;
import com.ljuslin.repo.HistoryRepositoryImpl;
import com.ljuslin.repo.RentalRepositoryImpl;
import com.ljuslin.util.ValidationUtil;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalService {
    private RentalRepositoryImpl rentalRepository;
    private HistoryRepositoryImpl historyRepository;
    private RentalObjectService rentalObjectService;

    public RentalService(RentalRepositoryImpl rentalRepository, HistoryRepositoryImpl historyRepository,
                         RentalObjectService rentalObjectService) {
        this.rentalRepository = rentalRepository;
        this.historyRepository = historyRepository;
        this.rentalObjectService = rentalObjectService;
    }

    public void newRental(Member member, RentalObject rentalObject) {
        Rental rental = new Rental(member, rentalObject.getItemId(),
                rentalObject.getRentalType());
        rental.setRentalDate(ValidationUtil.getNowAsLocalDateTime());
        rental.setTotalRevenue(BigDecimal.ZERO);
        rentalObject.setAvailable(false);
        rentalObjectService.setRentalObjectAvailable(rentalObject, false);
        Rental tempRental = rentalRepository.save(rental);
        History history =
                new History((ValidationUtil.getNow() + ": " + tempRental.toString()
                        + " uthyrning påbörjad."), tempRental.getMember());
        historyRepository.save(history);

    }
    public List<Rental> getRentals() {
        return rentalRepository.getAll();
    }
    public void endRental(Rental rental) {
        if (rental.getReturnDate() != null) {
            throw new ValidationException("Denna vara är redan återlämnad");
        }
        rental.setReturnDate(ValidationUtil.getNowAsLocalDateTime());
        RentalObject rentalObject = rentalObjectService.getRentalObjectById(rental.getRentalType(), rental.getItemId());
        int days = (int) ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate());
        BigDecimal price = getTotalPrice(rental.getMember(), rentalObject.getPricePerDay(), days );
        rental.setTotalRevenue(price);
        rentalRepository.change(rental);
        History history =
                new History((ValidationUtil.getNow() + ": " + rental.toString()
                        + " avslutad."), rental.getMember());
        historyRepository.save(history);

    }

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

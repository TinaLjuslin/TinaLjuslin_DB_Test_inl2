package com.ljuslin.repo;

import com.ljuslin.entity.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    List<Rental> getAll();
    Optional getById(Long id);
    void save(Rental rental);
    Rental change(Rental rental);
    List<Rental> search(String searchText);
    BigDecimal getTotalRevenue();
    BigDecimal getRevenuePerRentalObject(RentalType rentalType, long itemId);
    boolean checkMemberHasActiveRental(Member member);
}

package com.ljuslin.repo;

import com.ljuslin.entity.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    List<Rental> getAll();
    Optional getById(Long id);
    Rental save(Rental rental);
    void change(Rental rental);
    //delete får skötas i servicelagret, sätt active till false och köp update
    //void deleteById(Long id);
    BigDecimal getTotalRevenue();
    BigDecimal getRevenuePerRentalObject(RentalType rentalType, long itemId);

}

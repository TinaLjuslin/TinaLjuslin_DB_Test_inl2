package com.ljuslin.service;

import com.ljuslin.repo.RentalRepository;
import com.ljuslin.repo.RentalRepositoryImpl;

public class RentalService {
    RentalRepositoryImpl rentalRepository;
    public RentalService(RentalRepositoryImpl rentalRepository) {
        this.rentalRepository = rentalRepository;
    }
}

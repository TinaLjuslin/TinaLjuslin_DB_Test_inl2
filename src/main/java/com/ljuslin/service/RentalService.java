package com.ljuslin.service;

import com.ljuslin.entity.*;
import com.ljuslin.repo.RentalRepositoryImpl;

public class RentalService {
    RentalRepositoryImpl rentalRepository;

    public RentalService(RentalRepositoryImpl rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void newRental(Member member, Object item) {
        Rental rental;
        switch (item.getClass().getSimpleName()) {
            case "Bowtie": {
                rental = new Rental(member, ((Bowtie) item).getItemId(), RentalType.BOWTIE);
                break;
            }
            case "PocketSquare": {
                rental = new Rental(member, ((PocketSquare) item).getItemId(), RentalType.POCKET_SQUARE);
                break;
            }
            case "Tie": {
                rental = new Rental(member, ((Tie) item).getItemId(), RentalType.TIE);
                break;
            }
            default: {
                throw new IllegalArgumentException("Okänd vara");
            }
        };
        rentalRepository.save(rental);
    }
}

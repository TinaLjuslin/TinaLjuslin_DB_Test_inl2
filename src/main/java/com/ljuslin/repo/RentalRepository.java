package com.ljuslin.repo;

import com.ljuslin.entity.Material;
import com.ljuslin.entity.PocketSquare;
import com.ljuslin.entity.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    List<Rental> getAll();
    Optional getById(Long id);
    void save(Rental rental);
    void change(Rental rental);
    //delete får skötas i servicelagret, sätt active till false och köp update
    //void deleteById(Long id);


}

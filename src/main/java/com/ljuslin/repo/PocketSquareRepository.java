package com.ljuslin.repo;

import com.ljuslin.entity.Bowtie;
import com.ljuslin.entity.Material;
import com.ljuslin.entity.PocketSquare;

import java.util.List;
import java.util.Optional;

public interface PocketSquareRepository {
    List<PocketSquare> getAll();
    Optional getById(Long id);
    void save(PocketSquare pocketSquare);
    void change(PocketSquare pocketSquare);
    //delete får skötas i servicelagret, sätt active till false och köp update
    //void deleteById(Long id);
    List<PocketSquare> getByMaterial(Material material);

}

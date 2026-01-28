package com.ljuslin.repo;

import com.ljuslin.entity.Bowtie;
import com.ljuslin.entity.Material;

import java.util.List;
import java.util.Optional;

public interface BowtieRepository {
    List<Bowtie> getAll();
    Optional<Bowtie> getById(Long id);
    void save(Bowtie bowtie);
    void change(Bowtie bowtie);
    //delete får skötas i servicelagret, sätt active till false och köp update
    //void delete(Bowtie bowtie);
    List<Bowtie> getByMaterial(Material material);
    List<Bowtie> search(String searchText);
    List<Bowtie> getAllAvailable();

}

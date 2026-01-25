package com.ljuslin.repo;

import com.ljuslin.entity.Bowtie;
import com.ljuslin.entity.Material;
import com.ljuslin.entity.PocketSquare;
import com.ljuslin.entity.Tie;

import java.lang.classfile.Opcode;
import java.util.List;
import java.util.Optional;

public interface TieRepository {
    List<Tie> getAll();
    Optional<Tie> getById(Long id);
    void save(Tie tie);
    void change(Tie tie);
    //delete får skötas i servicelagret, sätt active till false och köp update
    //void deleteById(Long id);
    List<Tie> getByMaterial(Material material);
    List<Tie> search(String searchText);

}

package com.ljuslin.service;

import com.ljuslin.repo.BowtieRepositoryImpl;
import com.ljuslin.repo.PocketSquareRepositoryImpl;
import com.ljuslin.repo.TieRepositoryImpl;

public class ItemService {
    BowtieRepositoryImpl bowtieRepository;
    PocketSquareRepositoryImpl pocketSquareRepository;
    TieRepositoryImpl tieRepository;
    public ItemService() {}

    public ItemService(BowtieRepositoryImpl bowtieRepository,
                       PocketSquareRepositoryImpl pocketSquareRepository,
                       TieRepositoryImpl tieRepository) {
        this.bowtieRepository = bowtieRepository;
        this.pocketSquareRepository = pocketSquareRepository;
        this.tieRepository = tieRepository;
    }

}

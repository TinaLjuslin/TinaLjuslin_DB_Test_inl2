package com.ljuslin.service;

import com.ljuslin.entity.*;
import com.ljuslin.exception.InvalidDataException;
import com.ljuslin.repo.BowtieRepositoryImpl;
import com.ljuslin.repo.PocketSquareRepositoryImpl;
import com.ljuslin.repo.TieRepositoryImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public void newItem(RentalType rentalType, Material material, String color,
                        BigDecimal pricePerDay) {
        checkInput(rentalType, material, color, pricePerDay);
        switch (rentalType) {
            case RentalType.BOWTIE -> {
                Bowtie bowtie = new Bowtie(material, color, pricePerDay);
                bowtieRepository.save(bowtie);
            }
            case RentalType.POCKET_SQUARE -> {
                PocketSquare pocketSquare = new PocketSquare(material, color, pricePerDay);
                pocketSquareRepository.save(pocketSquare);
            }
            case RentalType.TIE -> {
                Tie tie = new Tie(material, color, pricePerDay);
                tieRepository.save(tie);
            }
        }
    }
    public void removeItem(Object item) {
        switch (item.getClass().getSimpleName()) {
            case "Bowtie" -> {
                Bowtie bowtie = (Bowtie) item;
                //kolla om item finns i en rental, annars sätt active till false
            }
            case "PocketSquare" -> {
                PocketSquare pocketSquare = (PocketSquare) item;
                //kolla om item finns i en rental, annars sätt active till false
            }
            case "Tie" -> {
                Tie tie = (Tie) item;
                //kolla om item finns i en rental, annars sätt active till false
            }
        }
    }
    public void changeItem(Object item) {
        switch (item.getClass().getSimpleName()) {
            case "Bowtie" -> {
                Bowtie bowtie = (Bowtie) item;
                checkInput(RentalType.BOWTIE, bowtie.getMaterial(), bowtie.getColor(),
                        bowtie.getPricePerDay());
                bowtieRepository.change(bowtie);
            }
            case "PocketSquare" -> {
                PocketSquare pocketSquare = (PocketSquare) item;
                checkInput(RentalType.POCKET_SQUARE, pocketSquare.getMaterial(),
                        pocketSquare.getColor(), pocketSquare.getPricePerDay());
                pocketSquareRepository.change(pocketSquare);
            }
            case "Tie" -> {
                Tie tie = (Tie) item;
                checkInput(RentalType.TIE, tie.getMaterial(), tie.getColor(), tie.getPricePerDay());
                tieRepository.change(tie);
            }
        }
    }

    private boolean checkInput(RentalType rentalType, Material material, String color,
                           BigDecimal pricePerDay) {
        if (rentalType == null) {
            throw new InvalidDataException("Vänligen ange en typ.");
        }if (material == null) {
            throw new InvalidDataException("Vänligen ange ett material.");
        } else if (color == null || color.isBlank()) {
            throw new InvalidDataException("Vänligen ange en färg.");
        } else if (pricePerDay.doubleValue() <= 0) {
            throw new InvalidDataException("Vänligen ange ett giltigt pris per dag.");
        }
        return true;
    }
    public List<Bowtie> getAllBowties() {
        return bowtieRepository.getAll();
    }
    public List<PocketSquare> getAllPocketSquares() {
        return pocketSquareRepository.getAll();
    }
    public List<Tie> getAllTies() {
        return tieRepository.getAll();
    }
    public List<Object> getAllItems() {
        List<Object> items = new ArrayList<>();
        items.addAll(bowtieRepository.getAll());
        items.addAll(pocketSquareRepository.getAll());
        items.addAll(tieRepository.getAll());
        return items;
    }
    public void changeItemAvailable(Object item, boolean available) {
        switch (item.getClass().getSimpleName()) {
            case "Bowtie" -> {
                ((Bowtie) item).setAvailable(available);
            }
            case "PocketSquare" -> {
                ((PocketSquare) item).setAvailable(available);
            }
            case "Tie" -> {
            ((Tie) item).setAvailable(available);
            }
        }
    }
}

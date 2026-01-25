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

    public ItemService() {
    }

    public ItemService(BowtieRepositoryImpl bowtieRepository,
                       PocketSquareRepositoryImpl pocketSquareRepository,
                       TieRepositoryImpl tieRepository) {
        this.bowtieRepository = bowtieRepository;
        this.pocketSquareRepository = pocketSquareRepository;
        this.tieRepository = tieRepository;
    }

    public void newItem(RentalType rentalType, Material material, String color,
                        String pricePerDay) {
        BigDecimal decimalPrica = checkBigDecimal(pricePerDay);
        checkInput(rentalType, material, color, decimalPrica);
        switch (rentalType) {
            case RentalType.BOWTIE -> {
                Bowtie bowtie = new Bowtie(material, color, decimalPrica);
                bowtie.setActive(true);
                bowtieRepository.save(bowtie);
            }
            case RentalType.POCKET_SQUARE -> {
                PocketSquare pocketSquare = new PocketSquare(material, color, decimalPrica);
                pocketSquare.setActive(true);
                pocketSquareRepository.save(pocketSquare);
            }
            case RentalType.TIE -> {
                Tie tie = new Tie(material, color, decimalPrica);
                tie.setActive(true);
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

    public List<Bowtie> searchBowties(String searchText) {
        if (searchText == null || searchText.isBlank()) {
            throw new InvalidDataException("Vänligen skriv in en söktext");
        }
        return bowtieRepository.search(searchText);
    }

    public List<PocketSquare> searchPocketSquares(String searchText) {
        if (searchText == null || searchText.isBlank()) {
            throw new InvalidDataException("Vänligen skriv in en söktext");
        }
        return pocketSquareRepository.search(searchText);
    }

    public List<Tie> searchTies(String searchText) {

        if (searchText == null || searchText.isBlank()) {
            throw new InvalidDataException("Vänligen skriv in en söktext");
        }
        return tieRepository.search(searchText);
    }

    private BigDecimal checkBigDecimal(String sNumber) {
        try {
            sNumber = sNumber.replace(",", ".");
            return new BigDecimal(sNumber);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Vänligen ange ett giltigt pris per dag.");
        }
    }

    private boolean checkInput(RentalType rentalType, Material material, String color,
                               BigDecimal pricePerDay) {
        if (rentalType == null) {
            throw new InvalidDataException("Vänligen ange en typ.");
        }
        if (material == null) {
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

    public void changeBowtie(Bowtie bowtie, String color, Material material, String sPrice) {
        BigDecimal price = checkBigDecimal(sPrice);
        checkInput(RentalType.BOWTIE, material, color, price);
        //600sdi eller 600etec
        bowtie.setColor(color);
        bowtie.setMaterial(material);
        bowtie.setPricePerDay(price);
        bowtie.setActive(true);
        bowtieRepository.change(bowtie);
    }

    public void changePocketSquare(PocketSquare pocketSquare, String color, Material material,
                                   String sPrice) {
        BigDecimal price = checkBigDecimal(sPrice);
        checkInput(RentalType.POCKET_SQUARE, material, color, price);
        //600sdi eller 600etec
        pocketSquare.setColor(color);
        pocketSquare.setMaterial(material);
        pocketSquare.setPricePerDay(price);
        pocketSquare.setActive(true);
        pocketSquareRepository.change(pocketSquare);
    }

    public void changeTie(Tie tie, String color, Material material, String sPrice) {
        BigDecimal price = checkBigDecimal(sPrice);
        checkInput(RentalType.BOWTIE, material, color, price);
        //600sdi eller 600etec
        tie.setColor(color);
        tie.setMaterial(material);
        tie.setPricePerDay(price);
        tie.setActive(true);
        tieRepository.change(tie);
    }

    public void removeBowtie(Bowtie bowtie) {
        if (bowtie == null) {
            throw new InvalidDataException("Flugan error");
        }
        if (!bowtie.isAvailable()) {
            throw new InvalidDataException("Flugan kan inte tas bort då den är uthyrd");
        }
        bowtie.setActive(false);
    }

    public void removePocketSquare(PocketSquare pocketSquare) {
        if (pocketSquare == null) {
            throw new InvalidDataException("pocketSquare error");
        }
        if (!pocketSquare.isAvailable()) {
            throw new InvalidDataException("Näsduken kan inte tas bort då den är uthyrd");
        }
        pocketSquare.setActive(false);
    }

    public void removeTie(Tie tie) {
        if (tie == null) {
            throw new InvalidDataException("pocketSquare error");
        }
        if (!tie.isAvailable()) {
            throw new InvalidDataException("Näsduken kan inte tas bort då den är uthyrd");
        }
        tie.setActive(false);
    }
    public Bowtie getBowtieById(long id) {
        return bowtieRepository.getById(id).get();
    }

    public Tie getTieById(long id) {
        return tieRepository.getById(id).get();
    }

    public PocketSquare getPocketSquareById(long id) {
        return pocketSquareRepository.getById(id).get();
    }
}

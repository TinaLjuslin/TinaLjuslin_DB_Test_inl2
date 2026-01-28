package com.ljuslin.service;

import com.ljuslin.entity.*;
import com.ljuslin.exception.ValidationException;
import com.ljuslin.repo.BowtieRepositoryImpl;
import com.ljuslin.repo.PocketSquareRepositoryImpl;
import com.ljuslin.repo.TieRepositoryImpl;
import com.ljuslin.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RentalObjectService {
    private BowtieRepositoryImpl bowtieRepository;
    private PocketSquareRepositoryImpl pocketSquareRepository;
    private TieRepositoryImpl tieRepository;

    public RentalObjectService() {
    }

    public RentalObjectService(BowtieRepositoryImpl bowtieRepository,
                               PocketSquareRepositoryImpl pocketSquareRepository,
                               TieRepositoryImpl tieRepository) {
        this.bowtieRepository = bowtieRepository;
        this.pocketSquareRepository = pocketSquareRepository;
        this.tieRepository = tieRepository;
    }

    public void newRentalObject(RentalType rentalType, Material material, String color,
                        String pricePerDay) {
        BigDecimal decimalPrica = ValidationUtil.checkBigDecimal(pricePerDay);
        checkInput(rentalType, color, material, decimalPrica);
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

    public void changeRentalObject(RentalObject rentalObject, String color, Material material,
                                   String sPrice) {
        BigDecimal price = ValidationUtil.checkBigDecimal(sPrice);
        rentalObject.setColor(color);
        rentalObject.setMaterial(material);
        rentalObject.setPricePerDay(price);
        rentalObject.setActive(true);
        if (rentalObject instanceof Bowtie bowtie) {
            checkInput(RentalType.BOWTIE, bowtie.getColor(), bowtie.getMaterial(),
                    bowtie.getPricePerDay());
            bowtieRepository.change(bowtie);
        } else if (rentalObject instanceof PocketSquare pocketSquare) {
            checkInput(RentalType.POCKET_SQUARE, pocketSquare.getColor(), pocketSquare.getMaterial(),
                    pocketSquare.getPricePerDay());
            pocketSquareRepository.change(pocketSquare);
        } else if (rentalObject instanceof Tie tie) {
            checkInput(RentalType.TIE, tie.getColor(), tie.getMaterial(),
                    tie.getPricePerDay());
            tieRepository.change(tie);
        }
    }

    public List<RentalObject> searchRentalObjects(String searchText) {
        if (searchText == null || searchText.isBlank()) {
            throw new ValidationException("Vänligen skriv in en söktext");
        }
        Set<RentalObject> list = new LinkedHashSet<>();
        if (RentalType.BOWTIE.getSwedishName().toLowerCase().contains(searchText.trim().toLowerCase())) {
            list.addAll(bowtieRepository.getAll());
        }
        if (RentalType.POCKET_SQUARE.getSwedishName().toLowerCase().contains(searchText.trim().toLowerCase())) {
            list.addAll(pocketSquareRepository.getAll());
        }
        if (RentalType.TIE.getSwedishName().toLowerCase().contains(searchText.trim().toLowerCase())) {
            list.addAll(tieRepository.getAll());
        }
        list.addAll(bowtieRepository.search(searchText));
        list.addAll(pocketSquareRepository.search(searchText));
        list.addAll(tieRepository.search(searchText));
        return new ArrayList<>(list);
    }


    private boolean checkInput(RentalType rentalType, String color, Material material,
                               BigDecimal pricePerDay) {
        if (rentalType == null) {
            throw new ValidationException("Vänligen ange en typ.");
        }
        if (material == null) {
            throw new ValidationException("Vänligen ange ett material.");
        } else if (color == null || color.isBlank()) {
            throw new ValidationException("Vänligen ange en färg.");
        } else if (pricePerDay.doubleValue() <= 0) {
            throw new ValidationException("Vänligen ange ett giltigt pris per dag.");
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

    public List<RentalObject> getAllRentalObjects() {
        List<RentalObject> items = new ArrayList<>();
        items.addAll(bowtieRepository.getAll());
        items.addAll(pocketSquareRepository.getAll());
        items.addAll(tieRepository.getAll());
        return items;
    }

    public void removeRentalObject(RentalObject rentalObject) {
        if (rentalObject == null) {
            throw new ValidationException("Välj en vara att ta bort.");
        }
        if (!rentalObject.isAvailable()) {
            throw new ValidationException("Varan kan inte tas bort då den är uthyrd");
        }
        rentalObject.setActive(false);
        if (rentalObject instanceof Bowtie bowtie) {
            bowtieRepository.change(bowtie);
        } else if (rentalObject instanceof PocketSquare pocketSquare) {
            pocketSquareRepository.change(pocketSquare);
        } else if (rentalObject instanceof Tie tie) {
            tieRepository.change(tie);
        }
    }
    public List<RentalObject> getAllAvailableRentalObjects() {
        List<RentalObject> availableRentalObjects = new ArrayList<>();
        availableRentalObjects.addAll(bowtieRepository.getAllAvailable());
        availableRentalObjects.addAll(pocketSquareRepository.getAllAvailable());
        availableRentalObjects.addAll(tieRepository.getAllAvailable());
        return availableRentalObjects;
    }
public void setRentalObjectAvailable(RentalObject rentalObject, boolean available) {
        rentalObject.setAvailable(available);
        if (rentalObject instanceof Bowtie) {
            bowtieRepository.change((Bowtie)rentalObject);
        } else if (rentalObject instanceof PocketSquare) {
            pocketSquareRepository.change((PocketSquare)rentalObject);
        } else if (rentalObject instanceof Tie) {
            tieRepository.change((Tie)rentalObject);
        }
}
    public RentalObject getRentalObjectById(RentalType type, long id) {
        switch (type) {
            case BOWTIE -> {
                return bowtieRepository.getById(id).get();
            }
            case POCKET_SQUARE -> {
                return pocketSquareRepository.getById(id).get();
            }
            case TIE -> {
                return tieRepository.getById(id).get();
            }
        }
        //throw nånting istället? type finns inte?
        return null;
    }
}

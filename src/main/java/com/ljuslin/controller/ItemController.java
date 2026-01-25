package com.ljuslin.controller;

import com.ljuslin.entity.*;
import com.ljuslin.exception.DatabaseException;
import com.ljuslin.service.ItemService;
import com.ljuslin.view.ChangeItemView;
import com.ljuslin.view.ItemView;
import com.ljuslin.view.NewItemView;
import com.ljuslin.view.SearchItemView;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlls all Views for Items and calls the right service to perform operations
 *
 * @author Tina Ljuslin
 */
public class ItemController {
    private ItemService itemService;
    private ItemView itemView;
    private NewItemView newTieView;
    private ChangeItemView changeItemView;
    private SearchItemView searchItemView;
    private Stage stage;
    private Scene scene;

    public ItemController() {
    }

    public ItemController(ItemService itemService, ItemView itemView) {
        this.itemService = itemService;
        this.itemView = itemView;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Tab getTab() {
        return itemView.getTab();
    }

    public void populateTable() {
        try {
            itemView.populateTable();
        } catch (DatabaseException e) {
            itemView.showInfoAlert(e.getMessage());
        }
    }
    public List<Bowtie> getAllBowties() {
        return itemService.getAllBowties();
    }

    public List<PocketSquare> getAllPocketSquares() {
        return itemService.getAllPocketSquares();
    }
    public List<Tie> getAllTies() {
        return itemService.getAllTies();
    }
    public void newItem(RentalType rentalType, Material material, String color,
                         String pricePerDay) {
        itemService.newItem(rentalType, material, color, pricePerDay);
    }

    public void newItemView() {
        newTieView = new NewItemView(this);
        newTieView.showPopUp(stage, scene);
    }
/*
    public List<Item> getAllItems() {
        return itemService.getItems();
    }

    public List<Item> getAllAvailableItems()  {
        return itemService.getAvailableItems();
    }

    public void newItemView() {
        newTieView = new NewItemView(this);
        newTieView.showPopUp(stage, scene);
    }

    public void removeItem(Item item) throws DBException, ItemException {
        itemService.removeItem(item);
    }

    public void changeItemView(Item item) {
        changeItemView = new ChangeItemView(this);
        changeItemView.showPopUp(stage, scene, item);
    }

    public void changeItem(Item item, String brand, String color, Material material,
                           Pattern pattern,
                           String sPricePerDay, String sWidth, String sLength) throws DBException, ItemException {
        itemService.changeItem(item, brand, color, material, pattern,
                sPricePerDay, sWidth, sLength);
    }

    public void changeItem(Item item, String brand, String color, Material material,
                           Pattern pattern,
                           String sPricePerDay, String sSize, boolean preTied) throws DBException,
            ItemException {
        itemService.changeItem(item, brand, color, material, pattern, sPricePerDay, sSize, preTied);
    }
*/
public void changeBowtieView(Bowtie bowtie) {
    changeItemView = new ChangeItemView(this);
    changeItemView.showBowtiePopUp(stage, scene, bowtie);
}
    public void changePocketSquareView(PocketSquare pocketSquare) {
        changeItemView = new ChangeItemView(this);
        changeItemView.showPocketSquarePopUp(stage, scene, pocketSquare);
    }public void changeTieView(Tie tie) {
        changeItemView = new ChangeItemView(this);
        changeItemView.showTiePopUp(stage, scene, tie);
    }
    public void changeBowtie(Bowtie bowtie, String color, Material material, String price){
        itemService.changeBowtie(bowtie, color, material, price);
    }
    public void changePocketSquare(PocketSquare pocketSquare, String color, Material material,
                          String price){
        itemService.changePocketSquare(pocketSquare, color, material, price);
    }
    public void changeTie(Tie tie, String color, Material material, String price){
        itemService.changeTie(tie, color, material, price);
    }
    public void removeBowtie(Bowtie bowtie) {}
    public void removePocketSquare(PocketSquare pocketSquare) {}
    public void removeTie(Tie tie) {}
    public void searchItemView(RentalType type) {
        searchItemView = new SearchItemView(this);
        searchItemView.showPopUp(stage, scene, type);
    }
    public void searchItem(RentalType type, String search) {
        switch (type) {
            case BOWTIE -> {
                List<Bowtie> searchItems = itemService.searchBowties(search);
                itemView.showBowtieTable(searchItems);
            }
            case POCKET_SQUARE -> {
                List<PocketSquare> searchItems = itemService.searchPocketSquares(search);
                itemView.showPocketSquareTable(searchItems);}
            case TIE -> {
                List<Tie> searchItems = itemService.searchTies(search);
                itemView.showTieTable(searchItems);}
        }
    }
    public Bowtie getBowtieById(long id) {
        return itemService.getBowtieById(id);
    }
    public Tie getTieById(long id) {
        return itemService.getTieById(id);
    }
    public PocketSquare getPocketSquareById(long id) {
        return itemService.getPocketSquareById(id);
    }
}

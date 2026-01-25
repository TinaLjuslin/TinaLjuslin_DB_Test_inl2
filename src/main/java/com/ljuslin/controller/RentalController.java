package com.ljuslin.controller;

import com.ljuslin.entity.Bowtie;
import com.ljuslin.entity.RentalType;
import com.ljuslin.exception.DatabaseException;

import com.ljuslin.entity.Member;
import com.ljuslin.entity.Rental;
import com.ljuslin.service.RentalService;
import com.ljuslin.view.NewRentalView;
import com.ljuslin.view.RentalView;
import com.ljuslin.view.SearchRentalView;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controlls all Views for Rentals and calls the right service to perform operations
 *
 * @author Tina Ljuslin
 */
public class RentalController {
    private ItemController itemController;
    private MemberController memberController;
    private RentalService rentalService;
    private RentalView rentalView;
    private NewRentalView newRentalView;
    private SearchRentalView searchRentalView;
    private Stage stage;
    private Scene scene;

    public RentalController() {
    }

    public RentalController(RentalService rentalService,
                            MemberController memberController,
                            ItemController itemController, RentalView rentalView) {
        this.rentalService = rentalService;
        this.rentalView = rentalView;
        this.memberController = memberController;
        this.itemController = itemController;
        newRentalView = new NewRentalView(memberController, itemController);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Tab getTab() {
        return rentalView.getTab();
    }

    public void populateTable() {
        try {
            rentalView.populateTable(rentalService.getRentals());
        } catch (DatabaseException e) {
            rentalView.showInfoAlert(e.getMessage());
        }
    }

    public List<Rental> getAllRentals() {
        return rentalService.getRentals();
    }
    public void newRental(RentalType type, long itemId ) {
        Member member = newRentalView.showMemberPopUp(stage);
        rentalService.newRental(member, type, itemId);

    }
    public void searchRentalView() {
        searchRentalView = new SearchRentalView(this);
        searchRentalView.showPopUp(stage, scene);
    }
    public void searchRental(String search) {
        List<Rental> searchRentals = rentalService.searchRentals(search);
        rentalView.populateTable(searchRentals);
    }
/*
    public double getRevenuePerRental(Rental rental) {
        return rentalService.getRevenuePerRental(rental);
    }

    public void newRental() {
        Member member = newRentalView.showMemberPopUp(stage);
        Item item = newRentalView.showAvailableItemPopUp(stage);
        rentalService.newRental(member, item);
    }

    public void endRental(Rental rental) {
        rentalService.endRental(rental);
    }

    public void newRental(Member member) {
        *//*Item item = newRentalView.showAvailableItemPopUp(stage);
        rentalService.newRental(member, item);*//*
    }

    public void newRental() {
        Member member = newRentalView.showMemberPopUp(stage);
        rentalService.newRental(member, item);
    }

    public void searchRentalView() {
        searchRentalView = new SearchRentalView(this);
        searchRentalView.showPopUp(stage, scene);
    }

    public void searchRental(String search) {
        List<Rental> searchRentals = rentalService.searchRentals(search);
        rentalView.populateTable(searchRentals);
    }*/
}

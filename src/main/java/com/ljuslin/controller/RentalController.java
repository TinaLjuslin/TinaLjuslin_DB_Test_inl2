package com.ljuslin.controller;

import com.ljuslin.entity.*;
import com.ljuslin.exception.DatabaseException;

import com.ljuslin.exception.LjuslinException;
import com.ljuslin.service.RentalService;
import com.ljuslin.view.*;
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
    private RentalObjectController rentalObjectController;
    private MemberController memberController;
    private RentalService rentalService;
    private RentalView rentalView;
    private NewRentalView newRentalView;
    private MemberView memberView;
    private SearchRentalView searchRentalView;
    private Stage stage;
    private Scene scene;

    public RentalController() {
    }

    public RentalController(RentalService rentalService,
                            MemberController memberController,
                            RentalObjectController rentalObjectController, RentalView rentalView) {
        this.rentalService = rentalService;
        this.rentalView = rentalView;
        this.memberController = memberController;
        this.rentalObjectController = rentalObjectController;
        this.newRentalView = new NewRentalView(memberController, rentalObjectController);
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
        } catch (LjuslinException e) {
            rentalView.showInfoAlert(e.getMessage());
        } catch (Exception e) {
            rentalView.showErrorAlert(e.getMessage());
        }
    }

    public List<Rental> getAllRentals(View view) {
        try {
            return rentalService.getRentals();
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return List.of();
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return List.of();
        }
    }

    public void newRental(RentalObject rentalObject, View view) {
        try {
            Member member = newRentalView.showMemberPopUp(stage);
            if (member != null && rentalObject != null) {

                rentalService.newRental(member, rentalObject);
            }
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
        }
    }

    public void newRental(Member member, View view) {
        try {

            RentalObject rentalObject = newRentalView.showAvailableRentalObjectsPopUp(stage);
            if (member != null && rentalObject != null) {

                rentalService.newRental(member, rentalObject);
            }
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
        }
    }

    public void newRental(View view) {
        try {
            Member member = newRentalView.showMemberPopUp(stage);
            if (member != null) {
                RentalObject rentalObject = newRentalView.showAvailableRentalObjectsPopUp(stage);

                if (rentalObject != null) {
                    rentalService.newRental(member, rentalObject);
                }
            }
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
        }
    }

    public void searchRentalView() {
        searchRentalView = new SearchRentalView(this);
        searchRentalView.showPopUp(stage, scene);
    }

    public boolean searchRental(String search, View view) {
        try {
            List<Rental> searchRentals = rentalService.searchRentals(search);
            rentalView.populateTable(searchRentals);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }

    public boolean endRental(Rental rental, View view) {
        try {
            rentalService.endRental(rental);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }
}

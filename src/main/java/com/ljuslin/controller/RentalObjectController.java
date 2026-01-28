package com.ljuslin.controller;

import com.ljuslin.entity.*;
import com.ljuslin.exception.LjuslinException;
import com.ljuslin.service.RentalObjectService;
import com.ljuslin.view.*;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controlls all Views for rental objects and calls the right service to perform operations
 *
 * @author Tina Ljuslin
 */
public class RentalObjectController {
    private RentalObjectService rentalObjectService;
    private RentalObjectView rentalObjectView;
    private NewRentalObjectView newRentalObjectView;
    private ChangeRentalObjectView changeRentalObjectView;
    private SearchRentalObjectView searchRentalObjectView;
    private Stage stage;
    private Scene scene;

    public RentalObjectController() {
    }

    public RentalObjectController(RentalObjectService rentalObjectService,
                                  RentalObjectView rentalObjectView) {
        this.rentalObjectService = rentalObjectService;
        this.rentalObjectView = rentalObjectView;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Tab getTab() {
        return rentalObjectView.getTab();
    }

    public void populateTable() {
        try {
            rentalObjectView.populateTable();
        } catch (LjuslinException e) {
            rentalObjectView.showInfoAlert(e.getMessage());
        } catch (Exception e) {
            rentalObjectView.showErrorAlert(e.getMessage());
        }
    }

    public boolean newRentalObject(RentalType rentalType, Material material, String color,
                        String pricePerDay, View view) {
        try {
            rentalObjectService.newRentalObject(rentalType, material, color, pricePerDay);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }

    public void newRentalObjectView() {
        newRentalObjectView = new NewRentalObjectView(this);
        newRentalObjectView.showPopUp(stage, scene);
    }

    public List<RentalObject> getAllRentalObjects(View view) {
        try {
            return rentalObjectService.getAllRentalObjects();
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return List.of();
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return List.of();
        }
    }

    public List<RentalObject> getAllAvailableRentalObjects(View view) {
        try {
            return rentalObjectService.getAllAvailableRentalObjects();
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return List.of();
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return List.of();
        }
    }

    public void changeRentalObjectView(RentalObject rentalObject) {
        changeRentalObjectView = new ChangeRentalObjectView(this);
        changeRentalObjectView.showPopUp(stage, scene, rentalObject);
    }

    public boolean changeRentalObject(RentalObject rentalObject, String color, Material material,
                                   String price, View view) {
        try {
            rentalObjectService.changeRentalObject(rentalObject, color, material, price);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }

    public boolean removeRentalObject(RentalObject rentalObject, View view) {
        try {
            rentalObjectService.removeRentalObject(rentalObject);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }

    public void searchRentalObjectView() {
        searchRentalObjectView = new SearchRentalObjectView(this);
        searchRentalObjectView.showPopUp(stage, scene);
    }

    public boolean searchRentalObject(String search, View view) {
        try {
            List<RentalObject> searchItems = rentalObjectService.searchRentalObjects(search);
            rentalObjectView.populateTable(searchItems);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }

    public RentalObject getRentalObjectById(RentalType type, long id, View view) {
        try {
            return rentalObjectService.getRentalObjectById(type, id);
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return null;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return null;
        }
    }
}

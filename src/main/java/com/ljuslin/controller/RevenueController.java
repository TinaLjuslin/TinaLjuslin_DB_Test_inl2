package com.ljuslin.controller;

import com.ljuslin.entity.RentalObject;
import com.ljuslin.exception.LjuslinException;
import com.ljuslin.service.RevenueService;
import com.ljuslin.view.NewRentalView;
import com.ljuslin.view.RevenueView;
import com.ljuslin.view.View;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

/**
 * Controlls all Views for Revenue and calls the right service to perform operations
 *
 * @author Tina Ljuslin
 */
public class RevenueController {
    private MemberController memberController;
    private RentalObjectController rentalObjectController;
    private RevenueService revenueService;
    private RevenueView revenueView;
    private NewRentalView newRentalView;
    private Stage stage;
    private Scene scene;

    public RevenueController() {
    }

    public RevenueController(RevenueService revenueService, MemberController memberController,
                             RentalObjectController rentalObjectController, RevenueView revenueView) {
        this.revenueService = revenueService;
        this.revenueView = revenueView;
        newRentalView = new NewRentalView(memberController, rentalObjectController);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Tab getTab() {
        return revenueView.getTab();
    }

    public void populateTable() {
        try {
            revenueView.updateTotalRevenue();
        } catch (LjuslinException e) {
            revenueView.showInfoAlert(e.getMessage());
        } catch (Exception e) {
            revenueView.showErrorAlert(e.getMessage());
        }
    }

    public String getTotalRevenue(View view) {
        try {
            return revenueService.getTotalRevenue();
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return null;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return null;
        }
    }

    public String getRevenuePerItem(RentalObject rentalObject, View view) {
        try {
            return revenueService.getRevenuePerRentalObject(rentalObject);
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return null;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return null;
        }
    }

    public RentalObject getRentalObjectForRevenue(View view) {
        try {
            return newRentalView.showRentalObjectsPopUp(stage);
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return null;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return null;
        }
    }
}

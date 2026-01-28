package com.ljuslin.view;

import com.ljuslin.controller.RevenueController;
import com.ljuslin.entity.RentalObject;
import com.ljuslin.exception.DatabaseException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;

/**
 * Creates revenues tab
 *
 * @author Tina Ljuslin
 */
public class RevenueView extends View implements TabView {
    private RevenueController revenueController;
    private Tab tab;
    private BorderPane pane;
    private VBox vBox;
    private HBox hBox;

    private Button searchItemRevenueButton;
    private Button totalRevenueButton;
    private Button exitButton;
    private Region region;
    private Label totalRevenueLabel;

    public RevenueView() {
    }

    public void setRevenueController(RevenueController revenueController) {
        this.revenueController = revenueController;
    }

    public Tab getTab() {
        tab = new Tab("Ekonomi");
        pane = new BorderPane();
        vBox = new VBox();
        hBox = new HBox();
        totalRevenueButton = new Button("Total vinst");
        searchItemRevenueButton = new Button("Vinst per vara");
        totalRevenueButton.setMaxWidth(Double.MAX_VALUE);
        searchItemRevenueButton.setMaxWidth(Double.MAX_VALUE);
        exitButton = new Button("Avsluta");
        region = new Region();
        totalRevenueLabel = new Label("Total vinst: ");

        vBox.getChildren().addAll(totalRevenueButton, searchItemRevenueButton, region, exitButton);
        hBox.getChildren().addAll(totalRevenueLabel);
        VBox.setVgrow(region, Priority.ALWAYS);

        totalRevenueButton.setOnAction(ae -> {
            updateTotalRevenue();
        });
        searchItemRevenueButton.setOnAction(ae -> {
            updateTotalRevenuePerRentalObject();
        });
        exitButton.setOnAction(ae -> {
            System.exit(0);
        });

        pane.setLeft(vBox);
        pane.setCenter(hBox);
        tab.setContent(pane);
        return tab;
    }

    public void updateTotalRevenue() {
        String sRevenue = revenueController.getTotalRevenue(this);
        if (sRevenue != null) {
            totalRevenueLabel.setText("Total vinst: " + sRevenue + "kr");
        }
    }

    public void updateTotalRevenuePerRentalObject() {
        RentalObject rentalObject = revenueController.getRentalObjectForRevenue(this);
        String sRevenue = revenueController.getRevenuePerItem(rentalObject, this);
        if (sRevenue != null) {
            totalRevenueLabel.setText(rentalObject.toString() + "\nVinst: "
                    + sRevenue + "kr");
        } else {
            updateTotalRevenue();
        }
    }
}

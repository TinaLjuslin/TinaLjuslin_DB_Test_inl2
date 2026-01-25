package com.ljuslin.view;

import com.ljuslin.controller.ItemController;
import com.ljuslin.controller.RentalController;
import com.ljuslin.entity.*;
import com.ljuslin.exception.DatabaseException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

/**
 * Creates rental tab
 *
 * @author Tina Ljuslin
 */
public class RentalView extends View implements TabView {
    private RentalController rentalController;
    private ItemController itemController;
    private Tab tab;
    private BorderPane pane;
    private VBox vbox;
    private Button newRentalButton;
    private Button endRentalButton;
    private Button searchButton;
    private Button rechargeButton;
    private Button exitButton;
    private Region region;
    private TableView<Rental> table;
    private TableColumn<Rental, String> memberColumn;
    private TableColumn<Rental, String> itemColumn;
    private TableColumn<Rental, LocalDate> rentalDateColumn;
    private TableColumn<Rental, LocalDate> endRentalDateColumn;
    private TableColumn<Rental, Double> totalRevenueColumn;

    public RentalView() {
    }

    public void setRentalController(RentalController rentalController, ItemController itemController) {
        this.rentalController = rentalController;
        this.itemController = itemController;
    }

    public Tab getTab() {
        tab = new Tab("Uthyrningar");
        vbox = new VBox();
        pane = new BorderPane();

        newRentalButton = new Button("Ny uthyrning");
        searchButton = new Button("Sök uthyrning");
        endRentalButton = new Button("Avsluta uthyrning");
        rechargeButton = new Button("Ladda om");
        newRentalButton.setMaxWidth(Double.MAX_VALUE);
        searchButton.setMaxWidth(Double.MAX_VALUE);
        endRentalButton.setMaxWidth(Double.MAX_VALUE);
        rechargeButton.setMaxWidth(Double.MAX_VALUE);
        exitButton = new Button("Avsluta");
        region = new Region();

        vbox.getChildren().addAll(newRentalButton, searchButton, endRentalButton, rechargeButton,
                region, exitButton);
        table = new TableView<>();
        table.setEditable(false);
        memberColumn = new TableColumn<>("Namn");
        memberColumn.setCellValueFactory(features -> {
            Rental rental = features.getValue();
            Member member = rental.getMember();
            String sMember = member.toString();
            return new ReadOnlyStringWrapper(sMember);
        });
        itemColumn = new TableColumn<>("Vara");
        itemColumn.setCellValueFactory(features -> {
            Rental rental = features.getValue();
            RentalType type = rental.getRentalType();
            switch (type) {
                case BOWTIE -> {
                    Bowtie bowtie = itemController.getBowtieById(rental.getItemId());
                    return new ReadOnlyStringWrapper(bowtie.toString());
                }
                case POCKET_SQUARE -> {
                    PocketSquare pocketSquare =
                            itemController.getPocketSquareById(rental.getItemId());
                    return new ReadOnlyStringWrapper(pocketSquare.toString());
                }
                case TIE -> {
                    Tie tie = itemController.getTieById(rental.getItemId());
                    return new ReadOnlyStringWrapper(tie.toString());}
            }
            return new ReadOnlyStringWrapper("FEL");
        });
        rentalDateColumn = new TableColumn<>("Uthyrningdatum");
        rentalDateColumn.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
        endRentalDateColumn = new TableColumn<>("Avslutningsdatum");
        endRentalDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        totalRevenueColumn = new TableColumn<>("Vinst");
        totalRevenueColumn.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));

        table.getColumns().addAll(memberColumn, itemColumn, rentalDateColumn, endRentalDateColumn,
                totalRevenueColumn);
        VBox.setVgrow(region, Priority.ALWAYS);
        populateTable();
        pane.setLeft(vbox);
        pane.setCenter(table);
        tab.setContent(pane);
        newRentalButton.setOnAction(ae -> {
          //här skulle item-tabben kunna visas istället för popup
            /*  try {
                rentalController.newRental();
            } catch (DatabaseException e) {
                showInfoAlert(e.getMessage());
            } catch (Exception e) {
                showErrorAlert(e.getMessage());
            }
            populateTable();*/
        });
        endRentalButton.setOnAction(ae -> {
            Rental rental = table.getSelectionModel().getSelectedItem();
            if (rental != null) {
                try {
                  //  rentalController.endRental(rental);
                    //double totalCost = rentalController.getRevenuePerRental(rental);
                   // showInfoAlert("Item återlämnad, total kostnad " + String.format("%.2f",
                   //         totalCost) + "kr");
                    populateTable();
                } catch (DatabaseException e) {
                    showInfoAlert(e.getMessage());
                } catch (Exception e) {
                    showErrorAlert(e.getMessage());
                }
            } else {
                showInfoAlert("Välj en uthyrning att avsluta!");
            }
        });
        searchButton.setOnAction(ae -> {
            rentalController.searchRentalView();
        });
        rechargeButton.setOnAction(ae -> {
            populateTable();
        });
        exitButton.setOnAction(ae -> {
            System.exit(0);
        });
        return tab;
    }

    private void populateTable() {
        try {
            List<Rental> list = rentalController.getAllRentals();
            ObservableList<Rental> observableList = FXCollections.observableList(list);
            table.setItems(observableList);
        } catch (DatabaseException e) {
            showInfoAlert(e.getMessage());
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    public void populateTable(List<Rental> rentals) {
        ObservableList<Rental> observableList = FXCollections.observableList(rentals);
        table.setItems(observableList);
    }
}

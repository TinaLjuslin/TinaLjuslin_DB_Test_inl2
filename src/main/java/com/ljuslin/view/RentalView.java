package com.ljuslin.view;

import com.ljuslin.controller.RentalObjectController;
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
    private RentalObjectController rentalObjectController;
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

    public void setRentalController(RentalController rentalController, RentalObjectController rentalObjectController) {
        this.rentalController = rentalController;
        this.rentalObjectController = rentalObjectController;
    }

    public Tab getTab() {
        tab = new Tab("Uthyrningar");
        vbox = new VBox();
        pane = new BorderPane();

        newRentalButton = new Button("Ny");
        searchButton = new Button("Sök");
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
            RentalObject rentalObject = rentalObjectController.getRentalObjectById(type,
                    rental.getItemId(), this);
            return new ReadOnlyStringWrapper(rentalObject.toString());
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

            rentalController.newRental(this);

            populateTable();
        });
        endRentalButton.disableProperty().bind(
                table.getSelectionModel().selectedItemProperty().isNull()
        );
        endRentalButton.setOnAction(ae -> {
            Rental rental = table.getSelectionModel().getSelectedItem();
            if (rental != null) {
                if (rentalController.endRental(rental, this)) {
                    showInfoAlert("Item återlämnad, total kostnad " + String.format("%.2f",
                            rental.getTotalRevenue()) + "kr");
                    populateTable();
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
        List<Rental> list = rentalController.getAllRentals(this);
        ObservableList<Rental> observableList = FXCollections.observableList(list);
        table.setItems(observableList);

    }

    public void populateTable(List<Rental> rentals) {
        ObservableList<Rental> observableList = FXCollections.observableList(rentals);
        table.setItems(observableList);
    }
}

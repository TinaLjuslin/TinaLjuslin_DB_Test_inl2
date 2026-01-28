package com.ljuslin.view;

import com.ljuslin.controller.RentalObjectController;
import com.ljuslin.controller.RentalController;
import com.ljuslin.entity.*;
import com.ljuslin.exception.DatabaseException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.util.List;

/**
 * Creates the item tab
 *
 * @author Tina Ljuslin
 */
public class RentalObjectView extends View implements TabView {
    private RentalObjectController rentalObjectController;
    private RentalController rentalController;
    private Tab tab;
    private BorderPane pane;
    private VBox vbox;
    private Button newItemButton;
    private Button searchButton;
    private Button changeButton;
    private Button deleteButton;
    private Button newRentalButton;
    private Button rechargeButton;
    private Button exitButton;
    private Region region;
    private TableView<RentalObject> rentalObjectTable;
    private TableColumn<RentalObject, String> typeColumn;
    private TableColumn<RentalObject, Material> materialColumn;
    private TableColumn<RentalObject, String> colorColumn;
    private TableColumn<RentalObject, BigDecimal> priceColumn;
    private TableColumn<RentalObject, Boolean> availableColumn;

    public RentalObjectView() {
    }

    public void setItemController(RentalObjectController rentalObjectController
            , RentalController rentalController) {
        this.rentalObjectController = rentalObjectController;
        this.rentalController = rentalController;
    }

    public Tab getTab() {
        tab = new Tab("Varor");
        pane = new BorderPane();
        vbox = new VBox();
        newItemButton = new Button("Ny");
        searchButton = new Button("Sök");
        changeButton = new Button("Ändra");
        deleteButton = new Button("Ta bort");
        newRentalButton = new Button("Ny uthyrning");
        rechargeButton = new Button("Ladda om");
        newItemButton.setMaxWidth(Double.MAX_VALUE);
        searchButton.setMaxWidth(Double.MAX_VALUE);
        changeButton.setMaxWidth(Double.MAX_VALUE);
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        newRentalButton.setMaxWidth(Double.MAX_VALUE);
        rechargeButton.setMaxWidth(Double.MAX_VALUE);
        exitButton = new Button("Avsluta");
        region = new Region();
        vbox.getChildren().addAll(newItemButton, searchButton, changeButton, deleteButton,
                newRentalButton, rechargeButton, region, exitButton);
        VBox.setVgrow(region, Priority.ALWAYS);
        rentalObjectTable = new TableView<>();
        rentalObjectTable.setEditable(false);
        colorColumn = new TableColumn<>("Färg");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        typeColumn = new TableColumn<>("Typ:");
        typeColumn.setCellValueFactory(cellData -> {
            RentalType type = cellData.getValue().getRentalType();
            String swedishName = (type != null) ? type.getSwedishName() : "";

            return new SimpleStringProperty(swedishName);
        });
        materialColumn = new TableColumn<>("Material");
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        priceColumn = new TableColumn<>("Pris per dag");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));
        availableColumn = new TableColumn<>("Tillgänglig");
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        availableColumn.setCellFactory(new Callback<TableColumn<RentalObject, Boolean>,
                TableCell<RentalObject,
                        Boolean>>() {
            @Override
            public TableCell<RentalObject, Boolean> call(TableColumn<RentalObject, Boolean> param) {
                return new TableCell<RentalObject, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            if (item) {
                                setText("Ja");
                            } else {
                                setText("Nej");
                            }
                        }
                    }
                };
            }
        });

        rentalObjectTable.getColumns().addAll(typeColumn, colorColumn, materialColumn, priceColumn,
                availableColumn);
        populateTable();
        pane.setLeft(vbox);
        pane.setCenter(rentalObjectTable);
        tab.setContent(pane);
        newItemButton.setOnAction(e -> {
            rentalObjectController.newRentalObjectView();
            populateTable();
        });
        searchButton.setOnAction(e -> {
            rentalObjectController.searchRentalObjectView();
        });
        changeButton.disableProperty().bind(
                rentalObjectTable.getSelectionModel().selectedItemProperty().isNull()
        );
        changeButton.setOnAction(e -> {
            RentalObject rentalObject = rentalObjectTable.getSelectionModel().getSelectedItem();
            if (rentalObject != null) {
                rentalObjectController.changeRentalObjectView(rentalObject);
                    populateTable();

            } else {
                showInfoAlert("Välj en vara att ändra.");
            }

        });
        deleteButton.disableProperty().bind(
                rentalObjectTable.getSelectionModel().selectedItemProperty().isNull()
        );
        deleteButton.setOnAction(ae -> {
            RentalObject rentalObject = rentalObjectTable.getSelectionModel().getSelectedItem();
            if (rentalObject != null) {
                if (rentalObjectController.removeRentalObject(rentalObject, this)) {
                    populateTable();
                    showInfoAlert(rentalObject.toString() + " borttagen.");
                }
            } else {
                showInfoAlert("Välj en vara att ta bort.");
            }
        });
        newRentalButton.disableProperty().bind(
                rentalObjectTable.getSelectionModel().selectedItemProperty().isNull()
        );
        newRentalButton.setOnAction(ae -> {
            RentalObject rentalObject = rentalObjectTable.getSelectionModel().getSelectedItem();
            if (rentalObject == null) {
                showInfoAlert("Välj en vara att hyra ut!");
            } else if (rentalObject.isAvailable() == false) {
                showInfoAlert("Varan är tyvärr redan uthyrd");
            } else {
                rentalController.newRental(rentalObject, this);

            }
        });
        rechargeButton.setOnAction(ae -> {
            populateTable();
        });
        exitButton.setOnAction(ae -> {
            System.exit(0);
        });
        return tab;
    }

    public void populateTable() {
        List<RentalObject> list = rentalObjectController.getAllRentalObjects(this);
        ObservableList<RentalObject> observableList = FXCollections.observableList(list);
        rentalObjectTable.setItems(observableList);
    }

    public void populateTable(List<RentalObject> list) {
        ObservableList<RentalObject> observableList = FXCollections.observableList(list);
        rentalObjectTable.setItems(observableList);
    }
}

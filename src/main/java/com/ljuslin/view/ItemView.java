package com.ljuslin.view;

import com.ljuslin.controller.ItemController;
import com.ljuslin.controller.RentalController;
import com.ljuslin.entity.*;
import com.ljuslin.exception.DatabaseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Creates the item tab
 *
 * @author Tina Ljuslin
 */
public class ItemView extends View implements TabView {
    private ItemController itemController;
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
    ComboBox<RentalType> typeBox;
    private TableView<Tie> tieTable;
    private TableColumn<Tie, Material> tieMaterialColumn;
    private TableColumn<Tie, String> tieColorColumn;
    private TableColumn<Tie, BigDecimal> tiePriceColumn;
    private TableColumn<Tie, Boolean> tieAvailableColumn;

    private TableView<Bowtie> bowtieTable;
    private TableColumn<Bowtie, Material> bowtieMaterialColumn;
    private TableColumn<Bowtie, String> bowtieColorColumn;
    private TableColumn<Bowtie, BigDecimal> bowtiePriceColumn;
    private TableColumn<Bowtie, Boolean> bowtieAvailableColumn;

    private TableView<PocketSquare> pocketSquareTable;
    private TableColumn<PocketSquare, Material> pocketSquareMaterialColumn;
    private TableColumn<PocketSquare, String> pocketSquareColorColumn;
    private TableColumn<PocketSquare, BigDecimal> pocketSquarePriceColumn;
    private TableColumn<PocketSquare, Boolean> pocketSquareAvailableColumn;

    public ItemView() {
    }

    public void setItemController(ItemController itemController
            , RentalController rentalController) {
        this.itemController = itemController;
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
        typeBox = new ComboBox<>();
        newItemButton.setMaxWidth(Double.MAX_VALUE);
        searchButton.setMaxWidth(Double.MAX_VALUE);
        changeButton.setMaxWidth(Double.MAX_VALUE);
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        newRentalButton.setMaxWidth(Double.MAX_VALUE);
        rechargeButton.setMaxWidth(Double.MAX_VALUE);
        exitButton = new Button("Avsluta");
        region = new Region();
        typeBox.getItems().setAll(RentalType.values());
        typeBox.setConverter(new StringConverter<RentalType>() {
            @Override
            public String toString(RentalType type) {
                return (type == null) ? "" : type.getSwedishName();
            }

            @Override
            public RentalType fromString(String string) {
                // Denna behövs sällan om boxen inte är redigerbar
                return null;
            }
        });
        typeBox.setOnAction(actionEvent -> {
            RentalType rentalType = typeBox.getValue();
            switch (rentalType) {
                case BOWTIE -> {
                    showBowtieTable();
                }
                case POCKET_SQUARE -> {
                    showPocketSquareTable();
                }
                case TIE -> {
                    showTieTable();
                }
            }
        });
        typeBox.setValue(RentalType.TIE);
        vbox.getChildren().addAll(typeBox, newItemButton, searchButton, changeButton, deleteButton,
                newRentalButton, rechargeButton, region, exitButton);
        VBox.setVgrow(region, Priority.ALWAYS);
        tieTable = new TableView<>();
        tieTable.setEditable(false);
        bowtieTable = new TableView<>();
        bowtieTable.setEditable(false);
        pocketSquareTable = new TableView<>();
        pocketSquareTable.setEditable(false);
        tieColorColumn = new TableColumn<>("Färg");
        tieColorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        tieMaterialColumn = new TableColumn<>("Material");
        tieMaterialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        tiePriceColumn = new TableColumn<>("Pris per dag");
        tiePriceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));
        tieAvailableColumn = new TableColumn<>("Tillgänglig");
        tieAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        tieAvailableColumn.setCellFactory(new Callback<TableColumn<Tie, Boolean>, TableCell<Tie,
                Boolean>>() {
            @Override
            public TableCell<Tie, Boolean> call(TableColumn<Tie, Boolean> param) {
                return new TableCell<Tie, Boolean>() {
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

        bowtieColorColumn = new TableColumn<>("Färg");
        bowtieColorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        bowtieMaterialColumn = new TableColumn<>("Material");
        bowtieMaterialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        bowtiePriceColumn = new TableColumn<>("Pris per dag");
        bowtiePriceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));
        bowtieAvailableColumn = new TableColumn<>("Tillgänglig");
        bowtieAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        bowtieAvailableColumn.setCellFactory(new Callback<TableColumn<Bowtie, Boolean>,
                TableCell<Bowtie,
                        Boolean>>() {
            @Override
            public TableCell<Bowtie, Boolean> call(TableColumn<Bowtie, Boolean> param) {
                return new TableCell<Bowtie, Boolean>() {
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
        pocketSquareColorColumn = new TableColumn<>("Färg");
        pocketSquareColorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        pocketSquareMaterialColumn = new TableColumn<>("Material");
        pocketSquareMaterialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        pocketSquarePriceColumn = new TableColumn<>("Pris per dag");
        pocketSquarePriceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));
        pocketSquareAvailableColumn = new TableColumn<>("Tillgänglig");
        pocketSquareAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        pocketSquareAvailableColumn.setCellFactory(new Callback<TableColumn<PocketSquare, Boolean>,
                TableCell<PocketSquare,
                        Boolean>>() {
            @Override
            public TableCell<PocketSquare, Boolean> call(TableColumn<PocketSquare, Boolean> param) {
                return new TableCell<PocketSquare, Boolean>() {
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
        tieTable.getColumns().addAll(tieColorColumn, tieMaterialColumn, tiePriceColumn,
                tieAvailableColumn);
        bowtieTable.getColumns().addAll(bowtieColorColumn, bowtieMaterialColumn, bowtiePriceColumn,
                bowtieAvailableColumn);
        pocketSquareTable.getColumns().addAll(pocketSquareColorColumn, pocketSquareMaterialColumn,
                pocketSquarePriceColumn, pocketSquareAvailableColumn);
        populateTieTable();
        pane.setLeft(vbox);
        pane.setCenter(tieTable);
        tab.setContent(pane);
        newItemButton.setOnAction(e -> {
            itemController.newItemView();
            updateTable();
        });
        searchButton.setOnAction(e -> {
            itemController.searchItemView(typeBox.getValue());
        });
        changeButton.setOnAction(e -> {
            switch (typeBox.getValue()) {
                case BOWTIE -> {
                    Bowtie bowtie = bowtieTable.getSelectionModel().getSelectedItem();
                    if (bowtie != null) {
                        itemController.changeBowtieView(bowtie);
                        showBowtieTable();
                    } else {
                        showInfoAlert("Välj en fluga att ändra!");
                    }
                }
                case POCKET_SQUARE -> {
                    PocketSquare pocketSquare = pocketSquareTable.getSelectionModel().getSelectedItem();
                    if (pocketSquare != null) {
                        itemController.changePocketSquareView(pocketSquare);
                        showPocketSquareTable();
                    } else {
                        showInfoAlert("Välj en näsduk att ändra!");
                    }
                }
                case TIE -> {
                    Tie tie = tieTable.getSelectionModel().getSelectedItem();
                    if (tie != null) {
                        itemController.changeTieView(tie);
                        showTieTable();
                    } else {
                        showInfoAlert("Välj en slips att ändra!");
                    }
                }
            }
        });
        deleteButton.setOnAction(ae -> {
            switch (typeBox.getValue()) {
                case BOWTIE -> {
                    Bowtie bowtie = bowtieTable.getSelectionModel().getSelectedItem();
                    if (bowtie != null) {
                        itemController.removeBowtie(bowtie);
                        showBowtieTable();
                    } else {
                        showInfoAlert("Välj en fluga att ta bort!");
                    }
                }
                case POCKET_SQUARE -> {
                    PocketSquare pocketSquare = pocketSquareTable.getSelectionModel().getSelectedItem();
                    if (pocketSquare != null) {
                        itemController.removePocketSquare(pocketSquare);
                        showPocketSquareTable();
                    } else {
                        showInfoAlert("Välj en näsduk att ta bort!");
                    }
                }
                case TIE -> {

                    Tie tie = tieTable.getSelectionModel().getSelectedItem();
                    if (tie != null) {
                        itemController.removeTie(tie);
                        showTieTable();
                    } else {
                        showInfoAlert("Välj en slips att ta bort!");
                    }
                }
            }
        });
        newRentalButton.setOnAction(ae -> {
            switch (typeBox.getValue()) {
                case BOWTIE -> {
                    Bowtie bowtie = bowtieTable.getSelectionModel().getSelectedItem();
                    if (bowtie != null) {
                        try {
                            rentalController.newRental(RentalType.BOWTIE, bowtie.getItemId());
                        } catch (DatabaseException e) {
                            showInfoAlert(e.getMessage());
                        } catch (Exception e) {
                            showErrorAlert(e.getMessage());
                        }
                    } else {
                        showInfoAlert("Välj en vara att hyra ut!");
                    }
                }
                case POCKET_SQUARE -> {
                    PocketSquare pocketSquare = pocketSquareTable.getSelectionModel().getSelectedItem();
                    if (pocketSquare != null) {
                        try {
                            rentalController.newRental(RentalType.POCKET_SQUARE, pocketSquare.getItemId());
                        } catch (DatabaseException e) {
                            showInfoAlert(e.getMessage());
                        } catch (Exception e) {
                            showErrorAlert(e.getMessage());
                        }
                    } else {
                        showInfoAlert("Välj en vara att hyra ut!");
                    }
                }
                case TIE -> {

                    Tie tie = tieTable.getSelectionModel().getSelectedItem();
                    if (tie != null) {
                        try {
                            rentalController.newRental(RentalType.TIE, tie.getItemId());
                        } catch (DatabaseException e) {
                            showInfoAlert(e.getMessage());
                        } catch (Exception e) {
                            showErrorAlert(e.getMessage());
                        }
                    } else {
                        showInfoAlert("Välj en vara att hyra ut!");
                    }
                }
            }

        });
        rechargeButton.setOnAction(ae ->

        {
            updateTable();
        });
        exitButton.setOnAction(ae ->

        {
            System.exit(0);
        });
        return tab;
    }


    private void populateBowtieTable(List<Bowtie> list) {
        try {
            ObservableList<Bowtie> observableList = FXCollections.observableList(list);
            bowtieTable.setItems(observableList);
        } catch (DatabaseException e) {
            showInfoAlert(e.getMessage());
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void showBowtieTable() {
        populateBowtieTable(itemController.getAllBowties());
        pane.setCenter(bowtieTable);

    }

    public void showBowtieTable(List<Bowtie> list) {
        populateBowtieTable(list);
        pane.setCenter(bowtieTable);
    }

    public void showPocketSquareTable(List<PocketSquare> list) {
        populatePocketSquareTable(list);
        pane.setCenter(pocketSquareTable);
    }

    public void showTieTable(List<Tie> list) {
        populateTieTable(list);
        pane.setCenter(tieTable);
    }

    private void populateTieTable() {
        try {
            List<Tie> list = itemController.getAllTies();
            ObservableList<Tie> observableList = FXCollections.observableList(list);
            tieTable.setItems(observableList);
        } catch (DatabaseException e) {
            showInfoAlert(e.getMessage());
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void populateTieTable(List<Tie> list) {
        try {
            ObservableList<Tie> observableList = FXCollections.observableList(list);
            tieTable.setItems(observableList);
        } catch (DatabaseException e) {
            showInfoAlert(e.getMessage());
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void showTieTable() {
        populateTieTable();
        pane.setCenter(tieTable);

    }

    public void populateTable() {
        switch (typeBox.getValue()) {
            case BOWTIE -> showBowtieTable();
            case POCKET_SQUARE -> showPocketSquareTable();
            case TIE -> showTieTable();
        }

    }

    private void populatePocketSquareTable() {
        try {
            List<PocketSquare> list = itemController.getAllPocketSquares();
            ObservableList<PocketSquare> observableList = FXCollections.observableList(list);
            pocketSquareTable.setItems(observableList);
        } catch (DatabaseException e) {
            showInfoAlert(e.getMessage());
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void populatePocketSquareTable(List<PocketSquare> list) {
        try {
            ObservableList<PocketSquare> observableList = FXCollections.observableList(list);
            pocketSquareTable.setItems(observableList);
        } catch (DatabaseException e) {
            showInfoAlert(e.getMessage());
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void showPocketSquareTable() {
        populatePocketSquareTable();
        pane.setCenter(pocketSquareTable);

    }

    public void updateTable() {
        RentalType rentalType = typeBox.getValue();
        switch (rentalType) {
            case BOWTIE -> {
                showBowtieTable();
            }
            case POCKET_SQUARE -> {
                showPocketSquareTable();
            }
            case TIE -> {
                showTieTable();
            }
        }
    }
}

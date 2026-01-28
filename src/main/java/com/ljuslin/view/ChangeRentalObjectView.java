package com.ljuslin.view;

import com.ljuslin.controller.RentalObjectController;
import com.ljuslin.entity.*;
import com.ljuslin.exception.DatabaseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Shows view for changing an item
 *
 * @author Tina Ljuslin
 */
public class ChangeRentalObjectView extends View {
    private RentalObjectController rentalObjectController;

    private Stage newItemStage;
    private TextField colorField;
    private TextField pricePerDayField;

    private ComboBox<Material> materialComboBox;

    private Label colorLabel;
    private Label materialLabel;
    private Label pricePerDayLabel;
    private Button saveButton;
    private Button cancelButton;
    private GridPane gridPane;
    private Scene scene2;

    public ChangeRentalObjectView() {
    }

    public ChangeRentalObjectView(RentalObjectController rentalObjectController) {
        this.rentalObjectController = rentalObjectController;
    }
/*

public void showBowtiePopUp(Stage mainStage, Scene mainScene, Bowtie bowtie) {
    newItemStage = new Stage();
    saveButton = new Button("Spara");
    cancelButton = new Button("Avbryt");
    colorLabel = new Label("Färg");
    materialLabel = new Label("Material");
    pricePerDayLabel = new Label("Pris per dag");
    colorField = new TextField();
    colorField.setText(bowtie.getColor());
    pricePerDayField = new TextField();
    pricePerDayField.setText(String.valueOf(bowtie.getPricePerDay()));
    ObservableList<Material> materials = FXCollections.observableArrayList(Material.values());
    materialComboBox = new ComboBox<>(materials);
    materialComboBox.getSelectionModel().select(bowtie.getMaterial());
    gridPane = new GridPane();
    gridPane.add(colorLabel, 0, 0);
    gridPane.add(materialLabel, 0, 1);
    gridPane.add(pricePerDayLabel, 0, 2);
    gridPane.add(colorField, 1, 0);
    gridPane.add(materialComboBox, 1, 1);
    gridPane.add(pricePerDayField, 1, 2);
    gridPane.add(saveButton, 0, 4);
    gridPane.add(cancelButton, 1, 4);
    scene2 = new Scene(gridPane, 500, 500);
    newItemStage.setScene(scene2);



    String css = getClass().getResource("/greenStyles.css").toExternalForm();
    scene2.getStylesheets().add(css);
    saveButton.setOnAction(ae -> {
        try {
                itemController.changeRentalObject(bowtie, colorField.getText(),
                        materialComboBox.getValue(),
                        pricePerDayField.getText());
            newItemStage.close();
        } catch (DatabaseException e) {
            showInfoAlert(e.getMessage());
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    });
    cancelButton.setOnAction(ae -> {
        newItemStage.close();
    });

    newItemStage.initOwner(mainStage);
    newItemStage.initModality(Modality.APPLICATION_MODAL);
    newItemStage.setScene(scene2);
    newItemStage.showAndWait();
}
*/
/*

    public void showPocketSquarePopUp(Stage mainStage, Scene mainScene, PocketSquare pocketSquare) {
        newItemStage = new Stage();
        saveButton = new Button("Spara");
        cancelButton = new Button("Avbryt");
        colorLabel = new Label("Färg");
        materialLabel = new Label("Material");
        pricePerDayLabel = new Label("Pris per dag");
        colorField = new TextField();
        colorField.setText(pocketSquare.getColor());
        pricePerDayField = new TextField();
        pricePerDayField.setText(String.valueOf(pocketSquare.getPricePerDay()));
        ObservableList<Material> materials = FXCollections.observableArrayList(Material.values());
        materialComboBox = new ComboBox<>(materials);
        materialComboBox.getSelectionModel().select(pocketSquare.getMaterial());
        gridPane = new GridPane();
        gridPane.add(colorLabel, 0, 0);
        gridPane.add(materialLabel, 0, 1);
        gridPane.add(pricePerDayLabel, 0, 2);
        gridPane.add(colorField, 1, 0);
        gridPane.add(materialComboBox, 1, 1);
        gridPane.add(pricePerDayField, 1, 2);
        gridPane.add(saveButton, 0, 4);
        gridPane.add(cancelButton, 1, 4);
        scene2 = new Scene(gridPane, 500, 500);
        newItemStage.setScene(scene2);

        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);
        saveButton.setOnAction(ae -> {
            try {
                itemController.changeRentalObject(pocketSquare, colorField.getText(),
                        materialComboBox.getValue(),
                        pricePerDayField.getText());
                newItemStage.close();
            } catch (DatabaseException e) {
                showInfoAlert(e.getMessage());
            } catch (Exception e) {
                showErrorAlert(e.getMessage());
            }
        });
        cancelButton.setOnAction(ae -> {
            newItemStage.close();
        });

        newItemStage.initOwner(mainStage);
        newItemStage.initModality(Modality.APPLICATION_MODAL);
        newItemStage.setScene(scene2);
        newItemStage.showAndWait();
    }
    public void showTiePopUp(Stage mainStage, Scene mainScene, Tie tie) {
        newItemStage = new Stage();
        saveButton = new Button("Spara");
        cancelButton = new Button("Avbryt");
        colorLabel = new Label("Färg");
        materialLabel = new Label("Material");
        pricePerDayLabel = new Label("Pris per dag");
        colorField = new TextField();
        colorField.setText(tie.getColor());
        pricePerDayField = new TextField();
        pricePerDayField.setText(String.valueOf(tie.getPricePerDay()));
        ObservableList<Material> materials = FXCollections.observableArrayList(Material.values());
        materialComboBox = new ComboBox<>(materials);
        materialComboBox.getSelectionModel().select(tie.getMaterial());
        gridPane = new GridPane();
        gridPane.add(colorLabel, 0, 0);
        gridPane.add(materialLabel, 0, 1);
        gridPane.add(pricePerDayLabel, 0, 2);
        gridPane.add(colorField, 1, 0);
        gridPane.add(materialComboBox, 1, 1);
        gridPane.add(pricePerDayField, 1, 2);
        gridPane.add(saveButton, 0, 4);
        gridPane.add(cancelButton, 1, 4);
        scene2 = new Scene(gridPane, 500, 500);
        newItemStage.setScene(scene2);

        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);
        saveButton.setOnAction(ae -> {
            try {
                itemController.changeRentalObject(tie, colorField.getText(),
                        materialComboBox.getValue(),
                        pricePerDayField.getText());
                newItemStage.close();
            } catch (DatabaseException e) {
                showInfoAlert(e.getMessage());
            } catch (Exception e) {
                showErrorAlert(e.getMessage());
            }
        });
        cancelButton.setOnAction(ae -> {
            newItemStage.close();
        });

        newItemStage.initOwner(mainStage);
        newItemStage.initModality(Modality.APPLICATION_MODAL);
        newItemStage.setScene(scene2);
        newItemStage.showAndWait();
    }
*/

    public void showPopUp(Stage mainStage, Scene mainScene, RentalObject rentalObject) {
        newItemStage = new Stage();
        saveButton = new Button("Spara");
        cancelButton = new Button("Avbryt");
        colorLabel = new Label("Färg");
        materialLabel = new Label("Material");
        pricePerDayLabel = new Label("Pris per dag");
        colorField = new TextField();
        colorField.setText(rentalObject.getColor());
        pricePerDayField = new TextField();
        pricePerDayField.setText(String.valueOf(rentalObject.getPricePerDay()));
        ObservableList<Material> materials = FXCollections.observableArrayList(Material.values());
        materialComboBox = new ComboBox<>(materials);
        materialComboBox.getSelectionModel().select(rentalObject.getMaterial());
        gridPane = new GridPane();
        gridPane.add(colorLabel, 0, 0);
        gridPane.add(materialLabel, 0, 1);
        gridPane.add(pricePerDayLabel, 0, 2);
        gridPane.add(colorField, 1, 0);
        gridPane.add(materialComboBox, 1, 1);
        gridPane.add(pricePerDayField, 1, 2);
        gridPane.add(saveButton, 0, 4);
        gridPane.add(cancelButton, 1, 4);
        scene2 = new Scene(gridPane, 500, 500);
        newItemStage.setScene(scene2);

        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);
        saveButton.setOnAction(ae -> {

            if (rentalObjectController.changeRentalObject(rentalObject, colorField.getText(),
                    materialComboBox.getValue(),
                    pricePerDayField.getText(), this)) {
                newItemStage.close();
            }

        });
        cancelButton.setOnAction(ae -> {
            newItemStage.close();
        });

        newItemStage.initOwner(mainStage);
        newItemStage.initModality(Modality.APPLICATION_MODAL);
        newItemStage.setScene(scene2);
        newItemStage.showAndWait();
    }
}

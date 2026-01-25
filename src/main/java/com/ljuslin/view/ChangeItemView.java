package com.ljuslin.view;

import com.ljuslin.controller.ItemController;
import com.ljuslin.entity.Bowtie;
import com.ljuslin.entity.Material;
import com.ljuslin.entity.PocketSquare;
import com.ljuslin.entity.Tie;
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
public class ChangeItemView extends View {
    private ItemController itemController;

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

    public ChangeItemView() {
    }

    public ChangeItemView(ItemController itemController) {
        this.itemController = itemController;
    }
/*
    public void showPopUp(Stage mainStage, Scene mainScene, Item item) {
        newItemStage = new Stage();
        saveButton = new Button("Spara");
        cancelButton = new Button("Avbryt");
        brandLabel = new Label("Märke");
        colorLabel = new Label("Färg");
        materialLabel = new Label("Material");
        patternLabel = new Label("Mönster");
        pricePerDayLabel = new Label("Pris per dag");
        brandField = new TextField();
        brandField.setText(item.getBrand());
        colorField = new TextField();
        colorField.setText(item.getColor());
        pricePerDayField = new TextField();
        pricePerDayField.setText(String.valueOf(item.getPricePerDay()));
        ObservableList<Material> materials = FXCollections.observableArrayList(Material.values());
        materialComboBox = new ComboBox<>(materials);
        materialComboBox.getSelectionModel().select(item.getMaterial());
        ObservableList<Pattern> patterns = FXCollections.observableArrayList(Pattern.values());
        patternComboBox = new ComboBox<>(patterns);
        patternComboBox.getSelectionModel().select(item.getPattern());
        gridPane = new GridPane();
        gridPane.add(brandLabel, 0, 0);
        gridPane.add(colorLabel, 0, 1);
        gridPane.add(patternLabel, 0, 2);
        gridPane.add(materialLabel, 0, 3);
        gridPane.add(pricePerDayLabel, 0, 4);
        gridPane.add(brandField, 1, 0);
        gridPane.add(colorField, 1, 1);
        gridPane.add(patternComboBox, 1, 2);
        gridPane.add(materialComboBox, 1, 3);
        gridPane.add(pricePerDayField, 1, 4);
        gridPane.add(saveButton, 0, 7);
        gridPane.add(cancelButton, 1, 7);
        scene2 = new Scene(gridPane, 500, 500);
        newItemStage.setScene(scene2);

        if (item instanceof Tie) {
            widthLabel = new Label("Bredd");
            lengthLabel = new Label("Längd");
            widthField = new TextField(String.valueOf(((Tie) item).getWidth()));
            lengthField = new TextField(String.valueOf(((Tie) item).getLength()));
            gridPane.add(widthLabel, 0, 5);
            gridPane.add(widthField, 1, 5);
            gridPane.add(lengthLabel, 0, 6);
            gridPane.add(lengthField, 1, 6);

        } else {
            sizeLabel = new Label("Storlek");
            preeTiedLabel = new Label("Färdigknuten");

            ObservableList<Boolean> pre = FXCollections.observableArrayList(Boolean.FALSE, Boolean.TRUE);
            preeTiedComboBox = new ComboBox<>(pre);
            preeTiedComboBox.getSelectionModel().select(((Bowtie) item).isPreTied());

            ObservableList<String> size = FXCollections.observableArrayList("S", "M", "L", "XL");
            sizeComboBox = new ComboBox<>(size);
            sizeComboBox.getSelectionModel().select(((Bowtie) item).getSize());
            gridPane.add(preeTiedLabel, 0, 5);
            gridPane.add(preeTiedComboBox, 1, 5);
            gridPane.add(sizeLabel, 0, 6);
            gridPane.add(sizeComboBox, 1, 6);
        }
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);
        saveButton.setOnAction(ae -> {
            try {
                if (item instanceof Tie) {
                    itemController.changeItem(item, brandField.getText(), colorField.getText(),
                            materialComboBox.getValue(), patternComboBox.getValue(),
                            pricePerDayField.getText(), widthField.getText(), lengthField.getText());
                } else {
                    itemController.changeItem(item, brandField.getText(), colorField.getText(),
                            materialComboBox.getValue(), patternComboBox.getValue(),
                            pricePerDayField.getText(), sizeComboBox.getValue(),
                            preeTiedComboBox.getValue());

                }
                newItemStage.close();
            } catch (ItemException e) {
                showInfoAlert(e.getMessage());
            } catch (DBException e) {
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
    }*/
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
                itemController.changeBowtie(bowtie, colorField.getText(),
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
                itemController.changePocketSquare(pocketSquare, colorField.getText(),
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
                itemController.changeTie(tie, colorField.getText(),
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
}

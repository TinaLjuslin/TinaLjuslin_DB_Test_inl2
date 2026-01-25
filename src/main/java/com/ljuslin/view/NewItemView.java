package com.ljuslin.view;

import com.ljuslin.controller.ItemController;
import com.ljuslin.entity.Material;
import com.ljuslin.entity.RentalType;
import com.ljuslin.exception.DatabaseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * Cretes a view for a new item
 *
 * @author Tina Ljuslin
 */
public class NewItemView extends View {
    private ItemController itemController;

    private Stage newItemStage;
    private Scene scene2;
    private TextField colorField;
    private TextField pricePerDayField;

    private ComboBox<Material> materialComboBox;

    private HBox hBox;
    private Label colorLabel;
    private Label materialLabel;
    private Label pricePerDayLabel;
    private Label typeLabel;
    private Button saveButton;
    private Button cancelButton;
    private ComboBox<RentalType> typeBox;
    private GridPane gridPane;
    public NewItemView(ItemController itemController) {
        this.itemController = itemController;
    }

    public void showPopUp(Stage mainStage, Scene mainScene) {
        newItemStage = new Stage();
        saveButton = new Button("Spara");
        cancelButton = new Button("Avbryt");
        typeLabel = new Label("Typ");
        colorLabel = new Label("Färg");
        materialLabel = new Label("Material");
        pricePerDayLabel = new Label("Pris per dag");
        colorField = new TextField();
        pricePerDayField = new TextField();
        ObservableList<Material> materials = FXCollections.observableArrayList(Material.values());
        materialComboBox = new ComboBox<>(materials);
        materialComboBox.getSelectionModel().select(0);
        ObservableList<RentalType> types = FXCollections.observableArrayList(RentalType.values());
        typeBox = new ComboBox<>(types);
        typeBox.getItems().setAll(RentalType.values());
        typeBox.getSelectionModel().select(0);
        typeBox.setConverter(new StringConverter<RentalType>() {
            @Override
            public String toString(RentalType type) {
                return (type == null) ? "" : type.getSwedishName();
            }

            @Override
            public RentalType fromString(String string) {
                return null;
            }
        });
        gridPane = new GridPane();
        gridPane.add(typeLabel, 0,0);
        gridPane.add(colorLabel, 0, 1);
        gridPane.add(materialLabel, 0, 2);
        gridPane.add(pricePerDayLabel, 0, 3);
        gridPane.add(typeBox, 1, 0);
        gridPane.add(colorField, 1, 1);
        gridPane.add(materialComboBox, 1, 2);
        gridPane.add(pricePerDayField, 1, 3);
        gridPane.add(saveButton, 0, 4);
        gridPane.add(cancelButton, 1, 4);
        scene2 = new Scene(gridPane, 500, 500);
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);
        newItemStage.setScene(scene2);
        saveButton.setOnAction( ae -> {
            try {
                itemController.newItem(typeBox.getValue(), materialComboBox.getValue(),
                        colorField.getText(), pricePerDayField.getText());
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

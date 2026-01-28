package com.ljuslin.view;

import com.ljuslin.controller.RentalController;
import com.ljuslin.exception.DatabaseException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates view for searching a rental
 *
 * @author Tina Ljuslin
 */
public class SearchRentalView extends View {
    private RentalController rentalController;

    private Scene scene2;
    private Stage searchRentalStage;
    private TextField searchField;
    private Label searchLabel;
    private Button searchButton;
    private Button cancelButton;
    private GridPane gridPane;

    public SearchRentalView(RentalController rentalController) {
        this.rentalController = rentalController;
    }

    public void showPopUp(Stage mainStage, Scene mainScene) {
        searchRentalStage = new Stage();
        searchButton = new Button("Sök");
        cancelButton = new Button("Avbryt");
        searchLabel = new Label("Sök:");
        searchField = new TextField();
        gridPane = new GridPane();
        gridPane.add(searchLabel, 0, 0);
        gridPane.add(searchField, 1, 0);
        gridPane.add(searchButton, 0, 1);
        gridPane.add(cancelButton, 1, 1);
        scene2 = new Scene(gridPane, 300, 300);
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);

        searchButton.setOnAction(ae -> {
                if(rentalController.searchRental(searchField.getText(), this)) {
                    searchRentalStage.close();
                }

        });
        cancelButton.setOnAction(ae -> {
            searchRentalStage.close();
        });

        searchRentalStage.initOwner(mainStage);
        searchRentalStage.initModality(Modality.APPLICATION_MODAL);
        searchRentalStage.setScene(scene2);
        searchRentalStage.showAndWait();
    }
}

package com.ljuslin.view;

import com.ljuslin.controller.RentalObjectController;
import com.ljuslin.exception.DatabaseException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates view for searching an item
 *
 * @author Tina Ljuslin
 */
public class SearchRentalObjectView extends View {
    private RentalObjectController rentalObjectController;

    private Stage searchItemStage;
    private Scene scene2;
    private TextField searchField;
    private Label searchLabel;
    private Button searchButton;
    private Button cancelButton;
    private GridPane gridPane;

    public SearchRentalObjectView(RentalObjectController rentalObjectController) {
        this.rentalObjectController = rentalObjectController;
    }

    public void showPopUp(Stage mainStage, Scene mainScene) {
        searchItemStage = new Stage();
        searchButton = new Button("Sök");
        cancelButton = new Button("Avbryt");
        searchLabel = new Label("Sök ");
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

            rentalObjectController.searchRentalObject(searchField.getText(), this);
            searchItemStage.close();

        });
        cancelButton.setOnAction(ae -> {
            searchItemStage.close();
        });

        searchItemStage.initOwner(mainStage);
        searchItemStage.initModality(Modality.APPLICATION_MODAL);
        searchItemStage.setScene(scene2);
        searchItemStage.showAndWait();
    }
}

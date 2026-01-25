package com.ljuslin.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Ej implementerad,ev för att ersätta alla andra serach views
 */
public class SearchView extends View {
    private MainView mainController;

    private Scene scene2;
    private Stage searchStage;
    private TextField searchField;
    private Label searchLabel;
    private Button searchButton;
    private Button cancelButton;
    private GridPane gridPane;

    public SearchView() {
    }

    public SearchView(MainView mainController) {
        this.mainController = mainController;
    }

    public void showPopUp(Stage mainStage, Scene mainScene) {
        searchStage = new Stage();
        searchButton = new Button("Sök");
        cancelButton = new Button("Avbryt");
        searchLabel = new Label("Sök:");
        searchField = new TextField();
        gridPane = new GridPane();
        gridPane.add(searchLabel, 0, 0);
        gridPane.add(searchField, 1, 0);
        gridPane.add(searchButton, 0, 1);
        gridPane.add(cancelButton, 1, 1);
        Scene scene2 = new Scene(gridPane, 300, 300);
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);

        searchButton.setOnAction(ae -> {
                /*try {
                    //mainController.search(searchField.getText());
                    searchStage.close();
                } catch (MemberException e) {
                    showInfoAlert(e.getMessage());
                } catch (FileException e) {
                    showInfoAlert(e.getMessage());
                } catch (Exception e) {
                    showErrorAlert(e.getMessage());
                }*/


        });
        cancelButton.setOnAction(ae -> {
            searchStage.close();
        });
/*
            searchMemberStage.initOwner(mainStage);
            searchMemberStage.initModality(Modality.APPLICATION_MODAL);
            searchMemberStage.setScene(scene2);
            searchMemberStage.showAndWait();
     */
    }
}



package com.ljuslin.view;

import com.ljuslin.controller.MemberController;
import com.ljuslin.exception.DatabaseException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Shows a popup to search for a member
 *
 * @author Tina Ljuslin
 */
public class SearchMemberView extends View {
    private MemberController memberController;

    private Scene scene2;
    private Stage searchMemberStage;
    private TextField searchField;
    private Label searchLabel;
    private Button searchButton;
    private Button cancelButton;
    private GridPane gridPane;

    public SearchMemberView(MemberController memberController) {
        this.memberController = memberController;
    }

    public void showPopUp(Stage mainStage, Scene mainScene) {
        searchMemberStage = new Stage();
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
            try {
                memberController.searchMember(searchField.getText());
                searchMemberStage.close();
            } catch (DatabaseException e) {
                showInfoAlert(e.getMessage());
            } catch (Exception e) {
                showErrorAlert(e.getMessage());
            }
        });
        cancelButton.setOnAction(ae -> {
            searchMemberStage.close();
        });

        searchMemberStage.initOwner(mainStage);
        searchMemberStage.initModality(Modality.APPLICATION_MODAL);
        searchMemberStage.setScene(scene2);
        searchMemberStage.showAndWait();
    }
}

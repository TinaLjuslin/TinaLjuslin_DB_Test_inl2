package com.ljuslin.view;

import com.ljuslin.controller.MemberController;
import com.ljuslin.entity.Level;
import com.ljuslin.exception.DatabaseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Shows a popup where the user can enter values for a new member
 *
 * @author Tina Ljuslin
 */
public class NewMemberView extends View{
    private MemberController memberController;

    private Stage newMemberStage;
    private Scene scene2;
    private TextField fistNameField;
    private TextField lastNameField;
    private TextField emailField;
    private ComboBox<Level> levelComboBox;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label emailLabel;
    private Label levelLabel;
    private Button saveButton;
    private Button cancelButton;
    private GridPane gridPane;

    public NewMemberView(MemberController memberController) {
        this.memberController = memberController;
    }

    public void showPopUp(Stage mainStage, Scene mainScene) {
        newMemberStage = new Stage();
        saveButton = new Button("Spara");
        cancelButton = new Button("Avbryt");
        firstNameLabel = new Label("Förnamn");
        lastNameLabel = new Label("Efternamn");
        emailLabel = new Label("Email");
        levelLabel = new Label("Level");
        fistNameField = new TextField();
        lastNameField = new TextField();
        emailField = new TextField();
        ObservableList<Level> levels = FXCollections.observableArrayList(Level.values());
        levelComboBox = new ComboBox<>(levels);
        levelComboBox.getSelectionModel().select(0);

        gridPane = new GridPane();
        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(levelLabel, 0, 3);
        gridPane.add(fistNameField, 1, 0);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(emailField, 1, 2);
        gridPane.add(levelComboBox, 1, 3);
        gridPane.add(saveButton, 0, 4);
        gridPane.add(cancelButton, 1, 4);
        scene2 = new Scene(gridPane, 300, 300);
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);

        saveButton.setOnAction( ae -> {
            try {
                memberController.newMember(fistNameField.getText(), lastNameField.getText(),
                        emailField.getText(),levelComboBox.getValue());
                newMemberStage.close();
            } catch (DatabaseException e) {
                showInfoAlert(e.getMessage());
            } catch (Exception e) {
                showErrorAlert(e.getMessage());
            }
        });
        cancelButton.setOnAction(ae -> {
            newMemberStage.close();
        });

        newMemberStage.initOwner(mainStage);
        newMemberStage.initModality(Modality.APPLICATION_MODAL);
        newMemberStage.setScene(scene2);
        newMemberStage.showAndWait();
    }
}

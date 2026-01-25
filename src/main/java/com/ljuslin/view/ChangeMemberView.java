package com.ljuslin.view;

import com.ljuslin.controller.MemberController;
import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;
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
 * Shows view for changing a member
 *
 * @author Tina Ljuslin
 */
public class ChangeMemberView extends View {
    private MemberController memberController;

    private Stage newMemberStage;
    private Scene scene2;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private ComboBox<Level> levelComboBox;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label levelLabel;
    private Button saveButton;
    private Button cancelButton;
    private GridPane gridPane;

    public ChangeMemberView(MemberController memberController) {
        this.memberController = memberController;
    }

    public Member showPopUp(Stage mainStage, Member member) {
        newMemberStage = new Stage();
        saveButton = new Button("Spara");
        cancelButton = new Button("Avbryt");
        firstNameLabel = new Label("Förnamn");
        lastNameLabel = new Label("Efternamn");
        levelLabel = new Label("Level");
        firstNameField = new TextField(member.getFirstName());
        lastNameField = new TextField(member.getLastName());
        emailField = new TextField(member.getEmail());
        ObservableList<Level> levels = FXCollections.observableArrayList(Level.values());
        levelComboBox = new ComboBox<>(levels);
        levelComboBox.getSelectionModel().select(member.getMemberLevel());
        gridPane = new GridPane();
        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(levelLabel, 0, 2);
        gridPane.add(firstNameField, 1, 0);
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
                member.setFirstName(firstNameField.getText());
                member.setLastName(lastNameField.getText());
    //om email inte ändrats så blir det fel...
                member.setEmail(emailField.getText());
                member.setMemberLevel(levelComboBox.getValue());
                memberController.changeMember(member);
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
        return member;
    }
}

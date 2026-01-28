package com.ljuslin.view;

import com.ljuslin.controller.RentalObjectController;
import com.ljuslin.controller.MemberController;
import com.ljuslin.entity.*;
import com.ljuslin.exception.DatabaseException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * Creates view for a new rental
 *
 * @author Tina Ljuslin
 */
public class NewRentalView extends View {
    private MemberController memberController;
    private RentalObjectController rentalObjectController;

    private BorderPane pane;
    private Stage newRentalStage;
    private Scene scene2;
    private VBox vbox;
    private Button chooseButton;
    private Button cancelButton;
    private TableView<Member> memberTable;
    private TableView<RentalObject> rentalObjectTable;
    private TableColumn<RentalObject, String> typeColumn;
    private TableColumn<Member, String> idColumn;
    private TableColumn<Member, String> firstNameColumn;
    private TableColumn<Member, String> emailColumn;
    private TableColumn<Member, String> lastNameColumn;
    private TableColumn<Member, Level> levelColumn;
    private TableColumn<RentalObject, Material> materialColumn;
    private TableColumn<RentalObject, Double> priceColumn;
    private TableColumn<RentalObject, String> colorColumn;

    private Member member;
    private RentalObject rentalObject;

    public NewRentalView(MemberController memberController,
                         RentalObjectController rentalObjectController) {
        this.memberController = memberController;
        this.rentalObjectController = rentalObjectController;
    }

    public Member showMemberPopUp(Stage mainStage) {
        newRentalStage = new Stage();
        pane = new BorderPane();
        vbox = new VBox();

        chooseButton = new Button("Välj");
        cancelButton = new Button("Avbryt");
        chooseButton.setMaxWidth(Double.MAX_VALUE);
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        vbox.getChildren().addAll(chooseButton, cancelButton);
        memberTable = new TableView<>();
        memberTable.setEditable(false);
        firstNameColumn = new TableColumn<>("Förnamn");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn = new TableColumn<>("Efternamn");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        levelColumn = new TableColumn<>("Level");
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("memberLevel"));
        memberTable.getColumns().addAll(firstNameColumn, lastNameColumn, emailColumn, levelColumn);
        populateMemberTable();
        pane.setLeft(vbox);
        pane.setCenter(memberTable);
        scene2 = new Scene(pane, 1000, 500);
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);
        chooseButton.setOnAction(ae -> {
            Member tempMember = memberTable.getSelectionModel().getSelectedItem();
            if (tempMember != null) {
                this.member = tempMember;
                newRentalStage.close();
            } else {
                showInfoAlert("Välj en medlem som ska hyra!");
            }
        });
        cancelButton.setOnAction(ae -> {
            newRentalStage.close();
        });

        newRentalStage.initOwner(mainStage);
        newRentalStage.initModality(Modality.APPLICATION_MODAL);
        newRentalStage.setScene(scene2);
        newRentalStage.showAndWait();
        return member;
    }


    public RentalObject showAvailableRentalObjectsPopUp(Stage mainStage) {
        newRentalStage = new Stage();
        pane = new BorderPane();
        vbox = new VBox();

        chooseButton = new Button("Välj");
        cancelButton = new Button("Avbryt");
        chooseButton.setMaxWidth(Double.MAX_VALUE);
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        vbox.getChildren().clear();
        vbox.getChildren().addAll(chooseButton, cancelButton);
        rentalObjectTable = new TableView<>();
        rentalObjectTable.setEditable(false);
        typeColumn = new TableColumn<>("Typ:");
        typeColumn.setCellValueFactory(cellData -> {
            RentalType type = cellData.getValue().getRentalType();
            String swedishName = (type != null) ? type.getSwedishName() : "";

            return new SimpleStringProperty(swedishName);
        });
        colorColumn = new TableColumn<>("färg");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        materialColumn = new TableColumn<>("Material");
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        priceColumn = new TableColumn<>("Pris per dag");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));

        rentalObjectTable.getColumns().addAll(typeColumn, colorColumn,
                materialColumn,
                priceColumn);
        populateAvailableItemTable();
        pane.setLeft(vbox);
        pane.setCenter(rentalObjectTable);

        scene2 = new Scene(pane, 1000, 500);
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);
        chooseButton.setOnAction(ae -> {
            RentalObject tempRentalObject = rentalObjectTable.getSelectionModel().getSelectedItem();
            if (tempRentalObject != null) {
                this.rentalObject = tempRentalObject;
                newRentalStage.close();
            } else {
                showInfoAlert("Välj en item att hyra!");
            }
        });
        cancelButton.setOnAction(ae -> {
            newRentalStage.close();
        });

        newRentalStage.initOwner(mainStage);
        newRentalStage.initModality(Modality.APPLICATION_MODAL);
        newRentalStage.setScene(scene2);
        newRentalStage.showAndWait();
        return rentalObject;
    }

    public RentalObject showRentalObjectsPopUp(Stage mainStage) {
        newRentalStage = new Stage();
        pane = new BorderPane();
        vbox = new VBox();

        chooseButton = new Button("Välj");
        cancelButton = new Button("Avbryt");
        chooseButton.setMaxWidth(Double.MAX_VALUE);
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        vbox.getChildren().clear();
        vbox.getChildren().addAll(chooseButton, cancelButton);
        rentalObjectTable = new TableView<>();
        rentalObjectTable.setEditable(false);
        typeColumn = new TableColumn<>("Typ:");
        typeColumn.setCellValueFactory(cellData -> {
            RentalType type = cellData.getValue().getRentalType();
            String swedishName = (type != null) ? type.getSwedishName() : "";

            return new SimpleStringProperty(swedishName);
        });
        colorColumn = new TableColumn<>("färg");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        materialColumn = new TableColumn<>("Material");
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        priceColumn = new TableColumn<>("Pris per dag");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));

        rentalObjectTable.getColumns().addAll(typeColumn, colorColumn,
                materialColumn,
                priceColumn);
        populateRentalObjectTable();
        pane.setLeft(vbox);
        pane.setCenter(rentalObjectTable);

        scene2 = new Scene(pane, 1000, 500);
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);
        chooseButton.setOnAction(ae -> {
            RentalObject tempRentalObject = rentalObjectTable.getSelectionModel().getSelectedItem();
            if (tempRentalObject != null) {
                this.rentalObject = tempRentalObject;
                newRentalStage.close();
            } else {
                showInfoAlert("Välj en item att hyra!");
            }
        });
        cancelButton.setOnAction(ae -> {
            newRentalStage.close();
        });

        newRentalStage.initOwner(mainStage);
        newRentalStage.initModality(Modality.APPLICATION_MODAL);
        newRentalStage.setScene(scene2);
        newRentalStage.showAndWait();
        return rentalObject;
    }

    private void populateMemberTable() {
        List<Member> list = memberController.getAllMembers(this);
        ObservableList<Member> observableList = FXCollections.observableList(list);
        memberTable.setItems(observableList);

    }

    private void populateAvailableItemTable() {
        List<RentalObject> list = rentalObjectController.getAllAvailableRentalObjects(this);
        ObservableList<RentalObject> observableList = FXCollections.observableList(list);
        rentalObjectTable.setItems(observableList);

    }

    private void populateRentalObjectTable() {
        try {
            List<RentalObject> list = rentalObjectController.getAllRentalObjects(this);
            ObservableList<RentalObject> observableList = FXCollections.observableList(list);
            rentalObjectTable.setItems(observableList);
        } catch (DatabaseException e) {
            showInfoAlert(e.getMessage());
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }
}

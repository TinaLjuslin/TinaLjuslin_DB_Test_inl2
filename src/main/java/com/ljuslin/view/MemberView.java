package com.ljuslin.view;

import com.ljuslin.controller.MemberController;
import com.ljuslin.controller.RentalController;
import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;
import com.ljuslin.exception.DatabaseException;
import com.ljuslin.exception.IllegalActionException;
import com.ljuslin.exception.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Creates member tab
 *
 * @author Tina Ljuslin
 */
public class MemberView extends View implements TabView {
    private MemberController memberController;
    private RentalController rentalController;
    private Tab tab;
    private BorderPane pane;
    private VBox vbox;
    private Button newButton;
    private Button searchButton;
    private Button changeButton;
    private Button deleteButton;
    private Button historyButton;
    private Button newRentalButton;
    private Button rechargeButton;
    private Button exitButton;
    private Region region;
    private TableView<Member> table;
    private TableColumn<Member, Integer> idColumn;
    private TableColumn<Member, String> firstNameColumn;
    private TableColumn<Member, String> lastNameColumn;
    private TableColumn<Member, String> emailColumn;
    private TableColumn<Member, Level> levelColumn;


    //TODO
/*// Bind knappen "disable"-property till tabellens selection
// Knappen är avaktiverad (disabled) så länge ingenting är valt
changeButton.disableProperty().bind(
    table.getSelectionModel().selectedItemProperty().isNull()
);

changeButton.setOnAction(ae -> {
    // Nu VET vi att member inte är null, för knappen gick inte att trycka på annars!
    Member member = table.getSelectionModel().getSelectedItem();
    memberController.changeMemberView(member);
    // populateTable() körs i controllern efter att fönstret stängts (se nedan)
});*/
    public MemberView() {
    }

    public Tab getTab() {
        tab = new Tab("Medlemmar");
        pane = new BorderPane();
        vbox = new VBox();
        newButton = new Button("Ny medlem");
        searchButton = new Button("Sök medlem");
        changeButton = new Button("Ändra medlem");
        deleteButton = new Button("Ta bort medlem");
        historyButton = new Button("Visa historia");
        newRentalButton = new Button("Ny uthyrning");
        rechargeButton = new Button("Ladda om");
        exitButton = new Button("Avsluta");
        region = new Region();

        vbox.getChildren().addAll(newButton, searchButton, changeButton, deleteButton, historyButton
                , newRentalButton, rechargeButton, region, exitButton);
        VBox.setVgrow(region, Priority.ALWAYS);
        table = new TableView<>();
        table.setEditable(false);
        idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        firstNameColumn = new TableColumn<>("Förnamn");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn = new TableColumn<>("Efternamn");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        levelColumn = new TableColumn<>("Level");
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("memberLevel"));
        table.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, emailColumn, levelColumn);
        populateTable();
        pane.setLeft(vbox);
        pane.setCenter(table);
        tab.setContent(pane);
        newButton.setOnAction(ae -> {
            memberController.newMemberView();
            populateTable();
        });
        searchButton.setOnAction(ae -> {
            memberController.searchMemberView();
        });
        changeButton.setOnAction(ae -> {
            Member member = table.getSelectionModel().getSelectedItem();
            if (member != null) {
                memberController.changeMemberView(member);
                populateTable();
            } else {
                showInfoAlert("Välj en medlem att ändra!");
            }
        });
        newRentalButton.setOnAction(ae -> {
            Member member = table.getSelectionModel().getSelectedItem();
            if (member != null) {
                try {
                    rentalController.newRental(member, this);
                } catch (DatabaseException e) {
                    showInfoAlert(e.getMessage());
                } catch (Exception e) {
                    showErrorAlert(e.getMessage());
                }
            } else {
                showInfoAlert("Välj en medlem att hyra ut till!");
            }
        });
        deleteButton.setOnAction(ae -> {
            Member member = table.getSelectionModel().getSelectedItem();
            if (member != null) {
                if (memberController.removeMember(member, this)) {
                    showInfoAlert("Medlem " + member.getFirstName() + " " + member.getLastName() + " borttagen.");
                    populateTable();
                }


            } else {
                showInfoAlert("Välj en medlem att ta bort!");
            }
        });
        historyButton.setOnAction(ae -> {
            Member member = table.getSelectionModel().getSelectedItem();
            if (member != null) {
                memberController.getHistoryView(member, this);
            } else {
                showInfoAlert("Välj en medlem att visa historia för!");
            }
        });
        rechargeButton.setOnAction(ae -> {
            populateTable();
        });
        exitButton.setOnAction(ae -> {
            System.exit(0);
        });
        return tab;
    }

    private void populateTable() {
        List<Member> list = memberController.getAllMembers(this);
        ObservableList<Member> observableList = FXCollections.observableList(list);
        table.setItems(observableList);
    }

    public void populateTable(List<Member> members) {
        ObservableList<Member> observableList = FXCollections.observableList(members);
        table.setItems(observableList);
    }

    public void setMemberController(MemberController memberController) {
        this.memberController = memberController;
    }

    public void setRentalController(RentalController rentalController) {
        this.rentalController = rentalController;
    }
}

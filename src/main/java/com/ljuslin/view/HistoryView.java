package com.ljuslin.view;

import com.ljuslin.entity.Member;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Shows view with history of a member
 *
 * @author Tina Ljuslin
 */
public class HistoryView {

    private Stage historyStage;
    private Scene scene2;
    private VBox vBox;
    private Button okButton;
    private ListView<String> historyView;
    public HistoryView() {
       }
    public void showPopUp(Stage mainStage, Member member) {
        historyStage = new Stage();
        okButton = new Button("Ok");
        historyView = new ListView(FXCollections.observableArrayList(member.getHistory()));
        vBox = new VBox();
        vBox.getChildren().addAll(historyView, okButton);
        scene2 = new Scene(vBox, 600, 300);
        String css = getClass().getResource("/greenStyles.css").toExternalForm();
        scene2.getStylesheets().add(css);
        okButton.setOnAction(ae -> {
            historyStage.close();
        });
        historyStage.initOwner(mainStage);
        historyStage.initModality(Modality.APPLICATION_MODAL);
        historyStage.setScene(scene2);
        historyStage.showAndWait();
    }
}
